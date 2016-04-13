package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

/**
 * ReschedulableFootprint generated by hbm2java
 */
public class ReschedulableFootprint implements java.io.Serializable {

  private int equipmentId;
  private Equipment equipment;
  private Integer lengthCycle;
  private Double kwhCycle;
  private String demandPattern;
  private Double minLoad;
  private Double maxLoad;

  public ReschedulableFootprint() {
  }

  public ReschedulableFootprint(Equipment equipment) {
    this.equipment = equipment;
  }

  public ReschedulableFootprint(Equipment equipment, Integer lengthCycle, Double kwhCycle, String demandPattern, Double minLoad,
      Double maxLoad) {
    this.equipment = equipment;
    this.lengthCycle = lengthCycle;
    this.kwhCycle = kwhCycle;
    this.demandPattern = demandPattern;
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

  public Integer getLengthCycle() {
    return this.lengthCycle;
  }

  public void setLengthCycle(Integer lengthCycle) {
    this.lengthCycle = lengthCycle;
  }

  public Double getKwhCycle() {
    return this.kwhCycle;
  }

  public void setKwhCycle(Double kwhCycle) {
    this.kwhCycle = kwhCycle;
  }

  public String getDemandPattern() {
    return this.demandPattern;
  }

  public void setDemandPattern(String demandPattern) {
    this.demandPattern = demandPattern;
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