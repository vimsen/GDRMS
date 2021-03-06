package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * City generated by hbm2java
 */
public class City implements java.io.Serializable {

  private int id;
  private Country country;
  private String name;
  private String county;
  private String timezone;
  private Set weatherConditions = new HashSet(0);
  private Set sites = new HashSet(0);

  public City() {
  }

  public City(int id, Country country) {
    this.id = id;
    this.country = country;
  }

  public City(int id, Country country, String name, String county, String timezone, Set weatherConditions, Set sites) {
    this.id = id;
    this.country = country;
    this.name = name;
    this.county = county;
    this.timezone = timezone;
    this.weatherConditions = weatherConditions;
    this.sites = sites;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Country getCountry() {
    return this.country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCounty() {
    return this.county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getTimezone() {
    return this.timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public Set getWeatherConditions() {
    return this.weatherConditions;
  }

  public void setWeatherConditions(Set weatherConditions) {
    this.weatherConditions = weatherConditions;
  }

  public Set getSites() {
    return this.sites;
  }

  public void setSites(Set sites) {
    this.sites = sites;
  }

}
