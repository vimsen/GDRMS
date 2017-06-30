package com.wattics.vimsen.LDRMdatamanager;

public class ControllerRuleSenderMock implements ControllerRuleSenderInterface {

  public String sendNewConsumptionRule(String header, String ruleJson) throws LDRMRuleException {
    return ruleJson;
  }
  
  public String sendNewConsumptionRule(String ruleJson) throws LDRMRuleException {
    return ruleJson;
  }
}
