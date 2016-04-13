package com.wattics.vimsen.EDMSdatamanager;

import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EDMSMeasurement {

  public String ProsumerId;
  public List<HashMap<DateTime, Double>> Production;
  public List<HashMap<DateTime, Double>> Storage;
  public List<HashMap<DateTime, Double>> Consumption;

  public List<HashMap<DateTime, Double>> ForecastConsumption;
  public List<HashMap<DateTime, Double>> ForecastProduction;

}
