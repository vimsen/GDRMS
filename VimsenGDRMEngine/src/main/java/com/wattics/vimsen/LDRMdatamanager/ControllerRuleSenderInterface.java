package com.wattics.vimsen.LDRMdatamanager;

public interface ControllerRuleSenderInterface {

  public String sendNewConsumptionRule(String ruleJson) throws LDRMRuleException;

}
