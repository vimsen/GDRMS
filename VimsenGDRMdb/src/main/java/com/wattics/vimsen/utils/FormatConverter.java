package com.wattics.vimsen.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

public class FormatConverter {

  public static double[] stringToArraydouble(String s) throws MapperException {
    List<Double> arrayValues;
    try {
      arrayValues = JSONMapper.objectMapper.readValue(s, new TypeReference<List<Double>>() {
      });
    } catch (IOException e) {
      throw new MapperException("Cannot convert string format to array of double: ", e);
    }

    double[] array = new double[arrayValues.size()];
    for (int i = 0; i < arrayValues.size(); i++) {
      double value = arrayValues.get(i);
      array[i] = value;
    }

    return array;
  }

  public static Double[] stringToArrayDouble(String s) throws MapperException {
    Double[] array;
    try {
      array = JSONMapper.objectMapper.readValue(s, Double[].class);
    } catch (IOException e) {
      throw new MapperException("Cannot convert string format to array of double: ", e);
    }

    return array;
  }

}
