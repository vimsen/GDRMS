package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

/**
 * Plan generated by hbm2java
 */
public class Plan implements java.io.Serializable {

  private Integer id;
  private MarketSignal marketSignal;
  private String name;
  private DateTime date;
  private String type;
  @NotNull
  private String status;
  private Set planWeights = new HashSet(0);
  private Set planHasActions = new HashSet(0);

  public Plan() {
  }

  public Plan(MarketSignal marketSignal) {
    this.marketSignal = marketSignal;
  }

  public Plan(MarketSignal marketSignal, String name, DateTime date, String type, String status, Set planWeights,
      Set planHasActions) {
    this.marketSignal = marketSignal;
    this.name = name;
    this.date = date;
    this.type = type;
    this.status = status;
    this.planWeights = planWeights;
    this.planHasActions = planHasActions;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public MarketSignal getMarketSignal() {
    return this.marketSignal;
  }

  public void setMarketSignal(MarketSignal marketSignal) {
    this.marketSignal = marketSignal;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DateTime getDate() {
    return this.date;
  }

  public void setDate(DateTime date) {
    this.date = date;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Set getPlanWeights() {
    return this.planWeights;
  }

  public void setPlanWeights(Set planWeights) {
    this.planWeights = planWeights;
  }

  public Set getPlanHasActions() {
    return this.planHasActions;
  }

  public void setPlanHasActions(Set planHasActions) {
    this.planHasActions = planHasActions;
  }

}
