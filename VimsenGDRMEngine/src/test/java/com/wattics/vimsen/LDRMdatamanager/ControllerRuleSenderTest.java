package com.wattics.vimsen.LDRMdatamanager;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ControllerRuleSenderTest {

  private String getOneRule() {

    String json = "[{\"ruleMaxConsumption\": 7," + " \"ruleControllerID\": \"zwave.me\"," + " \"ruledate\": \"2016-01-28\", "
        + "\"ruletime\": \"12:35\", " + "\"ruletimeend\": \"12:40\", " + "\"rulerepeat\": false, "
        + "\"ruleRowConsumptionAction\": 1, " + "\"ruleguid\": \"U0TWJXOJPIKEV2MR\"} ]";
    return json;
  }

  private String getTwoRules() {
    String json = "[{\"ruleMaxConsumption\": 12, " + "\"ruleControllerID\": \"zwave.me\", " + "\"ruledate\": \"2016-01-23\", "
        + "\"ruletime\": \"17:35\", " + "\"ruletimeend\": \"17:40\", " + "\"rulerepeat\": false, "
        + "\"ruleRowConsumptionAction\": 1, " + "\"ruleguid\": \"ABC1234567290123456\"}, " + "{\"ruleMaxConsumption\": 5, "
        + "\"ruleControllerID\": \"zwave.me\", " + "\"ruledate\": \"2016-01-23\", " + "\"ruletime\": \"17:35\", "
        + "\"ruletimeend\": \"15:30\", " + "\"rulerepeat\": false, " + "\"ruleRowConsumptionAction\": 1, "
        + "\"ruleguid\": \"ABC1234567890153456\"} ]";
    return json;
  }

  private String getCustomRule() {
    String json = "[{\"ruleMaxConsumption\": 7," + " \"ruleControllerID\": \"openhab\"," + " \"ruledate\": \"2016-03-22\", "
        + "\"ruletime\": \"14:45\", " + "\"ruletimeend\": \"15:00\", " + "\"rulerepeat\": false, "
        + "\"ruleRowConsumptionAction\": 1, " + "\"ruleguid\": \"U0TWJXOJIKEUV2MR\"} ]";
    return json;
  }

  @Test
  public void sendLDRMControl() throws LDRMRuleException {
    String json = getCustomRule();
    ControllerRuleSenderInterface ruleSender = new ControllerRuleSender();
    ruleSender.sendNewConsumptionRule(json);
    String response = ruleSender.sendNewConsumptionRule(json);

    String expectedResponse = "{\"status\":false,\"message\":\"Rule exists\"}";
    System.out.println(response);

    Assert.assertEquals(response, expectedResponse);
  }

  @Test
  public void responseRuleExistIfOneRulesIsSentTwice() throws LDRMRuleException {
    String json = getOneRule();
    ControllerRuleSenderInterface ruleSender = new ControllerRuleSender();
    ruleSender.sendNewConsumptionRule(json);
    String response = ruleSender.sendNewConsumptionRule(json);

    String expectedResponse = "{\"status\":false,\"message\":\"Rule exists\"}";

    Assert.assertEquals(response, expectedResponse);
  }

  @Test
  public void responseRuleExistIfTwoRulesAreSentTwice() throws LDRMRuleException {
    String json = getTwoRules();
    ControllerRuleSenderInterface ruleSender = new ControllerRuleSender();
    ruleSender.sendNewConsumptionRule(json);
    String response = ruleSender.sendNewConsumptionRule(json);

    String expectedResponse = "{\"status\":false,\"message\":\"Rule exists\"}";

    Assert.assertEquals(response, expectedResponse);
  }

}
