package com.wattics.vimsen.EDMSdatamanager;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.wattics.vimsen.utils.TimeHelpers;

public class EDMSMockDataGetter implements EDMSDataGetterInterface {

  public EDMSMockDataGetter() {
  }

  @Override
  public EDMSMeasurement getEDMSMeasurementFromVGWUrl(String urlRequest) throws EDMSDataGetterException {
    String[] splitUrl = urlRequest.split("&");
    String[] startDateStringArray = splitUrl[1].split("=");
    Instant startTime = new Instant(startDateStringArray[1]);
    // DateTime startTime =
    // TimeHelpers.convertStringToDateTime(startDateStringArray[1]);
    String[] endDateStringArray = splitUrl[2].split("=");
    Instant endTime = new Instant(endDateStringArray[1]);
    // DateTime endTime =
    // TimeHelpers.convertStringToDateTime(endDateStringArray[1]);

    long numberValues = 2 + (endTime.getMillis() - startTime.getMillis()) / (900 * 1000);

    EDMSMeasurement measurement = new EDMSMeasurement();
    measurement.Consumption = new ArrayList<>();
    measurement.ForecastConsumption = new ArrayList<>();

    DateTime firstTime = TimeHelpers.convertStringWithMsToDateTime(startDateStringArray[1]);
    Double consumptionValue = 10.0;

    for (int i = 0; i < numberValues; i++) {
      DateTime dateTimeSample = firstTime.plus(i * 900 * 1000);
      HashMap<DateTime, Double> consumption = new HashMap<DateTime, Double>();
      consumption.put(dateTimeSample, consumptionValue);
      measurement.Consumption.add(consumption);
    }

    DateTime forecastTime = firstTime.plus(1 * 24 * 60 * 60 * 1000);
    Double forecastValue = 5.0;

    for (int i = 0; i < numberValues; i++) {
      DateTime dateTimeSample = forecastTime.plus(i * 900 * 1000);
      HashMap<DateTime, Double> consumption = new HashMap<DateTime, Double>();
      consumption.put(dateTimeSample, forecastValue);
      measurement.ForecastConsumption.add(consumption);
    }

    return measurement;
  }

}
