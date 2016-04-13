package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.TimeHelpers;

public class DRReductionCalculator {

  public static List<Double[]> createBalancedPlanPrimaryProsumersMap(Double[] targetReduction, List<Prosumer> prosumers,
      List<Map<Long, Double>> forecastAmount, DateTime startTime, DateTime endTime, Integer timeInterval) {

    List<Double[]> orderedForecasts = new ArrayList<Double[]>();
    List<Long> timestamps = TimeHelpers.getInitialSlotsTimeStamps(startTime, endTime, timeInterval);

    for (Map<Long, Double> forecastMap : forecastAmount) {
      Set<Long> forecastKeys = forecastMap.keySet();
      Double[] values = new Double[timestamps.size()];
      for (int i = 0; i < timestamps.size(); i++) {
        Long time = timestamps.get(i);
        if (forecastKeys.contains(time)) {
          values[i] = forecastMap.get(time);
        }
      }
      orderedForecasts.add(values);
    }

    List<Double[]> balancedPlan = createBalancedPlanPrimaryProsumers(targetReduction, prosumers, orderedForecasts);

    return balancedPlan;
  }


  private static List<Double[]> createBalancedPlanPrimaryProsumers(Double[] targetReduction, List<Prosumer> prosumers,
      List<Double[]> forecastAmounts) {
    List<Double[]> paddedForecast = padForecastAmount(forecastAmounts, targetReduction.length);
    List<Double[]> cleanForecast = convertNullValuesInZeros(paddedForecast);
    List<Double[]> plannedReductions = new ArrayList<Double[]>();
    for (int i = 0; i < prosumers.size(); i++) {
      plannedReductions.add(new Double[targetReduction.length]);
    }
    for (int i = 0; i < targetReduction.length; i++) {
      double totalForecast = 0;
      for (Double[] forecastAmount : cleanForecast) {
        totalForecast = totalForecast + forecastAmount[i];
      }
      for (int j = 0; j < plannedReductions.size(); j++) {
        Double[] values = plannedReductions.get(j);
        Double[] forecastAmount = cleanForecast.get(j);
        values[i] = targetReduction[i] * forecastAmount[i] / totalForecast;
        plannedReductions.set(j, values);
      }
    }
    return plannedReductions;
  }

  private static List<Double[]> convertNullValuesInZeros(List<Double[]> paddedForecast) {
    List<Double[]> cleanForecasts = new ArrayList<Double[]>();
    for (Double[] forecast : paddedForecast) {
      Double[] cleanForecast = new Double[forecast.length];
      for (int i = 0; i < forecast.length; i++) {
        if (forecast[i] == null) {
          cleanForecast[i] = 0.0;
        } else {
          cleanForecast[i] = forecast[i];
        }
      }
      cleanForecasts.add(cleanForecast);
    }
    return cleanForecasts;
  }

  private static List<Double[]> padForecastAmount(List<Double[]> forecastAmounts, int length) {
    List<Double[]> paddedForecasts = new ArrayList<Double[]>();
    for (Double[] forecast : forecastAmounts) {
      Double[] paddedForecast = new Double[length];
      for (int i = 0; i < length; i++) {
        if (i < forecast.length) {
          paddedForecast[i] = forecast[i];
        } else {
          paddedForecast[i] = 0.0;
        }
      }
      paddedForecasts.add(paddedForecast);
    }
    return paddedForecasts;
  }


}
