package com.wattics.vimsen.LDRMdatamanager;

public interface ControllerRuleSenderInterface {

  public String sendNewConsumptionRule(String header, String ruleJson) throws LDRMRuleException;

}
