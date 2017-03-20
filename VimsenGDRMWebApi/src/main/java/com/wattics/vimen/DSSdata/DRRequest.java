package com.wattics.vimen.DSSdata;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DRRequest {

  public DateTime start_time;
  public String start_time_text;
  public int interval;
  public String unit;
  public Double[] target_values;
  public String[] prosumers_primary;
  public String[] prosumers_secondary;
  public String type;

}
