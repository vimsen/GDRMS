package com.wattics.vimsen.GDRMEngine;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.utils.TimeHelpers;

public class Node extends DefaultMutableTreeNode {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private List<Action> actions;
  private Double sum;
  private boolean isSumComputed = false;

  public Node(List<Action> actions) {
    this.actions = actions;
  }

  public List<Action> getActions() {
    return actions;
  }

  public Double getSumAvailableActions(int slotSizeInSec, GDRMDataGetterAndValidationInterface dataGetter) {
    if (!isSumComputed) {
      computeSum(slotSizeInSec, dataGetter);
    }
    return sum;
  }

  private void computeSum(int slotSizeInSec, GDRMDataGetterAndValidationInterface dataGetter) {
    double slotTimeConversion = (TimeHelpers.SEC_IN_A_MIN * TimeHelpers.MIN_IN_A_HOUR) / slotSizeInSec;
    Double sum = 0.0;
    for (Action action : actions) {
      try {
        ActionSla actionSla = dataGetter.getActionSlaFromActionId(action.getId());
        Double actionReduction = actionSla.getConsumptionTarget();
        actionReduction = actionReduction / slotTimeConversion;
        sum = sum + actionReduction;
      } catch (NoValidDataException e) {
        // TODO: how to handle it?
      } catch (DataAccessLayerException e) {
        // TODO: error?
      }
    }
    this.sum = sum;
  }

  public boolean canHaveChildren() {
    return (actions.size() > 1);
  }

}
