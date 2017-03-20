package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;

public class PlanGenerator {

  private final static int MAXIMUM_NUMBER_ACTIONS = 7;
  private static Random randomizer = new Random();

  public static List<PlanHasAction> generatePlanActionsFromSla(int planId, GDRMDataGetterAndValidationInterface dataGetter)
      throws MapperException, DataAccessLayerException, NoValidDataException {
    Plan plan = dataGetter.getPlan(planId);
    MarketSignal marketSignal = plan.getMarketSignal();
    List<Prosumer> prosumers = dataGetter.getPrimaryProsumersFromMarketSignal(marketSignal);

    String targetReductionString = marketSignal.getAmountReduction();
    Double[] targetReduction = FormatConverter.stringToArrayDouble(targetReductionString);

    List<PlanHasAction> plannedActions = createReductionPlanFromSla(dataGetter, marketSignal, planId, targetReduction, prosumers);

    return plannedActions;
  }

  public static List<PlanHasAction> createReductionPlanFromSla(GDRMDataGetterAndValidationInterface dataGetter,
      MarketSignal marketSignal, int planId, Double[] targetReduction, List<Prosumer> prosumers)
      throws DataAccessLayerException, NoValidDataException {
    List<Action> actions = dataGetter.getActionsFromProsumers(prosumers);
    List<Action> validActions = DRReductionCalculator.selectValidActions(actions, marketSignal);

    List<Action> subSelectionValidAction = selectSubGroupOfValidActions(validActions, MAXIMUM_NUMBER_ACTIONS);

    List<PlanHasAction> drActions = DRReductionCalculator.selectActionsToAchieveReduction(subSelectionValidAction,
        targetReduction, marketSignal, planId, dataGetter);

    return drActions;
  }

  private static List<Action> selectSubGroupOfValidActions(List<Action> validActions, int maximumNumberActions) {
    if (validActions.size() < maximumNumberActions) {
      return validActions;
    }

    List<Action> selectedActions = new ArrayList<>();
    for (int i = 0; i < maximumNumberActions; i++) {
      Action randomAction = validActions.get(randomizer.nextInt(validActions.size()));
      selectedActions.add(randomAction);
      validActions.remove(randomAction);
    }
    return selectedActions;
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
