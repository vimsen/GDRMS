package com.wattics.vimen.DSSdata;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanCurrentStatus {

  public String status;
  public String start_time;
  public int plan_id;
  public int interval;
  public String unit;
  public HashMap<Integer, Double[]> planned_dr;
  public HashMap<Integer, Double[]> actual_dr;

}
