package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.TimeHelpers;

public class PlanUpdater {

  public static void updatePlanMap(GDRMDataGetterInterface dataGetter, GDRMDataStorerInterface dataStorer,
      EDMSDataGetterInterface edmsDataGetter, Plan plan, MarketSignal marketSignal)
      throws DataAccessLayerException, EDMSDataGetterException {
    if (!(plan.getStatus().equals(PlanStatusEnum.REGISTERED.toString())
        | plan.getStatus().equals(PlanStatusEnum.INVALID.toString())) && planActionsNeedUpdate(marketSignal, plan)) {
      PlanUpdater.updatePlanActionsMap(edmsDataGetter, dataGetter, dataStorer, marketSignal, plan);
    }
    PlanUpdater.updatePlanStatus(marketSignal, plan, dataStorer);
  }

  public static Double[] computeActualDr(Double[] forecast, Double[] actual, int numberIntervals) {
    Double[] delivered = new Double[numberIntervals];
    for (int i = 0; i < numberIntervals; i++) {
      if (forecast[i] != null && actual[i] != null) {
        delivered[i] = forecast[i] - actual[i];
      }
    }
    return delivered;
  }

  public static boolean planActionsNeedUpdate(MarketSignal marketSignal, Plan plan) {
    boolean needsUpdate = false;
    Instant now = Instant.now();
    Instant startPlanImplementation = marketSignal.getStartTime().toInstant();
    Instant endPlanImplementation = marketSignal.getEndTime().toInstant();

    long timeIntervalMs = TimeHelpers.MS_IN_A_SEC * marketSignal.getTimeInterval();

    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActionsSet = plan.getPlanHasActions();
    List<PlanHasAction> planActions = new ArrayList<PlanHasAction>(planActionsSet);
    Instant oldestUpdate = planActions.get(0).getUpdatedActualAt().toInstant();
    for (PlanHasAction planAction : planActions) {
      Instant planActionLastUpdate = planAction.getUpdatedActualAt().toInstant();
      if (planActionLastUpdate.isBefore(oldestUpdate)) {
        oldestUpdate = planActionLastUpdate;
      }
    }
    if (now.isAfter(startPlanImplementation) && now.isBefore(endPlanImplementation)) {
      if (now.minus(oldestUpdate.getMillis()).getMillis() > timeIntervalMs) {
        needsUpdate = true;
      }
    } else if (now.isAfter(endPlanImplementation) && endPlanImplementation.isAfter(oldestUpdate)) {
      needsUpdate = true;
    }
    return needsUpdate;
  }

  public static void updatePlanStatus(MarketSignal marketSignal, Plan plan, GDRMDataStorerInterface dataStorer)
      throws DataAccessLayerException {
    boolean planHasBeenModified = false;
    String planStatus = plan.getStatus();
    Instant now = Instant.now();

    if (planStatus.equals(PlanStatusEnum.ONGOING.toString())) {
      if (now.isAfter(marketSignal.getEndTime())) {
        plan.setStatus(PlanStatusEnum.COMPLETED.toString());
        planHasBeenModified = true;
      }
    }
    if (planStatus.equals(PlanStatusEnum.CREATED.toString())) {
      if (now.isAfter(marketSignal.getStartTime()) && now.isBefore(marketSignal.getEndTime())) {
        plan.setStatus(PlanStatusEnum.ONGOING.toString());
        planHasBeenModified = true;
      }
      if (now.isAfter(marketSignal.getEndTime())) {
        plan.setStatus(PlanStatusEnum.COMPLETED.toString());
        planHasBeenModified = true;
      }
    }
    if (planHasBeenModified) {
      dataStorer.storePlan(plan);
    }
  }

  private static void updatePlanActionsMap(EDMSDataGetterInterface edmsDataGetter, GDRMDataGetterInterface dataGetter,
      GDRMDataStorerInterface dataStorer, MarketSignal marketSignal, Plan plan)
      throws DataAccessLayerException, EDMSDataGetterException {
    Instant startTime = marketSignal.getStartTime().toInstant();
    Instant signalEndTime = marketSignal.getEndTime().toInstant();
    Instant endTime = signalEndTime;
    if (signalEndTime.isAfterNow()) {
      endTime = Instant.now();
    }

    int intervalDurationSec = marketSignal.getTimeInterval();
    int numberIntervalsToUpdate = TimeHelpers.getNumberIntervals(startTime, endTime, intervalDurationSec);
    @SuppressWarnings("unchecked")
    List<PlanHasAction> planHasActions = new ArrayList<PlanHasAction>(plan.getPlanHasActions());

    for (PlanHasAction planHasAction : planHasActions) {
      Map<Long, Double> actualConsumption = DataPlanGetter.getActionActualConsumptionFromEDMSMap(edmsDataGetter, dataGetter,
          marketSignal, plan, planHasAction, marketSignal.getStartTime(), marketSignal.getEndTime());
      Map<Long, Double> forecastConsumption = DataPlanGetter.getActionForecastConsumptionFromEDMSMap(edmsDataGetter, dataGetter,
          marketSignal, plan, planHasAction, marketSignal.getStartTime(), marketSignal.getEndTime());
      Double[] actualDr = computeActualDrMap(forecastConsumption, actualConsumption, marketSignal.getStartTime(), endTime,
          intervalDurationSec, numberIntervalsToUpdate);
      planHasAction.setActualAmount(Arrays.toString(actualDr));
      Instant now = Instant.now();
      planHasAction.setUpdatedActualAt(now.toDateTime());
      dataStorer.storePlanHasAction(planHasAction);
    }
  }

  private static Double[] computeActualDrMap(Map<Long, Double> forecastConsumption, Map<Long, Double> actualConsumption,
      DateTime startTime, Instant endTime, int intervalDuration, int numberIntervalsToUpdate) {

    HashMap<Long, Double> delivered = new HashMap<Long, Double>();

    List<Long> timestamps = TimeHelpers.getInitialSlotsTimeStamps(startTime, endTime.toDateTime(), intervalDuration);

    Set<Long> forecastTime = forecastConsumption.keySet();
    Set<Long> actualTime = actualConsumption.keySet();

    for (Long timestamp : timestamps) {
      Double deliveredAmount = null;
      if (forecastTime.contains(timestamp) && actualTime.contains(timestamp)) {
        deliveredAmount = forecastConsumption.get(timestamp) - actualConsumption.get(timestamp);
      }
      delivered.put(timestamp, deliveredAmount);
    }

    List<Double> deliveredList = new ArrayList<Double>();
    SortedSet<Long> timeKeys = new TreeSet<Long>(delivered.keySet());
    for (Long timeKey : timeKeys) {
      deliveredList.add(delivered.get(timeKey));
    }

    Double[] deliveredArray = deliveredList.toArray(new Double[deliveredList.size()]);

    return deliveredArray;
  }

}
