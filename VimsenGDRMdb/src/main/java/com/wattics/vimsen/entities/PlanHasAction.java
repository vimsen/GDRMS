package com.wattics.vimsen.entities;

import javax.validation.constraints.NotNull;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import org.joda.time.DateTime;

/**
 * PlanHasAction generated by hbm2java
 */
public class PlanHasAction implements java.io.Serializable {

  private PlanHasActionId id;
  private Plan plan;
  private Action action;
  @NotNull
  private DateTime TStart;
  private DateTime TEnd;
  @NotNull
  private String plannedAmount;
  private Boolean implemented;
  private String signalValue;
  private String actualAmount;
  private DateTime updatedActualAt;
  @NotNull
  private Integer amountRecProgressToSend;
  @NotNull
  private Integer amountRecProgressSent;

  public PlanHasAction() {
  }

  public PlanHasAction(PlanHasActionId id, Plan plan, Action action) {
    this.id = id;
    this.plan = plan;
    this.action = action;
  }

  public PlanHasAction(PlanHasActionId id, Plan plan, Action action, DateTime TStart, DateTime TEnd, String plannedAmount,
      Boolean implemented, String signalValue, String actualAmount, DateTime updatedActualAt, Integer amountRecProgressToSend,
      Integer amountRecProgressSent) {
    this.id = id;
    this.plan = plan;
    this.action = action;
    this.TStart = TStart;
    this.TEnd = TEnd;
    this.plannedAmount = plannedAmount;
    this.implemented = implemented;
    this.signalValue = signalValue;
    this.actualAmount = actualAmount;
    this.updatedActualAt = updatedActualAt;
    this.setAmountRecProgressToSend(amountRecProgressToSend);
    this.setAmountRecProgressSent(amountRecProgressSent);
  }

  public PlanHasActionId getId() {
    return this.id;
  }

  public void setId(PlanHasActionId id) {
    this.id = id;
  }

  public Plan getPlan() {
    return this.plan;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public Action getAction() {
    return this.action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public DateTime getTStart() {
    return this.TStart;
  }

  public void setTStart(DateTime TStart) {
    this.TStart = TStart;
  }

  public DateTime getTEnd() {
    return this.TEnd;
  }

  public void setTEnd(DateTime TEnd) {
    this.TEnd = TEnd;
  }

  public String getPlannedAmount() {
    return this.plannedAmount;
  }

  public void setPlannedAmount(String plannedAmount) {
    this.plannedAmount = plannedAmount;
  }

  public Boolean getImplemented() {
    return this.implemented;
  }

  public void setImplemented(Boolean implemented) {
    this.implemented = implemented;
  }

  public String getSignalValue() {
    return this.signalValue;
  }

  public void setSignalValue(String signalValue) {
    this.signalValue = signalValue;
  }

  public String getActualAmount() {
    return this.actualAmount;
  }

  public void setActualAmount(String actualAmount) {
    this.actualAmount = actualAmount;
  }

  public DateTime getUpdatedActualAt() {
    return this.updatedActualAt;
  }

  public void setUpdatedActualAt(DateTime updatedActualAt) {
    this.updatedActualAt = updatedActualAt;
  }

  public Integer getAmountRecProgressToSend() {
    return amountRecProgressToSend;
  }

  public void setAmountRecProgressToSend(Integer amountRecProgressToSend) {
    this.amountRecProgressToSend = amountRecProgressToSend;
  }

  public Integer getAmountRecProgressSent() {
    return amountRecProgressSent;
  }

  public void setAmountRecProgressSent(Integer amountRecProgressSent) {
    this.amountRecProgressSent = amountRecProgressSent;
  }

}
