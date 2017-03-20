package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;

public class PlanHelper {

  public static int getTotalNumberOfSlotsForMarketSignal(MarketSignal marketSignal) throws MapperException {
    String targetReductionString = marketSignal.getAmountReduction();
    Double[] targetReduction = FormatConverter.stringToArrayDouble(targetReductionString);
    return targetReduction.length;
  }

  public static Double[] computeActualDrMap(Map<Long, Double> forecastConsumption, Map<Long, Double> actualConsumption,
      DateTime startTime, Instant endTime, int intervalDuration) {

    HashMap<Long, Double> delivered = new HashMap<Long, Double>();

    List<Long> timestamps = TimeHelpers.getInitialSlotsTimeStamps(startTime, endTime.toDateTime(), intervalDuration);

    Set<Long> forecastTime = forecastConsumption.keySet();
    Set<Long> actualTime = actualConsumption.keySet();

    for (Long timestamp : timestamps) {
      Double deliveredAmount = null;
      if (forecastTime.contains(timestamp) && actualTime.contains(timestamp)) {
        deliveredAmount = forecastConsumption.get(timestamp) - actualConsumption.get(timestamp);
      }
      delivered.put(timestamp, deliveredAmount);
    }

    List<Double> deliveredList = new ArrayList<Double>();
    SortedSet<Long> timeKeys = new TreeSet<Long>(delivered.keySet());
    for (Long timeKey : timeKeys) {
      deliveredList.add(delivered.get(timeKey));
    }

    Double[] deliveredArray = deliveredList.toArray(new Double[deliveredList.size()]);

    return deliveredArray;
  }

  public static Double[] computeActualDrMapHistorical(Map<Long, Double> forecastConsumption, Map<Long, Double> actualConsumption,
      DateTime startTime, Instant endTime, int intervalDuration, int numberIntervalsToUpdate) {

    HashMap<Long, Double> delivered = new HashMap<Long, Double>();

    List<Long> timestamps = TimeHelpers.getInitialSlotsTimeStamps(startTime, endTime.toDateTime(), intervalDuration);

    Set<Long> forecastTime = forecastConsumption.keySet();
    Set<Long> actualTime = actualConsumption.keySet();

    for (Long timestamp : timestamps) {
      Double deliveredAmount = null;
      if (forecastTime.contains(timestamp) && actualTime.contains(timestamp)) {
        deliveredAmount = forecastConsumption.get(timestamp) - actualConsumption.get(timestamp);
      }
      delivered.put(timestamp, deliveredAmount);
    }

    List<Double> deliveredList = new ArrayList<Double>();
    SortedSet<Long> timeKeys = new TreeSet<Long>(delivered.keySet());
    for (Long timeKey : timeKeys) {
      deliveredList.add(delivered.get(timeKey));
    }

    Double[] deliveredArray = deliveredList.toArray(new Double[deliveredList.size()]);

    return Arrays.copyOfRange(deliveredArray, 0, numberIntervalsToUpdate);
  }

}
