package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterNoHib;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.PlanHasActionId;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;

public class PlanGenerator {

  public static List<PlanHasAction> generatePlanActionsMap(int planId, GDRMDataGetterInterface dataGetter,
      EDMSDataGetterInterface edmsDataGetter) throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    List<PlanHasAction> plannedActions = new ArrayList<PlanHasAction>();
    Plan plan = dataGetter.getPlan(planId);
    MarketSignal marketSignal = plan.getMarketSignal();
    List<Prosumer> prosumers = GDRMDataGetterNoHib.getPrimaryProsumersFromMarketSignal(marketSignal);
    List<Action> actions = new ArrayList<Action>();
    for (Prosumer prosumer : prosumers) {
      List<Action> prosumerActions = dataGetter.getActionsFromProsumer(prosumer);
      Action selectedAction = DataPlanGetter.selectProsumerAction(prosumerActions);
      actions.add(selectedAction);
    }

    String targetReductionString = marketSignal.getAmountReduction();
    Double[] targetReduction = FormatConverter.stringToArrayDouble(targetReductionString);

    List<Map<Long, Double>> forecastAmount = DataPlanGetter.getProsumersForecastConsumptionFromEDMSMap(prosumers,
        marketSignal.getStartTime(), marketSignal.getEndTime(), marketSignal.getTimeInterval(), edmsDataGetter);
    List<Double[]> plannedAmount = DRReductionCalculator.createBalancedPlanPrimaryProsumersMap(targetReduction, prosumers,
        forecastAmount, marketSignal.getStartTime(), marketSignal.getEndTime(), marketSignal.getTimeInterval());

    for (int i = 0; i < actions.size(); i++) {
      PlanHasActionId plannedActionId = new PlanHasActionId(planId, actions.get(i).getId());
      PlanHasAction plannedAction = new PlanHasAction();
      plannedAction.setId(plannedActionId);
      plannedAction.setTStart(marketSignal.getStartTime());
      plannedAction.setPlannedAmount(Arrays.toString(plannedAmount.get(i)));
      plannedAction.setUpdatedActualAt(marketSignal.getStartTime());
      plannedActions.add(plannedAction);
    }
    return plannedActions;
  }

  public static boolean generatedActionsAreValid(List<PlanHasAction> plannedActions) {
    boolean areValid = true;
    for (PlanHasAction plannedAction : plannedActions) {
      String amount = plannedAction.getPlannedAmount();
      try {
        FormatConverter.stringToArrayDouble(amount);
      } catch (MapperException e) {
        areValid = false;
      }
    }
    return areValid;
  }

  public static List<Plan> selectPlansEndingBefore24HFromNow(List<Plan> plans) {
    List<Plan> plansDay = new ArrayList<Plan>();

    for (Plan plan : plans) {
      MarketSignal marketSignal = plan.getMarketSignal();
      DateTime planEndTime = marketSignal.getEndTime();
      DateTime planEndTime24hAhead = planEndTime.minusDays(1);
      if (planEndTime.toInstant().isAfterNow() && planEndTime24hAhead.toInstant().isBeforeNow()) {
        plansDay.add(plan);
      }
    }
    return plansDay;
  }

}
