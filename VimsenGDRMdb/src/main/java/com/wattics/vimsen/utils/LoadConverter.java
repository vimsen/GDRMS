package com.wattics.vimsen.utils;

public class LoadConverter {

  public static Double convertKwToKwh(Double load, int intervalSizeInSec) {
    int intervalSizeInMin = intervalSizeInSec / TimeHelpers.SEC_IN_A_MIN;
    int conversionFactor = TimeHelpers.MIN_IN_A_HOUR / intervalSizeInMin;

    Double energy = load / conversionFactor;
    return energy;
  }

  public static Double convertKwhToKw(Double energy, int intervalSizeInSec) {
    int intervalSizeInMin = intervalSizeInSec / TimeHelpers.SEC_IN_A_MIN;
    int conversionFactor = TimeHelpers.MIN_IN_A_HOUR / intervalSizeInMin;
    Double load = null;
    if (energy != null) {
      load = energy * conversionFactor;
    }
    return load;
  }

  public static Double[] convertArrayKwToKwh(Double[] load, int intervalSizeInSec) {
    Double[] energy = new Double[load.length];
    for (int i = 0; i < load.length; i++) {
      energy[i] = convertKwToKwh(load[i], intervalSizeInSec);
    }
    return energy;
  }

  public static Double[] convertArrayKwhToKw(Double[] energy, int intervalSizeInSec) {
    Double[] load = new Double[energy.length];
    for (int i = 0; i < energy.length; i++) {
      load[i] = convertKwhToKw(energy[i], intervalSizeInSec);
    }
    return load;
  }

  public static double[] convertArraydoubleKwhToKw(double[] energy, int intervalSizeInSec) {
    double[] load = new double[energy.length];
    for (int i = 0; i < energy.length; i++) {
      load[i] = convertKwhToKw(energy[i], intervalSizeInSec);
    }
    return load;
  }

}
