package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import org.joda.time.DateTime;

/**
 * MarketSignal generated by hbm2java
 */
public class MarketSignal implements java.io.Serializable {

  private Integer id;
  private DsoTerritory dsoTerritory;
  private DateTime startTime;
  private DateTime endTime;
  private String amountReduction;
  private DateTime signalDate;
  private Boolean achieved;
  private Integer timeInterval;
  private String unit;
  private String startTimeText;
  private Set plans = new HashSet(0);
  private Set dssSelectedProsumers = new HashSet(0);

  public MarketSignal() {
  }

  public MarketSignal(DsoTerritory dsoTerritory) {
    this.dsoTerritory = dsoTerritory;
  }

  public MarketSignal(DsoTerritory dsoTerritory, DateTime startTime, DateTime endTime, String amountReduction,
      DateTime signalDate, Boolean achieved, Integer timeInterval, String unit, String startTimeText, Set plans,
      Set dssSelectedProsumers) {
    this.dsoTerritory = dsoTerritory;
    this.startTime = startTime;
    this.endTime = endTime;
    this.amountReduction = amountReduction;
    this.signalDate = signalDate;
    this.achieved = achieved;
    this.timeInterval = timeInterval;
    this.unit = unit;
    this.startTimeText = startTimeText;
    this.plans = plans;
    this.dssSelectedProsumers = dssSelectedProsumers;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DsoTerritory getDsoTerritory() {
    return this.dsoTerritory;
  }

  public void setDsoTerritory(DsoTerritory dsoTerritory) {
    this.dsoTerritory = dsoTerritory;
  }

  public DateTime getStartTime() {
    return this.startTime;
  }

  public void setStartTime(DateTime startTime) {
    this.startTime = startTime;
  }

  public DateTime getEndTime() {
    return this.endTime;
  }

  public void setEndTime(DateTime endTime) {
    this.endTime = endTime;
  }

  public String getAmountReduction() {
    return this.amountReduction;
  }

  public void setAmountReduction(String amountReduction) {
    this.amountReduction = amountReduction;
  }

  public DateTime getSignalDate() {
    return this.signalDate;
  }

  public void setSignalDate(DateTime signalDate) {
    this.signalDate = signalDate;
  }

  public Boolean getAchieved() {
    return this.achieved;
  }

  public void setAchieved(Boolean achieved) {
    this.achieved = achieved;
  }

  public Integer getTimeInterval() {
    return this.timeInterval;
  }

  public void setTimeInterval(Integer timeInterval) {
    this.timeInterval = timeInterval;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getStartTimeText() {
    return this.startTimeText;
  }

  public void setStartTimeText(String startTimeText) {
    this.startTimeText = startTimeText;
  }

  public Set getPlans() {
    return this.plans;
  }

  public void setPlans(Set plans) {
    this.plans = plans;
  }

  public Set getDssSelectedProsumers() {
    return this.dssSelectedProsumers;
  }

  public void setDssSelectedProsumers(Set dssSelectedProsumers) {
    this.dssSelectedProsumers = dssSelectedProsumers;
  }

}
