package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import org.joda.time.DateTime;

/**
 * DailyAvailableReductionId generated by hbm2java
 */
public class DailyAvailableReductionId implements java.io.Serializable {

  private DateTime dateRed;
  private DateTime startTime;

  public DailyAvailableReductionId() {
  }

  public DailyAvailableReductionId(DateTime dateRed, DateTime startTime) {
    this.dateRed = dateRed;
    this.startTime = startTime;
  }

  public DateTime getDateRed() {
    return this.dateRed;
  }

  public void setDateRed(DateTime dateRed) {
    this.dateRed = dateRed;
  }

  public DateTime getStartTime() {
    return this.startTime;
  }

  public void setStartTime(DateTime startTime) {
    this.startTime = startTime;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof DailyAvailableReductionId))
      return false;
    DailyAvailableReductionId castOther = (DailyAvailableReductionId) other;

    return ((this.getDateRed() == castOther.getDateRed()) || (this.getDateRed() != null && castOther.getDateRed() != null && this
        .getDateRed().equals(castOther.getDateRed())))
        && ((this.getStartTime() == castOther.getStartTime()) || (this.getStartTime() != null && castOther.getStartTime() != null && this
            .getStartTime().equals(castOther.getStartTime())));
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (getDateRed() == null ? 0 : this.getDateRed().hashCode());
    result = 37 * result + (getStartTime() == null ? 0 : this.getStartTime().hashCode());
    return result;
  }

}
