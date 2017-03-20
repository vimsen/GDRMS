package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.joda.time.Instant;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;

public class SimplifiedPlanUpdater {

  private static final double MIN_PERCENTAGE_ADJ = 1;
  private static final double MAX_PERCENTAGE_ADJ = 10;
  private static Random rn = new Random();

  public static void updatePlan(GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer,
      EDMSDataGetterInterface edmsDataGetter, Plan plan, MarketSignal marketSignal)
      throws MapperException, DataAccessLayerException, EDMSDataGetterException, NoValidDataException, InterruptedException {
    if (plan.getStatus().equals(PlanStatusEnum.CREATED.toString())) {
      processHistoricalPlan(marketSignal, plan, edmsDataGetter, dataGetter, dataStorer);
    }
  }

  public static void processHistoricalPlan(MarketSignal marketSignal, Plan plan, EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer)
      throws MapperException, DataAccessLayerException, EDMSDataGetterException, NoValidDataException, InterruptedException {
    setAndStorePlanStatus(plan, dataStorer, PlanStatusEnum.ONGOING);
    try {
      int totalNumberSlots = PlanHelper.getTotalNumberOfSlotsForMarketSignal(marketSignal);
      for (int slotNumber = 0; slotNumber < totalNumberSlots; slotNumber++) {
        updatePlanActionsHistorical(dataGetter, dataStorer, marketSignal, plan, slotNumber + 1);
        if (slotNumber < totalNumberSlots - 1) {
          Thread.sleep(60 * 1000);
        } // remove temporary sleep for creation of
          // historical plan
      }
    } catch (Exception e) {
      // Set plan status as completed if there was an error
      setAndStorePlanStatus(plan, dataStorer, PlanStatusEnum.COMPLETED);
    }
    setAndStorePlanStatus(plan, dataStorer, PlanStatusEnum.COMPLETED);
  }

  private static void setAndStorePlanStatus(Plan plan, GDRMDataStorerInterface dataStorer, PlanStatusEnum planStatus)
      throws DataAccessLayerException {
    plan.setStatus(planStatus.toString());
    dataStorer.storePlan(plan);
  }

  private static void updatePlanActionsHistorical(GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer, MarketSignal marketSignal, Plan plan, int numberIntervalsToUpdate)
      throws DataAccessLayerException, EDMSDataGetterException {

    @SuppressWarnings("unchecked")
    List<PlanHasAction> planHasActions = new ArrayList<PlanHasAction>(plan.getPlanHasActions());

    for (PlanHasAction planHasAction : planHasActions) {
      try {
        Double[] plannedDr = FormatConverter.stringToArrayDouble(planHasAction.getPlannedAmount());
        Double[] actualDr = new Double[numberIntervalsToUpdate];
        String previousAmount = planHasAction.getActualAmount();
        if (previousAmount != null) {
          Double[] previousActualDr = FormatConverter.stringToArrayDouble(planHasAction.getActualAmount());
          for (int j = 0; j < previousActualDr.length; j++) {
            actualDr[j] = previousActualDr[j];
          }
        }

        actualDr[numberIntervalsToUpdate - 1] = getAdjustedAmount(plannedDr[numberIntervalsToUpdate - 1]);

        planHasAction.setActualAmount(Arrays.toString(actualDr));
        Instant now = Instant.now();
        planHasAction.setUpdatedActualAt(now.toDateTime());
        dataStorer.storePlanHasAction(planHasAction);
      } catch (MapperException e) {
        // TODO:
      }
    }
  }

  private static Double getAdjustedAmount(Double value) {
    Double percentageAdjustment = MIN_PERCENTAGE_ADJ + (MAX_PERCENTAGE_ADJ - MIN_PERCENTAGE_ADJ) * rn.nextDouble();
    Double adjustment = value * percentageAdjustment / 100.0;
    Double adjAmount = value + adjustment;
    return adjAmount;
  }

}
