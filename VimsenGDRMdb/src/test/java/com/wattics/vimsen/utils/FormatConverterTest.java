package com.wattics.vimsen.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FormatConverterTest {
  @Test
  public void convertStringToArraydouble() throws MapperException {
    String s = "[3.0, 2.0]";
    double[] array = FormatConverter.stringToArraydouble(s);

    Assert.assertEquals(array.length, 2);
  }

  @Test(expectedExceptions = MapperException.class)
  public void throwsExceptionForNaNValues() throws MapperException {
    String s = "[NaN2,NaN3]";
    FormatConverter.stringToArraydouble(s);
  }

  @Test
  public void convertStringToArrayDouble() throws MapperException {
    String s = "[3.0, null]";
    Double[] array = FormatConverter.stringToArrayDouble(s);

    Assert.assertEquals(array.length, 2);
    Assert.assertEquals(array[0], 3.0);
    Assert.assertNull(array[1]);
  }

}
