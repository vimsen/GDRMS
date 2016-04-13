package com.wattics.vimen.DSSdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DRRequestAck {

  public String status;
  public int plan_id;

}
