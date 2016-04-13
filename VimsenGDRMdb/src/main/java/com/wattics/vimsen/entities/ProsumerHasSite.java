package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

/**
 * ProsumerHasSite generated by hbm2java
 */
public class ProsumerHasSite implements java.io.Serializable {

  private ProsumerHasSiteId id;
  private Site site;
  private Prosumer prosumer;
  private String type;

  public ProsumerHasSite() {
  }

  public ProsumerHasSite(ProsumerHasSiteId id, Site site, Prosumer prosumer) {
    this.id = id;
    this.site = site;
    this.prosumer = prosumer;
  }

  public ProsumerHasSite(ProsumerHasSiteId id, Site site, Prosumer prosumer, String type) {
    this.id = id;
    this.site = site;
    this.prosumer = prosumer;
    this.type = type;
  }

  public ProsumerHasSiteId getId() {
    return this.id;
  }

  public void setId(ProsumerHasSiteId id) {
    this.id = id;
  }

  public Site getSite() {
    return this.site;
  }

  public void setSite(Site site) {
    this.site = site;
  }

  public Prosumer getProsumer() {
    return this.prosumer;
  }

  public void setProsumer(Prosumer prosumer) {
    this.prosumer = prosumer;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

}