package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.entities.Prosumer;

public class DRReductionCalculatorTest {

  @Test
  public void generateBalancedDRPlanFromDSSRequest() {
    int numberProsumers = 2;
    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    for (int i = 0; i < numberProsumers; i++) {
      prosumers.add(new Prosumer());
    }
    int timeInterval = 900;
    DateTime startTime = new DateTime("2016-04-10T12:00:00+02:00");
    DateTime endTime = new DateTime("2016-04-10T12:45:00+02:00");
    Double[] reduction = new Double[] { 12.0, 3.0, 4.0 };
    Double[] forecastValuesPr1 = new Double[] { 4.0, 2.0, 10.0 };
    Double[] forecastValuesPr2 = new Double[] { 8.0, 4.0, 10.0 };
    Map<Long, Double> forecastPr1 = new TreeMap<Long, Double>();
    Map<Long, Double> forecastPr2 = new TreeMap<Long, Double>();
    for (int i = 0; i < forecastValuesPr1.length; i++) {
      Long time = startTime.getMillis() + timeInterval * i * 1000;
      forecastPr1.put(time, forecastValuesPr1[i]);
      forecastPr2.put(time, forecastValuesPr2[i]);
    }

    List<Map<Long, Double>> forecastValues = new ArrayList<Map<Long, Double>>();
    forecastValues.add(forecastPr1);
    forecastValues.add(forecastPr2);

    List<Double[]> prosumerReduction = DRReductionCalculator.createBalancedPlanPrimaryProsumersMap(reduction, prosumers,
        forecastValues, startTime, endTime, timeInterval);

    Double[] plannedPr1 = prosumerReduction.get(0);
    Double[] plannedPr2 = prosumerReduction.get(1);

    Assert.assertEquals(plannedPr1[0], 4.0);
    Assert.assertEquals(plannedPr2[0], 8.0);
    Assert.assertEquals(plannedPr1[1], 1.0);
    Assert.assertEquals(plannedPr2[1], 2.0);
    Assert.assertEquals(plannedPr1[2], 2.0);
    Assert.assertEquals(plannedPr2[2], 2.0);
  }

  @Test
  public void generateBalancedDRPlanFromDSSRequestThreeProsumers() {
    int numberProsumers = 3;
    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    for (int i = 0; i < numberProsumers; i++) {
      prosumers.add(new Prosumer());
    }
    int timeInterval = 900;
    DateTime startTime = new DateTime("2016-04-10T12:00:00+02:00");
    DateTime endTime = new DateTime("2016-04-10T12:30:00+02:00");
    Double[] reduction = new Double[] { 12.0, 3.0 };
    Double[] forecastValuesPr1 = new Double[] { 8.0, 2.0 };
    Double[] forecastValuesPr2 = new Double[] { 14.0, 4.0 };
    Double[] forecastValuesPr3 = new Double[] { 2.0, 3.0 };
    Map<Long, Double> forecastPr1 = new TreeMap<Long, Double>();
    Map<Long, Double> forecastPr2 = new TreeMap<Long, Double>();
    Map<Long, Double> forecastPr3 = new TreeMap<Long, Double>();
    for (int i = 0; i < forecastValuesPr1.length; i++) {
      Long time = startTime.getMillis() + timeInterval * i * 1000;
      forecastPr1.put(time, forecastValuesPr1[i]);
      forecastPr2.put(time, forecastValuesPr2[i]);
      forecastPr3.put(time, forecastValuesPr3[i]);
    }

    List<Map<Long, Double>> forecastValues = new ArrayList<Map<Long, Double>>();
    forecastValues.add(forecastPr1);
    forecastValues.add(forecastPr2);
    forecastValues.add(forecastPr3);

    List<Double[]> prosumerReduction = DRReductionCalculator.createBalancedPlanPrimaryProsumersMap(reduction, prosumers,
        forecastValues, startTime, endTime, timeInterval);

    Double[] plannedPr1 = prosumerReduction.get(0);
    Double[] plannedPr2 = prosumerReduction.get(1);
    Double[] plannedPr3 = prosumerReduction.get(2);

    Assert.assertEquals(plannedPr1[0], 4.0);
    Assert.assertEquals(plannedPr2[0], 7.0);
    Assert.assertEquals(plannedPr3[0], 1.0);
    Assert.assertEquals(plannedPr1[1], 0.66666666666666666, 0.00000001);
    Assert.assertEquals(plannedPr2[1], 1.33333333333333333, 0.00000001);
    Assert.assertEquals(plannedPr3[1], 1.0);
  }

  @Test
  public void generateBalancedDRPlanFromDSSRequestOneProsumer() {
    int numberProsumers = 1;
    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    for (int i = 0; i < numberProsumers; i++) {
      prosumers.add(new Prosumer());
    }
    int timeInterval = 900;
    DateTime startTime = new DateTime("2016-04-10T12:00:00+02:00");
    DateTime endTime = new DateTime("2016-04-10T12:30:00+02:00");
    Double[] reduction = new Double[] { 12.0, 3.0 };
    Double[] forecastValuesPr1 = new Double[] { 8.0, 2.0 };
    Map<Long, Double> forecastPr1 = new TreeMap<Long, Double>();
    for (int i = 0; i < forecastValuesPr1.length; i++) {
      Long time = startTime.getMillis() + timeInterval * i * 1000;
      forecastPr1.put(time, forecastValuesPr1[i]);
    }

    List<Map<Long, Double>> forecastValues = new ArrayList<Map<Long, Double>>();
    forecastValues.add(forecastPr1);

    List<Double[]> prosumerReduction = DRReductionCalculator.createBalancedPlanPrimaryProsumersMap(reduction, prosumers,
        forecastValues, startTime, endTime, timeInterval);
    Double[] plannedPr1 = prosumerReduction.get(0);

    Assert.assertEquals(plannedPr1[0], 12.0);
    Assert.assertEquals(plannedPr1[1], 3.0);
  }

  @Test
  public void generateBalancedDRPlanIfOneProsumerHasZeroForecast() {
    int numberProsumers = 2;
    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    for (int i = 0; i < numberProsumers; i++) {
      prosumers.add(new Prosumer());
    }
    int timeInterval = 900;
    DateTime startTime = new DateTime("2016-04-10T12:00:00+02:00");
    DateTime endTime = new DateTime("2016-04-10T12:30:00+02:00");
    Double[] reduction = new Double[] { 12.0, 3.0 };
    Double[] forecastValuesPr1 = new Double[] { 8.0, 2.0 };
    Double[] forecastValuesPr2 = new Double[] { 0.0, 0.0 };
    Map<Long, Double> forecastPr1 = new TreeMap<Long, Double>();
    Map<Long, Double> forecastPr2 = new TreeMap<Long, Double>();
    for (int i = 0; i < forecastValuesPr1.length; i++) {
      Long time = startTime.getMillis() + timeInterval * i * 1000;
      forecastPr1.put(time, forecastValuesPr1[i]);
      forecastPr2.put(time, forecastValuesPr2[i]);
    }

    List<Map<Long, Double>> forecastValues = new ArrayList<Map<Long, Double>>();
    forecastValues.add(forecastPr1);
    forecastValues.add(forecastPr2);

    List<Double[]> prosumerReduction = DRReductionCalculator.createBalancedPlanPrimaryProsumersMap(reduction, prosumers,
        forecastValues, startTime, endTime, timeInterval);
    Double[] plannedPr1 = prosumerReduction.get(0);
    Double[] plannedPr2 = prosumerReduction.get(1);

    Assert.assertEquals(plannedPr1[0], 12.0);
    Assert.assertEquals(plannedPr2[0], 0.0);
  }

  @Test
  public void generateBalancedDRPlanIfOneProsumerHasNullValues() {
    int numberProsumers = 2;
    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    for (int i = 0; i < numberProsumers; i++) {
      prosumers.add(new Prosumer());
    }
    int timeInterval = 900;
    DateTime startTime = new DateTime("2016-04-10T12:00:00+02:00");
    DateTime endTime = new DateTime("2016-04-10T12:30:00+02:00");
    Double[] reduction = new Double[] { 12.0, 3.0 };
    Double[] forecastValuesPr1 = new Double[] { 8.0, 2.0 };
    Double[] forecastValuesPr2 = new Double[] { null, null };
    Map<Long, Double> forecastPr1 = new TreeMap<Long, Double>();
    Map<Long, Double> forecastPr2 = new TreeMap<Long, Double>();
    for (int i = 0; i < forecastValuesPr1.length; i++) {
      Long time = startTime.getMillis() + timeInterval * i * 1000;
      forecastPr1.put(time, forecastValuesPr1[i]);
      forecastPr2.put(time, forecastValuesPr2[i]);
    }

    List<Map<Long, Double>> forecastValues = new ArrayList<Map<Long, Double>>();
    forecastValues.add(forecastPr1);
    forecastValues.add(forecastPr2);

    List<Double[]> prosumerReduction = DRReductionCalculator.createBalancedPlanPrimaryProsumersMap(reduction, prosumers,
        forecastValues, startTime, endTime, timeInterval);
    Double[] plannedPr1 = prosumerReduction.get(0);
    Double[] plannedPr2 = prosumerReduction.get(1);

    Assert.assertEquals(plannedPr1[0], 12.0);
    Assert.assertEquals(plannedPr2[0], 0.0);
  }

}
