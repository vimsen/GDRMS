package com.wattics.vimsen.GDRMEngine;

import java.util.Random;

import org.testng.annotations.Test;

public class SimplifiedPlanUpdaterTest {

  private static final double MIN_PERCENTAGE_ADJ = 1;
  private static final double MAX_PERCENTAGE_ADJ = 10;
  private static Random rn = new Random();

  @Test
  public void testAddingToleranceToValue() {
    Double value = 53.2;
    Double percentageAdjustment = MIN_PERCENTAGE_ADJ + (MAX_PERCENTAGE_ADJ - MIN_PERCENTAGE_ADJ) * rn.nextDouble();
    Double adjustment = value * percentageAdjustment / 100.0;
    Double newValue = value + adjustment;
    System.out.println(newValue);
  }
}
