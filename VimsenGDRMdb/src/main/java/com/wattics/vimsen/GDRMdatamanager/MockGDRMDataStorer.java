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

public class MockGDRMDataStorer implements GDRMDataStorerInterface {

  @Override
  public void storeSiteMetric(SiteMetric siteMetric) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeSite(Site site) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeKwh15mn(Kwh15mn kwh15mn) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeKwForecast(KwhForecast forecastValue) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeProsumersAvailableParticipate(DssSelectedProsumer prosumerAvailable) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storePlan(Plan plan) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storePlanHasAction(PlanHasAction planHasAction) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void updatePlanStatus(int planId, String newStatus) throws DataAccessLayerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeDssSelectedProsumers(DssSelectedProsumer dssProsumer) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    
  }

}
