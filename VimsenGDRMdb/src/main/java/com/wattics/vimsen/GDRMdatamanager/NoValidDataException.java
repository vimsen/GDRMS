package com.wattics.vimsen.GDRMdatamanager;

public class NoValidDataException extends Exception {

  private static final long serialVersionUID = 1L;

  public NoValidDataException() {
  }

  public NoValidDataException(String message) {
    super(message);
  }

  public NoValidDataException(Throwable cause) {
    super(cause);
  }

  public NoValidDataException(String message, Throwable cause) {
    super(message, cause);
  }

}
