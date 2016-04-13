package com.wattics.vimsen.LDRMdatamanager;

public class ControllerRuleSenderMock implements ControllerRuleSenderInterface {

  public String sendNewConsumptionRule(String ruleJson) throws LDRMRuleException {
    return ruleJson;
  }

}
