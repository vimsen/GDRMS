package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import org.joda.time.DateTime;

/**
 * ActionSla generated by hbm2java
 */
public class ActionSla implements java.io.Serializable {

  private int actionId;
  private Action action;
  private Boolean mandatory;
  private Boolean dayAheadNotice;
  private Boolean intraDayNotice;
  private Double timeIntraDay;
  private Boolean runtimeNotice;
  private Boolean automated;
  private Boolean manual;
  private DateTime deadline;
  private Double consumptionTarget;
  private DateTime startResponsePeriod;
  private DateTime endResponsePeriod;
  private Double maximumDuration;
  private Double slaCapacity;
  private Integer maxNumberCall;
  private Integer maxNumberCallDaily;
  private Boolean actionRegistration;

  public ActionSla() {
  }

  public ActionSla(Action action) {
    this.action = action;
  }

  public ActionSla(Action action, Boolean mandatory, Boolean dayAheadNotice, Boolean intraDayNotice, Double timeIntraDay,
      Boolean runtimeNotice, Boolean automated, Boolean manual, DateTime deadline, Double consumptionTarget,
      DateTime startResponsePeriod, DateTime endResponsePeriod, Double maximumDuration, Double slaCapacity,
      Integer maxNumberCall, Integer maxNumberCallDaily, Boolean actionRegistration) {
    this.action = action;
    this.mandatory = mandatory;
    this.dayAheadNotice = dayAheadNotice;
    this.intraDayNotice = intraDayNotice;
    this.timeIntraDay = timeIntraDay;
    this.runtimeNotice = runtimeNotice;
    this.automated = automated;
    this.manual = manual;
    this.deadline = deadline;
    this.consumptionTarget = consumptionTarget;
    this.startResponsePeriod = startResponsePeriod;
    this.endResponsePeriod = endResponsePeriod;
    this.maximumDuration = maximumDuration;
    this.slaCapacity = slaCapacity;
    this.maxNumberCall = maxNumberCall;
    this.maxNumberCallDaily = maxNumberCallDaily;
    this.actionRegistration = actionRegistration;
  }

  public int getActionId() {
    return this.actionId;
  }

  public void setActionId(int actionId) {
    this.actionId = actionId;
  }

  public Action getAction() {
    return this.action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public Boolean getMandatory() {
    return this.mandatory;
  }

  public void setMandatory(Boolean mandatory) {
    this.mandatory = mandatory;
  }

  public Boolean getDayAheadNotice() {
    return this.dayAheadNotice;
  }

  public void setDayAheadNotice(Boolean dayAheadNotice) {
    this.dayAheadNotice = dayAheadNotice;
  }

  public Boolean getIntraDayNotice() {
    return this.intraDayNotice;
  }

  public void setIntraDayNotice(Boolean intraDayNotice) {
    this.intraDayNotice = intraDayNotice;
  }

  public Double getTimeIntraDay() {
    return this.timeIntraDay;
  }

  public void setTimeIntraDay(Double timeIntraDay) {
    this.timeIntraDay = timeIntraDay;
  }

  public Boolean getRuntimeNotice() {
    return this.runtimeNotice;
  }

  public void setRuntimeNotice(Boolean runtimeNotice) {
    this.runtimeNotice = runtimeNotice;
  }

  public Boolean getAutomated() {
    return this.automated;
  }

  public void setAutomated(Boolean automated) {
    this.automated = automated;
  }

  public Boolean getManual() {
    return this.manual;
  }

  public void setManual(Boolean manual) {
    this.manual = manual;
  }

  public DateTime getDeadline() {
    return this.deadline;
  }

  public void setDeadline(DateTime deadline) {
    this.deadline = deadline;
  }

  public Double getConsumptionTarget() {
    return this.consumptionTarget;
  }

  public void setConsumptionTarget(Double consumptionTarget) {
    this.consumptionTarget = consumptionTarget;
  }

  public DateTime getStartResponsePeriod() {
    return this.startResponsePeriod;
  }

  public void setStartResponsePeriod(DateTime startResponsePeriod) {
    this.startResponsePeriod = startResponsePeriod;
  }

  public DateTime getEndResponsePeriod() {
    return this.endResponsePeriod;
  }

  public void setEndResponsePeriod(DateTime endResponsePeriod) {
    this.endResponsePeriod = endResponsePeriod;
  }

  public Double getMaximumDuration() {
    return this.maximumDuration;
  }

  public void setMaximumDuration(Double maximumDuration) {
    this.maximumDuration = maximumDuration;
  }

  public Double getSlaCapacity() {
    return this.slaCapacity;
  }

  public void setSlaCapacity(Double slaCapacity) {
    this.slaCapacity = slaCapacity;
  }

  public Integer getMaxNumberCall() {
    return this.maxNumberCall;
  }

  public void setMaxNumberCall(Integer maxNumberCall) {
    this.maxNumberCall = maxNumberCall;
  }

  public Integer getMaxNumberCallDaily() {
    return this.maxNumberCallDaily;
  }

  public void setMaxNumberCallDaily(Integer maxNumberCallDaily) {
    this.maxNumberCallDaily = maxNumberCallDaily;
  }

  public Boolean getActionRegistration() {
    return this.actionRegistration;
  }

  public void setActionRegistration(Boolean actionRegistration) {
    this.actionRegistration = actionRegistration;
  }

}
