package com.wattics.vimsen.general;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PlanStatusEnumTest {

  @Test
  public void toStringTest() {
    String actualPlanStatus = PlanStatusEnum.COMPLETED.toString();
    String expectedPlanStatus = "COMPLETED";

    Assert.assertTrue(actualPlanStatus.equals(expectedPlanStatus));
  }
}
