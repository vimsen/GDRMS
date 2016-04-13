package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterNoHib;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.LDRMdatamanager.ControllerRuleSender;
import com.wattics.vimsen.LDRMdatamanager.ControllerRuleSenderInterface;
import com.wattics.vimsen.LDRMdatamanager.LDRMRuleException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.MapperException;

public class PlanManager {

  public static void updatePlanIfNotUpToDateMap(String planId, GDRMDataGetterInterface dataGetter,
      GDRMDataStorerInterface dataStorer, EDMSDataGetterInterface edmsDataGetter)
      throws DataAccessLayerException, EDMSDataGetterException {
    int id = Integer.parseInt(planId);
    Plan plan = dataGetter.getPlan(id);
    MarketSignal marketSignal = plan.getMarketSignal();

    PlanUpdater.updatePlanMap(dataGetter, dataStorer, edmsDataGetter, plan, marketSignal);
  }

  public static List<Plan> selectPlansInRegisteredStatusToBeProcessed(GDRMDataGetterInterface dataGetter,
      GDRMDataStorerInterface dataStorer, EDMSDataGetterInterface edmsDataGetter) throws DataAccessLayerException {
    List<Plan> plans = GDRMDataGetterNoHib.getPlanInRegisteredStatus(dataGetter);
    List<Plan> planToBeProc = new ArrayList<Plan>();
    if (plans.size() > 0) {
      List<Plan> plansDay = plans;
      // List<Plan> plansDay =
      // PlanGenerator.selectPlansEndingBefore24HFromNow(plans);
      if (plansDay.size() > 0) {
        planToBeProc.add(plansDay.get(0));
      }
    }
    return planToBeProc;
  }

  public static void setPlanStatusToInvalid(GDRMDataGetterInterface dataGetter, GDRMDataStorerInterface dataStorer,
      List<Plan> plans) throws DataAccessLayerException {
    List<Integer> plansId = GDRMDataGetterNoHib.getListPlansId(plans);
    for (Integer planId : plansId) {
      Plan plan = dataGetter.getPlan(planId);
      plan.setStatus(PlanStatusEnum.INVALID.toString());
      dataStorer.storePlan(plan);
    }
  }

  public static void setAllPlansStatusToInvalid(GDRMDataGetterInterface dataGetter, GDRMDataStorerInterface dataStorer,
      List<Plan> plans) throws DataAccessLayerException {
    for (Plan plan : plans) {
      setSinglePlanStatusToInvalid(dataGetter, dataStorer, plan);
    }
  }

  public static void setSinglePlanStatusToInvalid(GDRMDataGetterInterface dataGetter, GDRMDataStorerInterface dataStorer,
      Plan planDetail) throws DataAccessLayerException {
    Integer planId = planDetail.getId();
    Plan plan = dataGetter.getPlan(planId);
    plan.setStatus(PlanStatusEnum.INVALID.toString());
    dataStorer.storePlan(plan);
  }

  public static void generateAndStorePlansMap(List<Plan> plans, GDRMDataGetterInterface dataGetter,
      GDRMDataStorerInterface dataStorer, EDMSDataGetterInterface edmsDataGetter)
      throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    for (Plan plan : plans) {
      List<PlanHasAction> plannedActions = PlanGenerator.generatePlanActionsMap(plan.getId(), dataGetter, edmsDataGetter);
      if (PlanGenerator.generatedActionsAreValid(plannedActions)) {
        PlanStorer.storeGeneratedPlanActions(plan.getId(), plannedActions, dataStorer);
      } else {
        setSinglePlanStatusToInvalid(dataGetter, dataStorer, plan);
      }
    }
  }

  public static List<String> generateAndSendControllerActionsMap(List<Integer> plansId, EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterInterface dataGetter, ControllerRuleSenderInterface ruleSender)
      throws LDRMRuleException, DataAccessLayerException, MapperException, EDMSDataGetterException {
    List<String> rulesJson = ControllerRuleSender.generatePlansControllerRulesMap(plansId, edmsDataGetter, dataGetter);
    if (rulesJson.size() > 0) {
      for (String rule : rulesJson) {
        ruleSender.sendNewConsumptionRule(rule);
      }
    }
    return rulesJson;
  }

}
