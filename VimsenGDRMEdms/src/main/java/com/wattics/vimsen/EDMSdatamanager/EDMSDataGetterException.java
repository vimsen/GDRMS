package com.wattics.vimsen.EDMSdatamanager;

public class EDMSDataGetterException extends Exception {

  private static final long serialVersionUID = 1L;

  public EDMSDataGetterException(String string) {
    super(string);
  }

  public EDMSDataGetterException(String string, Exception e) {
    super(string, e);
  }

  public EDMSDataGetterException(Exception e) {
    super(e);
  }
}
