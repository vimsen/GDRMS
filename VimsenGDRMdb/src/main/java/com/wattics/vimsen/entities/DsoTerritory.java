package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * DsoTerritory generated by hbm2java
 */
public class DsoTerritory implements java.io.Serializable {

  private int id;
  private String dsoName;
  private String dsoTerritory;
  private Set marketSignals = new HashSet(0);
  private Set transformers = new HashSet(0);

  public DsoTerritory() {
  }

  public DsoTerritory(int id) {
    this.id = id;
  }

  public DsoTerritory(int id, String dsoName, String dsoTerritory, Set marketSignals, Set transformers) {
    this.id = id;
    this.dsoName = dsoName;
    this.dsoTerritory = dsoTerritory;
    this.marketSignals = marketSignals;
    this.transformers = transformers;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDsoName() {
    return this.dsoName;
  }

  public void setDsoName(String dsoName) {
    this.dsoName = dsoName;
  }

  public String getDsoTerritory() {
    return this.dsoTerritory;
  }

  public void setDsoTerritory(String dsoTerritory) {
    this.dsoTerritory = dsoTerritory;
  }

  public Set getMarketSignals() {
    return this.marketSignals;
  }

  public void setMarketSignals(Set marketSignals) {
    this.marketSignals = marketSignals;
  }

  public Set getTransformers() {
    return this.transformers;
  }

  public void setTransformers(Set transformers) {
    this.transformers = transformers;
  }

}