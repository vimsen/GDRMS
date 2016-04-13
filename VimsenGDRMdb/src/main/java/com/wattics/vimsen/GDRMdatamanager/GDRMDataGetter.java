package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.joda.time.DateTime;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.KwhForecastId;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.ProsumerHasSite;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.DsoTerritoryService;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.Kwh15mnService;
import com.wattics.vimsen.entities_services.KwhForecastService;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.entities_services.ProsumerService;
import com.wattics.vimsen.entities_services.SiteMetricService;
import com.wattics.vimsen.entities_services.SiteService;
import com.wattics.vimsen.general.DefaultSettings;
import com.wattics.vimsen.general.EquipmentNameEnum;

public class GDRMDataGetter implements GDRMDataGetterInterface {

  private HibernateUtil hibernateUtil;

  public GDRMDataGetter(HibernateUtil hibernateUtil) {
    this.hibernateUtil = hibernateUtil;
  }

  public Site getSite(int siteId) throws DataAccessLayerException {

    SiteService siteService = new SiteService(hibernateUtil);
    Site site = siteService.find(siteId);
    return site;
  }

  public List<Action> getActionsFromProsumer(Prosumer prosumer) throws DataAccessLayerException {
    int prosumerId = prosumer.getId();
    List<Action> actions = getActionsFromProsumerId(prosumerId);
    return actions;
  }

  @Override
  public List<Action> getActionsFromProsumerId(int prosumerId) throws DataAccessLayerException {
    List<Action> actions = new ArrayList<Action>();
    Site site = getSiteFromProsumerId(prosumerId);
    List<Equipment> equipmentList = GDRMDataGetterNoHib.getEquipmentFromSite(site);
    for (Equipment equipment : equipmentList) {
      List<Action> equipActions = GDRMDataGetterNoHib.getActionsFromEquipment(equipment);
      actions.addAll(equipActions);
    }
    return actions;
  }

  public Site getSiteFromProsumerId(int prosumerId) throws DataAccessLayerException {
    ProsumerService prosumerService = new ProsumerService(hibernateUtil);
    Prosumer prosumer = prosumerService.find(prosumerId);
    @SuppressWarnings("unchecked")
    List<ProsumerHasSite> prosumerSitesList = new ArrayList<ProsumerHasSite>(prosumer.getProsumerHasSites());

    return prosumerSitesList.get(DefaultSettings.PROSUMER_SITE_IDX).getSite();
  }

  public Equipment getEquipment(int id, String text, Site site) throws DataAccessLayerException {
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = null;
    try {
      equipment = equipmentService.find(id);
    } catch (ObjectNotFoundException e) {
      // element not present in the db. returns null
    }
    return equipment;
  }

  public Kwh15mn getKwh15mn(Equipment eq, DateTime date) throws DataAccessLayerException {
    Kwh15mnService kwh15mnService = new Kwh15mnService(hibernateUtil);
    Kwh15mnId id = new Kwh15mnId(eq.getId(), date);
    Kwh15mn kwh15mn = null;
    try {
      kwh15mn = kwh15mnService.find(id);
    } catch (ObjectNotFoundException e) {
      kwh15mn = new Kwh15mn(id, eq);
    }
    return kwh15mn;
  }

  public Kwh15mn getKwh15mn(Kwh15mnId id, Equipment eq) throws DataAccessLayerException {
    Kwh15mnService kwh15mnService = new Kwh15mnService(hibernateUtil);
    Kwh15mn kwh15mn = null;
    try {
      kwh15mn = kwh15mnService.find(id);
      // kwh15mnService.delete(kwh15mn);
    } catch (ObjectNotFoundException e) {
      kwh15mn = new Kwh15mn(id, eq);
    }
    return kwh15mn;
  }

  public SiteMetric getSiteMetric(Site prosumerSite) throws DataAccessLayerException {
    SiteMetricService siteMetricService = new SiteMetricService(hibernateUtil);
    SiteMetric siteMetric = null;
    try {
      siteMetric = siteMetricService.find(prosumerSite.getId());
    } catch (ObjectNotFoundException e) {
      siteMetric = new SiteMetric(prosumerSite);
    }
    return siteMetric;
  }

  public KwhForecast getKwhForecast(KwhForecastId forecastId, Equipment equipment) throws DataAccessLayerException {
    KwhForecastService kwhForecastService = new KwhForecastService(hibernateUtil);
    KwhForecast kwhForecast = null;
    try {
      kwhForecast = kwhForecastService.find(forecastId);
    } catch (org.hibernate.ObjectNotFoundException e) {
      kwhForecast = new KwhForecast(forecastId, equipment);
    }
    return kwhForecast;
  }

  @Override
  public DsoTerritory getDsoTerritory(int dsoTerritoryId) throws DataAccessLayerException {
    DsoTerritoryService dsoTerritoryService = new DsoTerritoryService(hibernateUtil);
    DsoTerritory dsoTerritory = dsoTerritoryService.find(dsoTerritoryId);
    return dsoTerritory;
  }

  @Override
  public Plan getPlan(int planId) throws DataAccessLayerException {
    PlanService planService = new PlanService(hibernateUtil);
    Plan plan = planService.find(planId);
    return plan;
  }

  @Override
  public MarketSignal getMarketSignal(int marketSignalId) throws DataAccessLayerException {
    MarketSignalService marketSignalService = new MarketSignalService(hibernateUtil);
    MarketSignal marketSignal = marketSignalService.find(marketSignalId);
    return marketSignal;
  }

  @Override
  public Prosumer getProsumerFromActionId(int actionId) throws DataAccessLayerException {
    ActionService actionService = new ActionService(hibernateUtil);
    Action action = actionService.find(actionId);
    Equipment equipment = action.getEquipment();
    Site site = equipment.getSite();
    @SuppressWarnings("unchecked")
    Set<ProsumerHasSite> phsSet = site.getProsumerHasSites();
    List<ProsumerHasSite> phs = new ArrayList<ProsumerHasSite>(phsSet);
    Prosumer prosumer = phs.get(DefaultSettings.PROSUMER_SITE_IDX).getProsumer();
    return prosumer;
  }

  @Override
  public List<Plan> getPlansWithStatus(String string) throws DataAccessLayerException {
    PlanService planService = new PlanService(hibernateUtil);
    @SuppressWarnings("unchecked")
    List<Plan> plans = (List<Plan>) planService.findAll();
    List<Plan> plansWithStatus = new ArrayList<Plan>();
    for (Plan plan : plans) {
      if (plan.getStatus().equals(string)) {
        plansWithStatus.add(plan);
      }
    }
    return plansWithStatus;
  }

  public KwhForecast getKwhForecastFromProsumer(Prosumer prosumer, String parameterType, DateTime date)
      throws DataAccessLayerException {
    Site site = getSiteFromProsumerId(prosumer.getId());
    List<Equipment> equipmentList = GDRMDataGetterNoHib.getEquipmentFromSite(site);
    Equipment equipment = GDRMDataGetterNoHib.selectEquipmentByName(equipmentList, EquipmentNameEnum.CONSUMPTION.toString());
    KwhForecastId forecastId = new KwhForecastId(date, equipment.getId(), parameterType);
    KwhForecastService kwhForecastService = new KwhForecastService(hibernateUtil);
    KwhForecast kwhForecast = kwhForecastService.find(forecastId);
    return kwhForecast;
  }

}
