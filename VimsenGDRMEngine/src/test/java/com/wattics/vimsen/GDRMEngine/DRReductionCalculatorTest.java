package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;

public class DRReductionCalculatorTest {
  
  //TODO: rewrite and correct the tests to support the new configuration to get parameters from dataGetter

  @Test
  public void actionOutsideSlaTimeRangeAreNotValid() {
    MarketSignal marketSignal = new MarketSignal();
    marketSignal.setStartTime(new DateTime("2016-05-10T10:00:00+00:00"));
    marketSignal.setEndTime(new DateTime("2016-05-10T12:00:00+00:00"));
    ActionSla invalidActionSla = createInvalidActionSla(marketSignal);

    Action actionInvalid = new Action();
    actionInvalid.setActionSla(invalidActionSla);

    List<Action> actions = new ArrayList<>();
    actions.add(actionInvalid);

    List<Action> selectedAction = DRReductionCalculator.selectValidActions(actions, marketSignal);

    Assert.assertEquals(selectedAction.size(), 0);
  }

  @Test
  public void actionInsideSlaTimeRangeIsValid() {
    MarketSignal marketSignal = new MarketSignal();
    marketSignal.setStartTimeText("2016-05-10T10:00:00.000+00:00");
    marketSignal.setStartTime(new DateTime("2016-05-10T10:00:00.000+00:00"));
    marketSignal.setEndTime(new DateTime("2016-05-10T12:00:00.000+00:00"));
    ActionSla validActionSla = createValidActionSla(marketSignal);

    Action actionInvalid = new Action();
    actionInvalid.setActionSla(validActionSla);

    List<Action> actions = new ArrayList<>();
    actions.add(actionInvalid);

    List<Action> selectedAction = DRReductionCalculator.selectValidActions(actions, marketSignal);

    Assert.assertEquals(selectedAction.size(), 1);
  }

  @Test
  public void actionBroderSlaTimeRangeIsValid() {
    MarketSignal marketSignal = new MarketSignal();
    marketSignal.setStartTimeText("2016-05-10T10:00:00.000+00:00");
    marketSignal.setStartTime(new DateTime("2016-05-10T10:00:00.000+00:00"));
    marketSignal.setEndTime(new DateTime("2016-05-10T12:00:00.000+00:00"));
    ActionSla validActionSla = createBorderActionSla(marketSignal);

    Action actionInvalid = new Action();
    actionInvalid.setActionSla(validActionSla);

    List<Action> actions = new ArrayList<>();
    actions.add(actionInvalid);

    List<Action> selectedAction = DRReductionCalculator.selectValidActions(actions, marketSignal);

    Assert.assertEquals(selectedAction.size(), 1);
  }

  private ActionSla createBorderActionSla(MarketSignal marketSignal) {
    ActionSla actionSla = new ActionSla();
    actionSla.setStartResponsePeriod(marketSignal.getStartTime());
    actionSla.setEndResponsePeriod(marketSignal.getEndTime());
    return actionSla;
  }

  private ActionSla createValidActionSla(MarketSignal marketSignal) {
    ActionSla actionSla = new ActionSla();
    actionSla.setStartResponsePeriod(marketSignal.getStartTime().minus(1));
    actionSla.setEndResponsePeriod(marketSignal.getEndTime().plus(1));
    return actionSla;
  }

  private ActionSla createInvalidActionSla(MarketSignal marketSignal) {
    ActionSla actionSla = new ActionSla();
    actionSla.setStartResponsePeriod(marketSignal.getEndTime().minus(1));
    actionSla.setEndResponsePeriod(marketSignal.getEndTime().plus(1));
    return actionSla;
  }

  @Test(enabled = false)
  public void selectedActionsAchieveRequestedReduction() throws MapperException, DataAccessLayerException {
    Double[] targetReductions = new Double[] { 2.0, 5.0 };
    int planId = 1;
    MarketSignal marketSignal = new MarketSignal();
    // marketSignal.setStartTimeText("2016-05-10T10:00:00.000+00:00");
    // marketSignal.setStartTime(new DateTime("2016-05-10T10:00:00.000+00:00"));
    // marketSignal.setEndTime(new DateTime("2016-05-10T12:00:00.000+00:00"));

    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(10.0);
    Action action = new Action();
    action.setActionSla(actionSla);
    actions.add(action);

    List<PlanHasAction> planActions = DRReductionCalculator.selectActionsToAchieveReduction(actions, targetReductions,
        marketSignal, planId, null);

    Double[] plannedReduction = new Double[targetReductions.length];
    for (int j = 0; j < targetReductions.length; j++) {
      plannedReduction[j] = 0.0;
    }
    for (PlanHasAction planAction : planActions) {
      Double[] actionPlannedReduction = FormatConverter.stringToArrayDouble(planAction.getPlannedAmount());
      for (int j = 0; j < plannedReduction.length; j++) {
        plannedReduction[j] = plannedReduction[j] + actionPlannedReduction[j];
      }
    }

    for (int j = 0; j < plannedReduction.length; j++) {
      Assert.assertTrue((plannedReduction[j] - targetReductions[j]) >= 0);
    }
  }

  @Test(enabled = false)
  public void availableActionsDontAchieveRequestedReduction() throws MapperException, DataAccessLayerException {
    Double[] targetReductions = new Double[] { 10.0, 15.0 };
    int planId = 1;
    MarketSignal marketSignal = new MarketSignal();

    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(2.0);
    Action action = new Action();
    action.setActionSla(actionSla);
    actions.add(action);

    List<PlanHasAction> planActions = DRReductionCalculator.selectActionsToAchieveReduction(actions, targetReductions,
        marketSignal, planId, null);

    Double[] plannedReduction = new Double[targetReductions.length];
    for (int j = 0; j < targetReductions.length; j++) {
      plannedReduction[j] = 0.0;
    }
    for (PlanHasAction planAction : planActions) {
      Double[] actionPlannedReduction = FormatConverter.stringToArrayDouble(planAction.getPlannedAmount());
      for (int j = 0; j < plannedReduction.length; j++) {
        plannedReduction[j] = plannedReduction[j] + actionPlannedReduction[j];
      }
    }

    Assert.assertEquals(plannedReduction[0], 2.0);
    Assert.assertEquals(plannedReduction[1], 2.0);
  }

  @Test(enabled = false)
  public void selectedTwoActionsFirstSlotOneSecondSlot() throws MapperException, DataAccessLayerException {
    Double[] targetReductions = new Double[] { 10.0, 15.0 };
    int planId = 1;
    MarketSignal marketSignal = new MarketSignal();

    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(5.0);
    Action action = new Action();
    action.setActionSla(actionSla);
    actions.add(action);
    Action action2 = new Action();
    action2.setActionSla(actionSla);
    actions.add(action2);
    Action action3 = new Action();
    action3.setActionSla(actionSla);
    actions.add(action3);

    List<PlanHasAction> planActions = DRReductionCalculator.selectActionsToAchieveReduction(actions, targetReductions,
        marketSignal, planId, null);

    Double[] plannedReduction = new Double[targetReductions.length];
    for (int j = 0; j < targetReductions.length; j++) {
      plannedReduction[j] = 0.0;
    }
    for (PlanHasAction planAction : planActions) {
      Double[] actionPlannedReduction = FormatConverter.stringToArrayDouble(planAction.getPlannedAmount());
      for (int j = 0; j < plannedReduction.length; j++) {
        plannedReduction[j] = plannedReduction[j] + actionPlannedReduction[j];
      }
    }

    Assert.assertEquals(plannedReduction[0], 10.0);
    Assert.assertEquals(plannedReduction[1], 15.0);
  }

  @Test(enabled = false)
  public void selectedCombinationOfActions() throws DataAccessLayerException {
    Double target = 10.0;
    int slotSizeSec = 900;

    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(6.0);
    Action action = new Action();
    action.setActionSla(actionSla);
    actions.add(action);
    Action action2 = new Action();
    action2.setActionSla(actionSla);
    actions.add(action2);
    Action action3 = new Action();
    action3.setActionSla(actionSla);
    actions.add(action3);

    List<Action> selectedActions = DRReductionCalculator.selectCombinationCloserToTarget(actions, target, slotSizeSec, null);

    Assert.assertEquals(selectedActions.size(), 2);
  }

  @Test(enabled = false)
  public void selectedTwoActionsFromTenActions() throws DataAccessLayerException {
    int slotSizeSec = 900;
    Double target = 10.0;
    Double actionsTarget = 6.0;
    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(actionsTarget);

    for (int i = 0; i < 10; i++) {
      Action action = new Action();
      action.setActionSla(actionSla);
      actions.add(action);
    }

    List<Action> selectedActions = DRReductionCalculator.selectCombinationCloserToTarget(actions, target, slotSizeSec, null);

    Assert.assertEquals(selectedActions.size(), 2);
  }

  @Test(enabled = false)
  public void selectedNineActionsFromTenActions() throws DataAccessLayerException {
    int slotSizeSec = 900;
    Double target = 50.0;
    Double actionsTarget = 6.0;
    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(actionsTarget);

    for (int i = 0; i < 10; i++) {
      Action action = new Action();
      action.setActionSla(actionSla);
      actions.add(action);
    }

    List<Action> selectedActions = DRReductionCalculator.selectCombinationCloserToTarget(actions, target, slotSizeSec, null);

    Assert.assertEquals(selectedActions.size(), 9);
  }

  @Test(enabled = false)
  public void selectedTwoActionsExactTarget() throws DataAccessLayerException {
    int slotSizeSec = 900;
    Double target = 12.0;
    Double actionsTarget = 6.0;
    List<Action> actions = new ArrayList<>();
    ActionSla actionSla = new ActionSla();
    actionSla.setConsumptionTarget(actionsTarget);

    for (int i = 0; i < 10; i++) {
      Action action = new Action();
      action.setActionSla(actionSla);
      actions.add(action);
    }

    List<Action> selectedActions = DRReductionCalculator.selectCombinationCloserToTarget(actions, target, slotSizeSec, null);

    Assert.assertEquals(selectedActions.size(), 2);
  }

}
