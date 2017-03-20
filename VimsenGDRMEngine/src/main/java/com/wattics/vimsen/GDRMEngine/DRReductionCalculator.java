package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.PlanHasActionId;
import com.wattics.vimsen.utils.TimeHelpers;

public class DRReductionCalculator {

  private static Random randomizer = new Random();

  public static List<PlanHasAction> selectActionsToAchieveReduction(List<Action> validActions, Double[] targetReductions,
      MarketSignal marketSignal, int planId, GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException {
    int slotSizeInSec = marketSignal.getTimeInterval();
    Double totalReduction = 0.0;
    Double slotReductionToAchieve = targetReductions[0];
    List<Action> actionsPool = validActions;
    List<PlanHasAction> allPlanActions = new ArrayList<>();
    for (int i = 0; i < targetReductions.length; i++) {
      slotReductionToAchieve = targetReductions[i] - totalReduction;
      if (slotReductionToAchieve > 0) {
        // List<Action> slotAction =
        // selectRandomActionsToReachSlotTarget(actionsPool,
        // slotReductionToAchieve);
        List<Action> slotAction = selectCombinationCloserToTarget(actionsPool, slotReductionToAchieve, slotSizeInSec, dataGetter);
        totalReduction = updateTotalReduction(totalReduction, slotAction, slotSizeInSec);
        List<PlanHasAction> planActions = generatePlanActions(slotAction, i, targetReductions.length, marketSignal, planId);
        allPlanActions.addAll(planActions);
        actionsPool.removeAll(slotAction);
      }
      // else{
      // if(reductionInRequestedTargetFromPreviousSlot(targetReduction)))

      // }
    }

    return allPlanActions;
  }

  private static Double updateTotalReduction(Double totalReduction, List<Action> slotAction, int slotSizeInSec) {
    double slotTimeConversion = (TimeHelpers.SEC_IN_A_MIN * TimeHelpers.MIN_IN_A_HOUR) / slotSizeInSec;
    Double newReductions = 0.0;
    for (Action action : slotAction) {
      Double actionReduction = action.getActionSla().getConsumptionTarget() / slotTimeConversion;
      newReductions = newReductions + actionReduction;
    }
    Double reduction = totalReduction + newReductions;
    return reduction;
  }

  public static List<PlanHasAction> generatePlanActions(List<Action> slotActions, int i, int numberSlots,
      MarketSignal marketSignal, int planId) {
    int slotSizeInSec = marketSignal.getTimeInterval();

    List<PlanHasAction> plannedActions = new ArrayList<>();
    for (Action slotAction : slotActions) {
      PlanHasAction plannedAction = generatePlanAction(i, numberSlots, marketSignal, planId, slotSizeInSec, slotAction);
      plannedActions.add(plannedAction);
    }
    return plannedActions;
  }

  public static PlanHasAction generatePlanAction(int i, int numberSlots, MarketSignal marketSignal, int planId, int slotSizeInSec,
      Action slotAction) {
    PlanHasAction plannedAction = new PlanHasAction();
    PlanHasActionId plannedActionId = new PlanHasActionId(planId, slotAction.getId());
    plannedAction.setId(plannedActionId);
    plannedAction.setTStart(marketSignal.getStartTime());
    ActionSla actionSla = slotAction.getActionSla();
    Double[] plannedAmount = calculatePlannedAmount(actionSla, i, numberSlots, slotSizeInSec);
    plannedAction.setAmountRecProgressToSend(numberSlots - 1);
    plannedAction.setAmountRecProgressSent(0);
    plannedAction.setPlannedAmount(Arrays.toString(plannedAmount));
    plannedAction.setUpdatedActualAt(marketSignal.getStartTime());
    return plannedAction;
  }

  private static Double[] calculatePlannedAmount(ActionSla actionSla, int startSlot, int numberSlots, int slotSizeInSec) {
    Double slotTimeConversion = (double) ((TimeHelpers.SEC_IN_A_MIN * TimeHelpers.MIN_IN_A_HOUR) / slotSizeInSec);
    Double[] plannedAmount = new Double[numberSlots];
    Double actionReduction = actionSla.getConsumptionTarget();
    for (int i = 0; i < numberSlots; i++) {
      if (i < startSlot) {
        plannedAmount[i] = 0.0;
      } else {
        plannedAmount[i] = actionReduction / slotTimeConversion;
      }
    }
    return plannedAmount;
  }

  private static List<Action> selectRandomActionsToReachSlotTarget(List<Action> actionsPool, Double reduction) {
    Double slotReduction = reduction;

    List<Action> selectedActions = new ArrayList<>();
    while (slotReduction > 0 && actionsPool.size() > 0) {
      Action randomAction = actionsPool.get(randomizer.nextInt(actionsPool.size()));
      selectedActions.add(randomAction);
      actionsPool.remove(randomAction);
      Double actionReduction = randomAction.getActionSla().getConsumptionTarget();

      slotReduction = slotReduction - actionReduction;

    }
    return selectedActions;
  }

  public static List<Action> selectValidActions(List<Action> actions, MarketSignal marketSignal) {
    DateTime startDrPeriod = marketSignal.getStartTime();
    DateTime endDrPeriod = marketSignal.getEndTime();
    String referenceTime = marketSignal.getStartTimeText();
    List<Action> validActions = new ArrayList<>();
    for (Action action : actions) {
      ActionSla actionSla = action.getActionSla();
      boolean isActionTimeValid = isActionValidInDrTimerange(actionSla, startDrPeriod, endDrPeriod, referenceTime);
      if (isActionTimeValid) {
        validActions.add(action);
      }
    }
    return validActions;
  }

  private static boolean isActionValidInDrTimerange(ActionSla actionSla, DateTime startDrPeriod, DateTime endDrPeriod,
      String referenceTime) {
    try {
      DateTime slaStartPeriod = actionSla.getStartResponsePeriod();
      DateTime slaEndPeriod = actionSla.getEndResponsePeriod();
      int startHourSla = TimeHelpers.getLocalTimeHour(referenceTime, slaStartPeriod);
      int endHourSla = TimeHelpers.getLocalTimeHour(referenceTime, slaEndPeriod);
      int startHourDrPeriod = TimeHelpers.getLocalTimeHour(referenceTime, startDrPeriod);
      int endHourDrPeriod = TimeHelpers.getLocalTimeHour(referenceTime, endDrPeriod);
      return (startHourSla <= startHourDrPeriod && endHourSla >= endHourDrPeriod);
    } catch (Exception e) {
      return false;
    }
  }

  public static List<Action> selectCombinationCloserToTarget(List<Action> actions, Double target, int slotSizeInSec,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException {
    List<Action> selectedActions = actions;
    Node node = new Node(actions);

    if (node.getSumAvailableActions(slotSizeInSec, dataGetter) > target) {
      Node newNode = processNode(node, target, slotSizeInSec, dataGetter);
      selectedActions = selectActionsCloserToTarget(newNode, target, slotSizeInSec, dataGetter);
    }
    return selectedActions;
  }

  private static List<Action> selectActionsCloserToTarget(Node node, Double target, int slotSizeInSec,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException {
    Double nodeSum = node.getSumAvailableActions(slotSizeInSec, dataGetter);
    Double currentAchieved = nodeSum + 1;
    List<Action> actions = new ArrayList<>();
    Enumeration e = node.preorderEnumeration();

    while (e.hasMoreElements()) {
      Node nodeToCheck = (Node) e.nextElement();
      if (nodeToCheck.isLeaf()) {
        Double nodeAvailableReduction = nodeToCheck.getSumAvailableActions(slotSizeInSec, dataGetter);
        if (nodeAvailableReduction < currentAchieved && nodeAvailableReduction >= target) {
          currentAchieved = nodeToCheck.getSumAvailableActions(slotSizeInSec, dataGetter);
          actions = nodeToCheck.getActions();
        }
      }
    }
    return actions;
  }

  private static Node processNode(Node node, Double target, int slotSizeInSec, GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException {

    Double nodeReduction = node.getSumAvailableActions(slotSizeInSec, dataGetter);
    if (nodeReduction > target) {
      if (node.canHaveChildren()) {
        List<Node> childernNodes = createChildrenWithReductionAboveThreshold(node, target, slotSizeInSec, dataGetter);
        for (Node childernNode : childernNodes) {
          processNode(childernNode, target, slotSizeInSec, dataGetter);
        }
      }
    } else {

    }

    return node;
  }

  private static List<Node> createChildrenWithReductionAboveThreshold(Node node, Double target, int slotSizeInSec,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException {
    List<Node> childrenNodes = new ArrayList<>();
    List<Action> parentActions = node.getActions();
    for (int i = 0; i < parentActions.size() - 1; i++) {
      List<Action> nodeActions = new ArrayList<>(parentActions);
      nodeActions.remove(i);
      Node childrenNode = new Node(nodeActions);
      if (childrenNode.getSumAvailableActions(slotSizeInSec, dataGetter) >= target) {
        // childrenNode.setParent(node);
        node.add(childrenNode);
        childrenNodes.add(childrenNode);
      }
    }
    return childrenNodes;
  }

}
