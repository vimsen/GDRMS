package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Months;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterNoHib;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.LDRMdatamanager.ControllerRuleSenderInterface;
import com.wattics.vimsen.LDRMdatamanager.LDRMRuleException;
import com.wattics.vimsen.LDRMdatamanager.MqttControllerRuleMapper;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.MapperException;

public class PlanManager {

  public static void updatePlanIfNotUpToDateMap(String planId, GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer, EDMSDataGetterInterface edmsDataGetter)
      throws DataAccessLayerException, EDMSDataGetterException, NoValidDataException, MapperException, InterruptedException {
    int id = Integer.parseInt(planId);
    Plan plan = dataGetter.getPlan(id);
    MarketSignal marketSignal = plan.getMarketSignal();

    if (isPlanHistorical(marketSignal)) {
      SimplifiedPlanUpdater.updatePlan(dataGetter, dataStorer, edmsDataGetter, plan, marketSignal);
    } else {
      PlanUpdater.updatePlanMap(dataGetter, dataStorer, edmsDataGetter, plan, marketSignal);
    }
  }

  public static boolean isPlanHistorical(MarketSignal marketSignal) {
    DateTime now = DateTime.now();
    return (Math.abs(Months.monthsBetween(now, marketSignal.getStartTime()).getMonths()) > 6);
  }

  public static List<Plan> selectPlansInRegisteredStatusToBeProcessed(GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer) throws DataAccessLayerException, NoValidDataException {
    List<Plan> plans = dataGetter.getPlanInRegisteredStatus();
    List<Plan> planToBeProc = new ArrayList<Plan>();

    List<Plan> plansDay = plans;
    // List<Plan> plansDay =
    // PlanGenerator.selectPlansEndingBefore24HFromNow(plans);
    if (plansDay.size() > 0) {
      planToBeProc.add(plansDay.get(0));
    }

    return planToBeProc;
  }

  public static void setPlanStatusToInvalid(GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer,
      List<Plan> plans) throws DataAccessLayerException {
    List<Integer> plansId = GDRMDataGetterNoHib.getListPlansId(plans);
    for (Integer planId : plansId) {
      try {
        Plan plan = dataGetter.getPlan(planId);
        plan.setStatus(PlanStatusEnum.INVALID.toString());
        dataStorer.storePlan(plan);
      } catch (NoValidDataException e) {
        // TODO:

      }
    }
  }

  public static void setAllPlansStatusToInvalid(GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer, List<Plan> plans) throws DataAccessLayerException {
    for (Plan plan : plans) {
      try {
        setSinglePlanStatusToInvalid(dataGetter, dataStorer, plan);
      } catch (NoValidDataException e) {
        // TODO:
      }
    }
  }

  public static void setSinglePlanStatusToInvalid(GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer, Plan planDetail) throws DataAccessLayerException, NoValidDataException {
    Integer planId = planDetail.getId();
    Plan plan = dataGetter.getPlan(planId);
    plan.setStatus(PlanStatusEnum.INVALID.toString());
    dataStorer.storePlan(plan);
  }

  public static void generateAndStorePlansMap(List<Plan> plans, GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer)
      throws EDMSDataGetterException, MapperException, DataAccessLayerException, NoValidDataException {
    for (Plan plan : plans) {
      try {
        List<PlanHasAction> plannedActions = PlanGenerator.generatePlanActionsFromSla(plan.getId(), dataGetter);

        if (PlanGenerator.generatedActionsAreValid(plannedActions)) {
          PlanStorer.storeGeneratedPlanActions(plan.getId(), plannedActions, dataStorer);
        } else {
          setSinglePlanStatusToInvalid(dataGetter, dataStorer, plan);
        }
      } catch (NoValidDataException e) {
        setSinglePlanStatusToInvalid(dataGetter, dataStorer, plan);
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public static List<Pair<String, String>> generateControllerActionsSla(List<Integer> plansId,
      GDRMDataGetterAndValidationInterface dataGetter)
      throws LDRMRuleException, DataAccessLayerException, MapperException, EDMSDataGetterException, NoValidDataException {
    List<Pair<String, String>> rulesJson = MqttControllerRuleMapper.generatePlansControllerRulesSla(plansId, dataGetter);
    return rulesJson;
  }

  public static List<String> sendControllerActionsMap(ControllerRuleSenderInterface ruleSender,
      List<Pair<String, String>> controlActions) throws LDRMRuleException {
    List<String> messages = new ArrayList<>();
    if (controlActions.size() > 0) {
      for (Pair<String, String> rule : controlActions) {
        String header = rule.getLeft();
        String message = rule.getRight();
        String result = ruleSender.sendNewConsumptionRule(header, message);
        messages.add(result);
      }
    }
    return messages;
  }

  public static List<Plan> selectCreatedAndOngoingPlans(GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, NoValidDataException {
    return dataGetter.getPlanInCreatedAndOngoingStatus();

  }

}
