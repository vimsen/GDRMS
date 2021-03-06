package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Prosumer generated by hbm2java
 */
public class Prosumer implements java.io.Serializable {

  private int id;
  @NotNull
  private String name;
  @NotNull
  private String surname;
  @NotNull
  private String email;
  private String phone;
  private Set prosumerHasSites = new HashSet(0);
  private Set dssSelectedProsumers = new HashSet(0);

  public Prosumer() {
  }

  public Prosumer(int id) {
    this.id = id;
  }

  public Prosumer(int id, String name, String surname, String email, String phone, Set prosumerHasSites,
      Set dssSelectedProsumers) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phone = phone;
    this.prosumerHasSites = prosumerHasSites;
    this.dssSelectedProsumers = dssSelectedProsumers;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return this.surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Set getProsumerHasSites() {
    return this.prosumerHasSites;
  }

  public void setProsumerHasSites(Set prosumerHasSites) {
    this.prosumerHasSites = prosumerHasSites;
  }

  public Set getDssSelectedProsumers() {
    return this.dssSelectedProsumers;
  }

  public void setDssSelectedProsumers(Set dssSelectedProsumers) {
    this.dssSelectedProsumers = dssSelectedProsumers;
  }

}
