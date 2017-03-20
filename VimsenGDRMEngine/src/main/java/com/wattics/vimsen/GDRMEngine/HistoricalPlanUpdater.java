package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.Instant;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;

public class HistoricalPlanUpdater {

  public static void updatePlan(GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer,
      EDMSDataGetterInterface edmsDataGetter, Plan plan, MarketSignal marketSignal)
      throws MapperException, DataAccessLayerException, EDMSDataGetterException, NoValidDataException, InterruptedException {
    if (plan.getStatus().equals(PlanStatusEnum.CREATED.toString())) {
      processHistoricalPlan(marketSignal, plan, edmsDataGetter, dataGetter, dataStorer);
    }
  }

  private static void processHistoricalPlan(MarketSignal marketSignal, Plan plan, EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer)
      throws MapperException, DataAccessLayerException, EDMSDataGetterException, NoValidDataException, InterruptedException {
    setAndStorePlanStatus(plan, dataStorer, PlanStatusEnum.ONGOING);
    try {
      int totalNumberSlots = PlanHelper.getTotalNumberOfSlotsForMarketSignal(marketSignal);
      for (int slotNumber = 0; slotNumber < totalNumberSlots; slotNumber++) {
        updatePlanActionsHistorical(edmsDataGetter, dataGetter, dataStorer, marketSignal, plan, slotNumber + 1);
        plan = dataGetter.getPlan(plan.getId());
        if (!hasReductionBeenAchieved(marketSignal, plan, slotNumber) && (slotNumber < totalNumberSlots - 1)) {
          addNewPlanAction(dataGetter, dataStorer, marketSignal, plan, slotNumber, totalNumberSlots);
          plan = dataGetter.getPlan(plan.getId());
        }
        Thread.sleep(10 * 1000); // remove temporary sleep for creation of
                                 // historical plan
      }
    } catch (Exception e) {
      //Set plan status as completed if there was an error
      setAndStorePlanStatus(plan, dataStorer, PlanStatusEnum.COMPLETED);
    }
    setAndStorePlanStatus(plan, dataStorer, PlanStatusEnum.COMPLETED);
  }

  private static void setAndStorePlanStatus(Plan plan, GDRMDataStorerInterface dataStorer, PlanStatusEnum planStatus)
      throws DataAccessLayerException {
    plan.setStatus(planStatus.toString());
    dataStorer.storePlan(plan);
  }

  public static boolean hasReductionBeenAchieved(MarketSignal ms, Plan plan, int slotNumber) throws MapperException {
    Double[] requestedReduction = FormatConverter.stringToArrayDouble(ms.getAmountReduction());
    Double requestedReductionInSlot = requestedReduction[slotNumber];
    Double actualReductionInSlot = getActualReduction(plan, slotNumber);
    boolean isReductionAchieved = (actualReductionInSlot - requestedReductionInSlot) > 0;
    return (isReductionAchieved);
  }

  private static Double getActualReduction(Plan plan, int slotNumber) throws MapperException {
    List<PlanHasAction> planActions = new ArrayList<>(plan.getPlanHasActions());
    Double actualReduction = 0.0;
    for (PlanHasAction planAction : planActions) {
      Double[] actualReductionAll = FormatConverter.stringToArrayDouble(planAction.getActualAmount());
      actualReduction = actualReduction + actualReductionAll[slotNumber];
    }
    return actualReduction;
  }

  public static boolean addNewPlanAction(GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer,
      MarketSignal ms, Plan plan, int slotNumber, int totalSlotNumber) throws DataAccessLayerException, NoValidDataException {
    List<Prosumer> prosumers = dataGetter.getNotSelectedSecondaryProsumers(ms.getId());
    List<Action> actions = dataGetter.getActionsFromProsumers(prosumers);
    List<Action> validActions = DRReductionCalculator.selectValidActions(actions, ms);
    if (validActions.size() > 0) {
      Action selectedAction = selectNewAction(validActions);

      PlanHasAction generatedPlanActions = DRReductionCalculator.generatePlanAction(slotNumber + 1, totalSlotNumber, ms,
          plan.getId(), ms.getTimeInterval(), selectedAction);

      Set<PlanHasAction> planActionUpdates = new HashSet<>(plan.getPlanHasActions());
      planActionUpdates.add(generatedPlanActions);
      plan.setPlanHasActions(planActionUpdates);
      dataStorer.storePlanHasAction(generatedPlanActions);
      return true;
    } else {
      return false;
    }

  }

  private static Action selectNewAction(List<Action> validActions) {
    return validActions.get(0);
  }

  private static void updatePlanActionsHistorical(EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer, MarketSignal marketSignal, Plan plan,
      int numberIntervalsToUpdate) throws DataAccessLayerException, EDMSDataGetterException {
    Instant startTime = marketSignal.getStartTime().toInstant();
    Instant endTime = startTime.plus(numberIntervalsToUpdate * marketSignal.getTimeInterval() * TimeHelpers.MS_IN_A_SEC);

    int intervalDurationSec = marketSignal.getTimeInterval();

    @SuppressWarnings("unchecked")
    List<PlanHasAction> planHasActions = new ArrayList<PlanHasAction>(plan.getPlanHasActions());

    for (PlanHasAction planHasAction : planHasActions) {
      try {
        Map<Long, Double> actualConsumption = DataPlanGetter.getActionActualConsumptionFromEDMSMap(edmsDataGetter, dataGetter,
            marketSignal, plan, planHasAction, marketSignal.getStartTime(), marketSignal.getEndTime());
        Map<Long, Double> forecastConsumption = DataPlanGetter.getActionForecastConsumptionFromEDMSMap(edmsDataGetter, dataGetter,
            marketSignal, plan, planHasAction, marketSignal.getStartTime(), marketSignal.getEndTime());
        Double[] actualDr = PlanHelper.computeActualDrMapHistorical(forecastConsumption, actualConsumption,
            marketSignal.getStartTime(), endTime, intervalDurationSec, numberIntervalsToUpdate);
        planHasAction.setActualAmount(Arrays.toString(actualDr));
        Instant now = Instant.now();
        planHasAction.setUpdatedActualAt(now.toDateTime());
        dataStorer.storePlanHasAction(planHasAction);
      } catch (NoValidDataException e) {
        // TODO:
      }
    }
  }

}
