package com.wattics.vimsen.LDRMdatamanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMEngine.DataPlanGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.general.DefaultSettings;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.JSONMapper;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;

public class ControllerRuleMapper {

  public static String mapPlanActionsInControllerRulesJsonMap(Plan plan, EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterInterface dataGetter) throws MapperException, DataAccessLayerException, EDMSDataGetterException {
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planHasActionSet = plan.getPlanHasActions();
    List<PlanHasAction> planHasActions = new ArrayList<PlanHasAction>(planHasActionSet);
    List<Rule> planRules = new ArrayList<Rule>();
    for (PlanHasAction planHasAction : planHasActions) {
      List<Rule> rules = mapPlannedActionInControllerRulesMap(planHasAction, edmsDataGetter, dataGetter);
      planRules.addAll(rules);
    }
    String jsonRule = serializeListRules(planRules);
    return jsonRule;
  }

  private static List<Rule> mapPlannedActionInControllerRulesMap(PlanHasAction planHasAction,
      EDMSDataGetterInterface edmsDataGetter, GDRMDataGetterInterface dataGetter)
      throws DataAccessLayerException, EDMSDataGetterException, MapperException {
    Action action = planHasAction.getAction();
    ControlSignal controlSignal = action.getControlSignal();
    Plan plan = planHasAction.getPlan();
    MarketSignal marketSignal = plan.getMarketSignal();

    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    Prosumer prosumer = dataGetter.getProsumerFromActionId(action.getId());
    prosumers.add(prosumer);

    List<Map<Long, Double>> forecastAmount = DataPlanGetter.getProsumersForecastConsumptionFromEDMSMap(prosumers,
        marketSignal.getStartTime(), marketSignal.getEndTime(), marketSignal.getTimeInterval(), edmsDataGetter);
    Double[] plannedAmount = FormatConverter.stringToArrayDouble(planHasAction.getPlannedAmount());

    List<Rule> rules = new ArrayList<Rule>();
    for (int i = 0; i < plannedAmount.length; i++) {

      Rule rule = generateRuleWithCommonParameters(controlSignal);
      Map<Long, Double> forecast = forecastAmount.get(0);
      Double[] forecastVector = DataPlanGetter.getOrderedValuesFromMap(forecast);
      try {
        rule = setTimeAndReduction(rule, planHasAction, marketSignal.getStartTimeText(), forecastVector[i], plannedAmount[i],
            i * marketSignal.getTimeInterval() * TimeHelpers.MS_IN_A_SEC,
            marketSignal.getTimeInterval() * TimeHelpers.MS_IN_A_SEC);
        rules.add(rule);
      } catch (LDRMRuleException e) {
        //TODO:
      }
    }
    return rules;
  }

  private static Rule setTimeAndReduction(Rule rule, PlanHasAction planHasAction, String startTimeText, Double forecast,
      double reduction, int startDelayInMs, int timeSlotSizeInMs) throws LDRMRuleException {

    DateTime startTimeRule = planHasAction.getTStart();
    startTimeRule = startTimeRule.plus(startDelayInMs);
    DateTime endTimeRule = startTimeRule.plus(timeSlotSizeInMs);

    rule.ruledate = TimeHelpers.getLocalDate(startTimeText, startTimeRule);
    rule.ruletime = TimeHelpers.getLocalTimeHourAndMinutes(startTimeText, startTimeRule);
    rule.ruletimeend = TimeHelpers.getLocalTimeHourAndMinutes(startTimeText, endTimeRule);
    try {
      Double maxAMount = forecast - reduction;
      rule.ruleMaxConsumption = maxAMount.intValue();
    } catch (NullPointerException e) {
      throw new LDRMRuleException("Not possible to map the rule. Null value", e);
    }

    return rule;
  }

  private static Rule generateRuleWithCommonParameters(ControlSignal controlSignal) {
    Rule rule = new Rule();
    rule.ruleControllerID = controlSignal.getRuleControllerId();
    rule.ruleguid = generateRandomSequenceString();
    rule.rulerepeat = false;
    rule.ruleRowConsumptionAction = 1;
    return rule;
  }

  private static String serializeRule(Rule rule) throws MapperException {
    String json = null;
    try {
      json = JSONMapper.objectMapper.writeValueAsString(rule);
    } catch (JsonProcessingException e) {
      // e.printStackTrace();
      throw new MapperException("Error to serialize DRRequest response: ", e);
    }
    return json;
  }

  private static String serializeListRules(List<Rule> rules) throws MapperException {
    String json = null;
    try {
      json = JSONMapper.objectMapper.writeValueAsString(rules);
    } catch (JsonProcessingException e) {
      // e.printStackTrace();
      throw new MapperException("Error to serialize DRRequest response: ", e);
    }
    return json;
  }

  private static String generateRandomSequenceString() {
    String randomString = RandomStringUtils.randomAlphanumeric(DefaultSettings.RULEGUID_LENGTH).toUpperCase();
    return randomString;
  }

}
