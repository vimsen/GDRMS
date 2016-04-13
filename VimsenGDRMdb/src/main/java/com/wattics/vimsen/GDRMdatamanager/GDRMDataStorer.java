package com.wattics.vimsen.GDRMdatamanager;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;
import com.wattics.vimsen.entities_services.DssSelectedProsumerService;
import com.wattics.vimsen.entities_services.Kwh15mnService;
import com.wattics.vimsen.entities_services.KwhForecastService;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanHasActionService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.entities_services.SiteMetricService;
import com.wattics.vimsen.entities_services.SiteService;

public class GDRMDataStorer implements GDRMDataStorerInterface {
  private HibernateUtil hibernateUtil;

  public GDRMDataStorer(HibernateUtil hibernateUtil) {
    this.hibernateUtil = hibernateUtil;
  }

  public void storeSiteMetric(SiteMetric siteMetric) throws DataAccessLayerException {
    SiteMetricService siteMetricService = new SiteMetricService(hibernateUtil);
    siteMetricService.update(siteMetric);
  }

  public void storeSite(Site site) throws DataAccessLayerException {
    SiteService siteService = new SiteService(hibernateUtil);
    siteService.update(site);
  }

  public void storeKwh15mn(Kwh15mn kwh15mn) throws DataAccessLayerException {
    Kwh15mnService kwh15mnService = new Kwh15mnService(hibernateUtil);
    kwh15mnService.update(kwh15mn);
  }

  public void storeKwForecast(KwhForecast forecastValue) throws DataAccessLayerException {
    KwhForecastService kwhForecastService = new KwhForecastService(hibernateUtil);
    kwhForecastService.update(forecastValue);
  }

  @Override
  public void storeMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    MarketSignalService marketSignalService = new MarketSignalService(hibernateUtil);
    marketSignalService.update(marketSignal);
  }

  @Override
  public void storeProsumersAvailableParticipate(DssSelectedProsumer prosumerAvailable) throws DataAccessLayerException {
    DssSelectedProsumerService dssSelectedProsumerService = new DssSelectedProsumerService(hibernateUtil);
    dssSelectedProsumerService.update(prosumerAvailable);
  }

  @Override
  public void storePlan(Plan plan) throws DataAccessLayerException {
    PlanService planService = new PlanService(hibernateUtil);
    planService.update(plan);
  }

  @Override
  public void storePlanHasAction(PlanHasAction planHasAction) throws DataAccessLayerException {
    PlanHasActionService planHasActionService = new PlanHasActionService(hibernateUtil);
    planHasActionService.update(planHasAction);
  }

  @Override
  public void updatePlanStatus(int planId, String newStatus) throws DataAccessLayerException {
    PlanService planService = new PlanService(hibernateUtil);
    Plan plan = planService.find(planId);
    plan.setStatus(newStatus);
    planService.update(plan);
  }

}
