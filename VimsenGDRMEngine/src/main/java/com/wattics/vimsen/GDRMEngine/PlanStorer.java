package com.wattics.vimsen.GDRMEngine;

import java.util.List;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.general.PlanStatusEnum;

public class PlanStorer {

  public static void storeGeneratedPlanActions(int planId, List<PlanHasAction> plannedActions, GDRMDataStorerInterface dataStorer)
      throws DataAccessLayerException {
    for (PlanHasAction plannedAction : plannedActions) {
      dataStorer.storePlanHasAction(plannedAction);
    }
    PlanStorer.setPlanStatus(planId, PlanStatusEnum.CREATED.toString(), dataStorer);
  }

  private static void setPlanStatus(int planId, String newStatus, GDRMDataStorerInterface dataStorer)
      throws DataAccessLayerException {
    dataStorer.updatePlanStatus(planId, newStatus);
  }

}
