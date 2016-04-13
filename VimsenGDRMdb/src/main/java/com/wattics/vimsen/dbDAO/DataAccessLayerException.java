package com.wattics.vimsen.dbDAO;

public class DataAccessLayerException extends Exception {

  private static final long serialVersionUID = 1L;

  public DataAccessLayerException() {
  }

  public DataAccessLayerException(String message) {
    super(message);
  }

  public DataAccessLayerException(Throwable cause) {
    super(cause);
  }

  public DataAccessLayerException(String message, Throwable cause) {
    super(message, cause);
  }

}
