package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

/**
 * ReducibleFootprint generated by hbm2java
 */
public class ReducibleFootprint implements java.io.Serializable {

  private int equipmentId;
  private Equipment equipment;
  private Double minLoad;
  private Double maxLoad;

  public ReducibleFootprint() {
  }

  public ReducibleFootprint(Equipment equipment) {
    this.equipment = equipment;
  }

  public ReducibleFootprint(Equipment equipment, Double minLoad, Double maxLoad) {
    this.equipment = equipment;
    this.minLoad = minLoad;
    this.maxLoad = maxLoad;
  }

  public int getEquipmentId() {
    return this.equipmentId;
  }

  public void setEquipmentId(int equipmentId) {
    this.equipmentId = equipmentId;
  }

  public Equipment getEquipment() {
    return this.equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }

  public Double getMinLoad() {
    return this.minLoad;
  }

  public void setMinLoad(Double minLoad) {
    this.minLoad = minLoad;
  }

  public Double getMaxLoad() {
    return this.maxLoad;
  }

  public void setMaxLoad(Double maxLoad) {
    this.maxLoad = maxLoad;
  }

}