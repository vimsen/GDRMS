package com.wattics.vimsen.utils;

public class MapperException extends Exception {

  private static final long serialVersionUID = 1L;

  public MapperException(String string) {
    super(string);
  }

  public MapperException(String string, Exception e) {
    super(string, e);
  }
}