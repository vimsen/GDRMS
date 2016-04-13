package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

/**
 * StorableFootprint generated by hbm2java
 */
public class StorableFootprint implements java.io.Serializable {

  private int equipmentId;
  private Equipment equipment;
  private Double maximumCapacity;
  private Double dischargeRate;

  public StorableFootprint() {
  }

  public StorableFootprint(Equipment equipment) {
    this.equipment = equipment;
  }

  public StorableFootprint(Equipment equipment, Double maximumCapacity, Double dischargeRate) {
    this.equipment = equipment;
    this.maximumCapacity = maximumCapacity;
    this.dischargeRate = dischargeRate;
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

  public Double getMaximumCapacity() {
    return this.maximumCapacity;
  }

  public void setMaximumCapacity(Double maximumCapacity) {
    this.maximumCapacity = maximumCapacity;
  }

  public Double getDischargeRate() {
    return this.dischargeRate;
  }

  public void setDischargeRate(Double dischargeRate) {
    this.dischargeRate = dischargeRate;
  }

}