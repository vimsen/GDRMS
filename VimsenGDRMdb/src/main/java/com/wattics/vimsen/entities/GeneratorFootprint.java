package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

/**
 * GeneratorFootprint generated by hbm2java
 */
public class GeneratorFootprint implements java.io.Serializable {

  private int equipmentId;
  private Equipment equipment;
  private Integer nominalOperatingVoltageDc;
  private Integer operatingVoltageMinDc;
  private Integer operatingVoltageMaxDc;
  private Float maxInputCurrentDc;
  private Integer nominalOperatingVoltageAc;
  private Integer nominalOperatingFrequencyAc;
  private Integer nominalOutputPowerAc;
  private Integer maxOutputPowerAc;
  private Integer maxOutputCurrentAc;

  public GeneratorFootprint() {
  }

  public GeneratorFootprint(Equipment equipment) {
    this.equipment = equipment;
  }

  public GeneratorFootprint(Equipment equipment, Integer nominalOperatingVoltageDc, Integer operatingVoltageMinDc,
      Integer operatingVoltageMaxDc, Float maxInputCurrentDc, Integer nominalOperatingVoltageAc,
      Integer nominalOperatingFrequencyAc, Integer nominalOutputPowerAc, Integer maxOutputPowerAc, Integer maxOutputCurrentAc) {
    this.equipment = equipment;
    this.nominalOperatingVoltageDc = nominalOperatingVoltageDc;
    this.operatingVoltageMinDc = operatingVoltageMinDc;
    this.operatingVoltageMaxDc = operatingVoltageMaxDc;
    this.maxInputCurrentDc = maxInputCurrentDc;
    this.nominalOperatingVoltageAc = nominalOperatingVoltageAc;
    this.nominalOperatingFrequencyAc = nominalOperatingFrequencyAc;
    this.nominalOutputPowerAc = nominalOutputPowerAc;
    this.maxOutputPowerAc = maxOutputPowerAc;
    this.maxOutputCurrentAc = maxOutputCurrentAc;
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

  public Integer getNominalOperatingVoltageDc() {
    return this.nominalOperatingVoltageDc;
  }

  public void setNominalOperatingVoltageDc(Integer nominalOperatingVoltageDc) {
    this.nominalOperatingVoltageDc = nominalOperatingVoltageDc;
  }

  public Integer getOperatingVoltageMinDc() {
    return this.operatingVoltageMinDc;
  }

  public void setOperatingVoltageMinDc(Integer operatingVoltageMinDc) {
    this.operatingVoltageMinDc = operatingVoltageMinDc;
  }

  public Integer getOperatingVoltageMaxDc() {
    return this.operatingVoltageMaxDc;
  }

  public void setOperatingVoltageMaxDc(Integer operatingVoltageMaxDc) {
    this.operatingVoltageMaxDc = operatingVoltageMaxDc;
  }

  public Float getMaxInputCurrentDc() {
    return this.maxInputCurrentDc;
  }

  public void setMaxInputCurrentDc(Float maxInputCurrentDc) {
    this.maxInputCurrentDc = maxInputCurrentDc;
  }

  public Integer getNominalOperatingVoltageAc() {
    return this.nominalOperatingVoltageAc;
  }

  public void setNominalOperatingVoltageAc(Integer nominalOperatingVoltageAc) {
    this.nominalOperatingVoltageAc = nominalOperatingVoltageAc;
  }

  public Integer getNominalOperatingFrequencyAc() {
    return this.nominalOperatingFrequencyAc;
  }

  public void setNominalOperatingFrequencyAc(Integer nominalOperatingFrequencyAc) {
    this.nominalOperatingFrequencyAc = nominalOperatingFrequencyAc;
  }

  public Integer getNominalOutputPowerAc() {
    return this.nominalOutputPowerAc;
  }

  public void setNominalOutputPowerAc(Integer nominalOutputPowerAc) {
    this.nominalOutputPowerAc = nominalOutputPowerAc;
  }

  public Integer getMaxOutputPowerAc() {
    return this.maxOutputPowerAc;
  }

  public void setMaxOutputPowerAc(Integer maxOutputPowerAc) {
    this.maxOutputPowerAc = maxOutputPowerAc;
  }

  public Integer getMaxOutputCurrentAc() {
    return this.maxOutputCurrentAc;
  }

  public void setMaxOutputCurrentAc(Integer maxOutputCurrentAc) {
    this.maxOutputCurrentAc = maxOutputCurrentAc;
  }

}
