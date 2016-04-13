package com.wattics.vimsen.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoadConverterTest {

  @Test
  public void convertKwToKwh15MnInterval() {
    Double value = 40.0;
    int intervalSec = 15 * 60;

    Double energy = LoadConverter.convertKwToKwh(value, intervalSec);

    Assert.assertEquals(energy, 10.0);
  }

  @Test
  public void convertKwhToKw15MnInterval() {
    Double value = 10.0;
    int intervalSec = 15 * 60;

    Double load = LoadConverter.convertKwhToKw(value, intervalSec);

    Assert.assertEquals(load, 40.0);
  }
}
