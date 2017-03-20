package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * PlanWeight generated by hbm2java
 */
public class PlanWeight implements java.io.Serializable {

  private PlanWeightId id;
  private Plan plan;
  private ActionAttribute actionAttribute;
  private BigDecimal weight;

  public PlanWeight() {
  }

  public PlanWeight(PlanWeightId id, Plan plan, ActionAttribute actionAttribute) {
    this.id = id;
    this.plan = plan;
    this.actionAttribute = actionAttribute;
  }

  public PlanWeight(PlanWeightId id, Plan plan, ActionAttribute actionAttribute, BigDecimal weight) {
    this.id = id;
    this.plan = plan;
    this.actionAttribute = actionAttribute;
    this.weight = weight;
  }

  public PlanWeightId getId() {
    return this.id;
  }

  public void setId(PlanWeightId id) {
    this.id = id;
  }

  public Plan getPlan() {
    return this.plan;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public ActionAttribute getActionAttribute() {
    return this.actionAttribute;
  }

  public void setActionAttribute(ActionAttribute actionAttribute) {
    this.actionAttribute = actionAttribute;
  }

  public BigDecimal getWeight() {
    return this.weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

}
