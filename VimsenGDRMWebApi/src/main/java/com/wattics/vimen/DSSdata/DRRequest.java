package com.wattics.vimen.DSSdata;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DRRequest {

  public DateTime start_time;
  public String start_time_text;
  public int interval;
  public String unit;
  public Double[] target_reduction;
  public int[] prosumers_primary;
  public int[] prosumers_secondary;

}
