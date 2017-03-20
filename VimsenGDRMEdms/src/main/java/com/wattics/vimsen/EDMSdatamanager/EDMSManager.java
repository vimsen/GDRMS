package com.wattics.vimsen.EDMSdatamanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.TimeHelpers;

public class EDMSManager {

  public static Map<Long, Double> getKwhForecastConsumptionVGWMap(Prosumer prosumer, String equipmentName, Instant startTime,
      Instant endTime, int intervalDurationSec, EDMSDataGetterInterface dataGetter) throws EDMSDataGetterException {
    Instant startTimeDayAhead = startTime.minus(TimeHelpers.MS_IN_A_DAY);
    Instant endTimeDayAhead = endTime.minus(TimeHelpers.MS_IN_A_DAY);

    String prosumerReference = prosumer.getName();
    String preparedUrl = generateGetVGWUrl(prosumerReference, startTimeDayAhead, endTimeDayAhead, intervalDurationSec);
    EDMSMeasurement prosMeasurements = dataGetter.getEDMSMeasurementFromVGWUrl(preparedUrl);
    TreeMap<Long, Double> kwhForecastValues = getConsumptionForecastValuesFromEDMSMeasurementMap(prosMeasurements, startTime,
        endTime, intervalDurationSec);
    return kwhForecastValues;
  }

  private static TreeMap<Long, Double> getConsumptionForecastValuesFromEDMSMeasurementMap(EDMSMeasurement prosMeasurements,
      Instant startTime, Instant endTime, int timeInterval) {
    TreeMap<Long, Double> forecastSelection = new TreeMap<Long, Double>();
    List<HashMap<DateTime, Double>> forecastConsumption = prosMeasurements.ForecastConsumption;

    long offSet = timeInterval * 1000;

    if (forecastConsumption == null) {
      return forecastSelection;
    }

    Instant startBorder = startTime.plus(timeInterval * TimeHelpers.MS_IN_A_SEC).minus(TimeHelpers.MS_IN_A_SEC);
    Instant endBorder = endTime.plus(timeInterval * TimeHelpers.MS_IN_A_SEC);

    for (HashMap<DateTime, Double> forecastValue : forecastConsumption) {
      for (Entry<DateTime, Double> valueEntry : forecastValue.entrySet()) {

        if (valueEntry.getKey().isAfter(startBorder) & valueEntry.getKey().isBefore(endBorder)) {
          DateTime time = valueEntry.getKey();
          Double value = valueEntry.getValue();
          Long timeBeginningSlot = time.toInstant().getMillis() - offSet;
          forecastSelection.put(timeBeginningSlot, value);
        }
      }
    }

    return forecastSelection;
  }

  public static Map<Long, Double> getKwh15mnConsumptionVGWMap(Prosumer prosumer, String equipmentName, Instant startTime,
      Instant endTime, int intervalDurationSec, EDMSDataGetterInterface dataGetter) throws EDMSDataGetterException {
    Instant startTimeSecAhead = startTime.minus(TimeHelpers.MS_IN_A_SEC).minus(60 * 60 * 1000);
    Instant endTimeSecAhead = endTime.plus(60 * 60 * 1000);
    String prosumerReference = prosumer.getName();
    String preparedUrl = generateGetVGWUrl(prosumerReference, startTimeSecAhead, endTimeSecAhead, intervalDurationSec);

    EDMSMeasurement prosMeasurements = dataGetter.getEDMSMeasurementFromVGWUrl(preparedUrl);
    TreeMap<Long, Double> kwh15mnValues = getConsumptionValuesFromEDMSMeasurementMap(prosMeasurements, startTime, endTime,
        intervalDurationSec);
    return kwh15mnValues;
  }

  private static TreeMap<Long, Double> getConsumptionValuesFromEDMSMeasurementMap(EDMSMeasurement prosMeasurements,
      Instant startTime, Instant endTime, int timeInterval) {
    TreeMap<Long, Double> consumptionSelection = new TreeMap<Long, Double>();
    List<HashMap<DateTime, Double>> consumptions = prosMeasurements.Consumption;
    int offset = 1000 * timeInterval;
    Instant startBorder = startTime.plus(timeInterval * TimeHelpers.MS_IN_A_SEC).minus(TimeHelpers.MS_IN_A_SEC);
    Instant endBorder = endTime.plus(timeInterval * TimeHelpers.MS_IN_A_SEC);

    for (HashMap<DateTime, Double> currentValue : consumptions) {
      for (Entry<DateTime, Double> valueEntry : currentValue.entrySet()) {

        if (valueEntry.getKey().isAfter(startBorder) & valueEntry.getKey().isBefore(endBorder)) {
          Double value = valueEntry.getValue();
          DateTime time = valueEntry.getKey();
          Long timeBeginningSlot = time.toInstant().getMillis() - offset;
          consumptionSelection.put(timeBeginningSlot, value);
        }
      }
    }

    return consumptionSelection;
  }

  public static String generateGetVGWUrl(String prosumerName, Instant startTime, Instant endTime, int timeInterval) {
    String url = "https://beta.intelen.com/vimsenapi/EDMS_DSS/index.php/intelen/getdataVGW?";
    String prosumerUrl = "prosumers=" + prosumerName;
    String startdateUrl = "startdate=" + TimeHelpers.convertInstantToString(startTime);
    String enddateUrl = "enddate=" + TimeHelpers.convertInstantToString(endTime);
    String slotSize = "interval=" + timeInterval;
    String pointer = "pointer=2";

    String preparedUrl = url + prosumerUrl + "&" + startdateUrl + "&" + enddateUrl + "&" + slotSize + "&" + pointer;
    return preparedUrl;
  }

}
