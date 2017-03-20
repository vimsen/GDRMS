package com.wattics.vimsen.GDRMdatamanager;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;

public interface GDRMDataStorerInterface {

  public void storeSiteMetric(SiteMetric siteMetric) throws DataAccessLayerException;

  public void storeSite(Site site) throws DataAccessLayerException;

  public void storeKwh15mn(Kwh15mn kwh15mn) throws DataAccessLayerException;

  public void storeKwForecast(KwhForecast forecastValue) throws DataAccessLayerException;

  public void storeMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException;

  public void storeProsumersAvailableParticipate(DssSelectedProsumer prosumerAvailable) throws DataAccessLayerException;

  public void storePlan(Plan plan) throws DataAccessLayerException;

  public void storePlanHasAction(PlanHasAction planHasAction) throws DataAccessLayerException;

  public void updatePlanStatus(int planId, String newStatus) throws DataAccessLayerException;

  public void storeDssSelectedProsumers(DssSelectedProsumer dssProsumer) throws DataAccessLayerException;

}
