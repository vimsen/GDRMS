package com.wattics.vimsen.LDRMdatamanager;

public class LDRMRuleException extends Exception {

  private static final long serialVersionUID = 1L;

  public LDRMRuleException(String string) {
    super(string);
  }

  public LDRMRuleException(String string, Exception e) {
    super(string, e);
  }

  public LDRMRuleException(Exception e) {
    super(e);
  }
}
