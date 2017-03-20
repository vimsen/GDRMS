package com.wattics.vimsen.LDRMdatamanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.JSONMapper;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;

public class MqttControllerRuleMapper {

  public static List<Pair<String, String>> generatePlansControllerRulesSla(List<Integer> plansId,
      GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, MapperException, EDMSDataGetterException, NoValidDataException {
    List<Pair<String, String>> controllerRules = new ArrayList<Pair<String, String>>();
    for (int planId : plansId) {
      Plan plan = dataGetter.getPlan(planId);
      List<Pair<String, String>> controllerRule = MqttControllerRuleMapper.mapPlanActionsInControllerRulesJsonMap(plan,
          dataGetter);
      controllerRules.addAll(controllerRule);
    }
    return controllerRules;
  }

  public static List<Pair<String, String>> mapPlanActionsInControllerRulesJsonMap(Plan plan,
      GDRMDataGetterAndValidationInterface dataGetter) throws MapperException, DataAccessLayerException {
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planHasActionSet = plan.getPlanHasActions();
    MarketSignal marketSignal = plan.getMarketSignal();
    List<PlanHasAction> planHasActions = new ArrayList<PlanHasAction>(planHasActionSet);
    HashMap<Pair<String, Long>, List<PlanHasAction>> actionsGroupedByStartTime = groupActionsSameSiteStartingAtTheSameTime(
        planHasActions, marketSignal.getTimeInterval() * TimeHelpers.MS_IN_A_SEC, dataGetter);
    List<Pair<String, String>> planRules = new ArrayList<Pair<String, String>>();
    for (Map.Entry<Pair<String, Long>, List<PlanHasAction>> groupActions : actionsGroupedByStartTime.entrySet()) {
      Pair<String, String> rules;
      try {
        rules = mapPlannedActionInControllerRulesMap(plan, groupActions.getValue(), dataGetter);
        planRules.add(rules);
      } catch (DataAccessLayerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (MapperException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NoValidDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    return planRules;
  }

  private static HashMap<Pair<String, Long>, List<PlanHasAction>> groupActionsSameSiteStartingAtTheSameTime(
      List<PlanHasAction> planHasActions, int slotSizeMs, GDRMDataGetterAndValidationInterface dataGetter)
      throws MapperException, DataAccessLayerException {
    HashMap<Pair<String, Long>, List<PlanHasAction>> groupedActions = new HashMap<Pair<String, Long>, List<PlanHasAction>>();
    for (PlanHasAction planAction : planHasActions) {
      try {
        Long startTime = getStartingTime(planAction, slotSizeMs).getMillis();
        String mac = getMac(planAction.getAction().getId(), dataGetter);
        Pair<String, Long> actionKey = new ImmutablePair<String, Long>(mac, startTime);
        if (!groupedActions.containsKey(actionKey)) {
          List<PlanHasAction> actions = new ArrayList<>();
          actions.add(planAction);

          groupedActions.put(actionKey, actions);
        } else {
          groupedActions.get(actionKey).add(planAction);
        }
      } catch (NoValidDataException e) {
        // TODO:
      } catch (DataAccessLayerException e) {
        // TODO:
      }
    }

    return groupedActions;
  }

  public static Pair<String, String> mapPlannedActionInControllerRulesMap(Plan plan, List<PlanHasAction> groupActions,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException, NoValidDataException, MapperException {
    MarketSignal marketSignal = plan.getMarketSignal();
    String topic = getTopic(groupActions.get(0), dataGetter);
    MqttRule message = getMqttRule(groupActions, marketSignal.getTimeInterval() * TimeHelpers.MS_IN_A_SEC, dataGetter);
    String jsonMessage = serializeMqttRule(message);
    Pair<String, String> rule = new ImmutablePair<String, String>(topic, jsonMessage);
    return rule;
  }

  public static String serializeMqttRule(MqttRule message) {
    String jsonMessage = null;
    try {
      jsonMessage = JSONMapper.objectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // TODO: check if it has to be in byte format
    return jsonMessage;
  }

  private static MqttRule getMqttRule(List<PlanHasAction> groupActions, int slotSizeMs,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException, MapperException, NoValidDataException {
    List<Integer> actionIds = new ArrayList<>();
    for (PlanHasAction planAction : groupActions) {
      actionIds.add(planAction.getAction().getId());
    }
    MqttRule mqttRule = new MqttRule();
    mqttRule.dr = "OFF";

    mqttRule.devices = getDevicesName(dataGetter, actionIds);
    mqttRule.date = getDate(groupActions.get(0), dataGetter);
    mqttRule.time = getLocalTime(groupActions.get(0), slotSizeMs, dataGetter);
    return mqttRule;
  }

  // TODO: not used at the moment because only off actions are supported
  @SuppressWarnings("unused")
  private static String getDrAction(GDRMDataGetterAndValidationInterface dataGetter, int actionId)
      throws DataAccessLayerException, NoValidDataException {
    ControlSignal controlSignal = dataGetter.getControlSignalFromActionId(actionId);
    String name = controlSignal.getType();
    return name;
  }

  private static String[] getDevicesName(GDRMDataGetterAndValidationInterface dataGetter, List<Integer> actionIds)
      throws DataAccessLayerException, NoValidDataException {
    String[] deviceNames = new String[actionIds.size()];
    for (int i = 0; i < actionIds.size(); i++) {
      int actionId = actionIds.get(i);
      Equipment equipment = dataGetter.getEquipmentFromActionId(actionId);
      String deviceName = equipment.getName();
      deviceNames[i] = deviceName;
    }
    return deviceNames;
  }

  private static String getDate(PlanHasAction planHasAction, GDRMDataGetterAndValidationInterface dataGetter) {
    DateTime datetime = planHasAction.getTStart();
    String date = TimeHelpers.convertDateTimeToDateString(datetime);
    return date;
  }

  private static String getLocalTime(PlanHasAction planHasAction, int slotSizeMs, GDRMDataGetterAndValidationInterface dataGetter)
      throws MapperException {
    Plan plan = planHasAction.getPlan();
    MarketSignal marketSignal = plan.getMarketSignal();
    String startTimeText = marketSignal.getStartTimeText();
    DateTime startTimeRule = getStartingTime(planHasAction, slotSizeMs);
    String localTime = TimeHelpers.getLocalTimeHourAndMinutes(startTimeText, startTimeRule);
    return localTime;
  }

  private static DateTime getStartingTime(PlanHasAction planHasAction, int slotSizeMs) throws MapperException {
    Double[] plannedReduction = FormatConverter.stringToArrayDouble(planHasAction.getPlannedAmount());
    int startIndex = getFirstIndexElementHigherThanZero(plannedReduction);
    DateTime time = planHasAction.getTStart();
    DateTime startControlAction = time.plus(startIndex * slotSizeMs);
    return startControlAction;
  }

  private static int getFirstIndexElementHigherThanZero(Double[] plannedReduction) {
    int index = 0;
    for (int i = 0; i < plannedReduction.length; i++) {
      int checkIndex = plannedReduction.length - 1 - i;
      if (plannedReduction[checkIndex] > 0) {
        index = checkIndex;
      }
    }
    return index;
  }

  private static String getTopicComposed(PlanHasAction planHasAction, GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, NoValidDataException {
    int actionId = planHasAction.getAction().getId();
    String user = getUser(actionId, dataGetter);
    String mac = getMac(actionId, dataGetter);
    String device = "GDRM";
    String topic = user + "/" + mac + "/" + device;
    return topic;
  }

  private static String getTopic(PlanHasAction planHasAction, GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, NoValidDataException {
    int actionId = planHasAction.getAction().getId();
    Prosumer prosumer = getProsumerFromActionId(actionId, dataGetter);
    String topic = prosumer.getSurname();
    return topic;
  }

  private static String getMac(int actionId, GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, NoValidDataException {
    Prosumer prosumer = getProsumerFromActionId(actionId, dataGetter);
    String user = prosumer.getName();
    return user;
  }

  private static String getUser(int actionId, GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, NoValidDataException {
    Prosumer prosumer = getProsumerFromActionId(actionId, dataGetter);
    String user = prosumer.getSurname();
    return user;
  }

  private static Prosumer getProsumerFromActionId(int actionId, GDRMDataGetterAndValidationInterface dataGetter)
      throws DataAccessLayerException, NoValidDataException {
    Prosumer prosumer = dataGetter.getProsumerFromActionId(actionId);
    return prosumer;
  }

}
