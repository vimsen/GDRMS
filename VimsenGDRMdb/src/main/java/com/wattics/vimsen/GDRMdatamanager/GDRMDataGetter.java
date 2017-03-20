package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HQLServices;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.KwhForecastId;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.ProsumerHasSite;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.DsoTerritoryService;
import com.wattics.vimsen.entities_services.DssSelectedProsumerService;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.Kwh15mnService;
import com.wattics.vimsen.entities_services.KwhForecastService;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.entities_services.ProsumerService;
import com.wattics.vimsen.entities_services.SiteMetricService;
import com.wattics.vimsen.entities_services.SiteService;
import com.wattics.vimsen.general.DefaultSettings;
import com.wattics.vimsen.general.EquipmentCategory;
import com.wattics.vimsen.utils.TimeHelpers;

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
  public List<Plan> getPlansWithStatus(String planStatus) throws DataAccessLayerException {
    String query = "FROM Plan as p WHERE p.status= '" + planStatus + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Plan> plans = hqlServices.selectWhereCondition(query);

    return plans;
  }

  public KwhForecast getKwhForecastFromProsumer(Prosumer prosumer, String parameterType, DateTime date)
      throws DataAccessLayerException {
    Site site = getSiteFromProsumerId(prosumer.getId());
    List<Equipment> equipmentList = GDRMDataGetterNoHib.getEquipmentFromSite(site);
    Equipment equipment = GDRMDataGetterNoHib.selectEquipmentByCategory(equipmentList, EquipmentCategory.CONSUMPTION.toString());
    KwhForecastId forecastId = new KwhForecastId(date, equipment.getId(), parameterType);
    KwhForecastService kwhForecastService = new KwhForecastService(hibernateUtil);
    KwhForecast kwhForecast = kwhForecastService.find(forecastId);
    return kwhForecast;
  }

  @Override
  public Equipment getEquipmentFromActionId(int actionId) throws DataAccessLayerException {
    ActionService actionService = new ActionService(hibernateUtil);
    Action action = actionService.find(actionId);
    Equipment equipment = action.getEquipment();

    return equipment;
  }

  @Override
  public ControlSignal getControlSignalFromActionId(int actionId) throws DataAccessLayerException {
    ActionService actionService = new ActionService(hibernateUtil);
    Action action = actionService.find(actionId);
    ControlSignal controlSignal = action.getControlSignal();
    return controlSignal;
  }

  public List<Action> getActionsFromProsumers(List<Prosumer> prosumers) throws DataAccessLayerException {
    List<Action> actions = new ArrayList<Action>();
    for (Prosumer prosumer : prosumers) {
      List<Action> actionsProsumer = getActionsFromProsumerId(prosumer.getId());
      actions.addAll(actionsProsumer);
    }
    return actions;
  }

  @SuppressWarnings("unchecked")
  @Override
  public DssSelectedProsumer getDssFromProsumerAndMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException {
    DssSelectedProsumer dssProsumer = null;
    int msId = marketSignal.getId();
    int pId = prosumer.getId();
    String query = "FROM DssSelectedProsumer as p where p.id.prosumerId= '" + pId + "' and p.id.marketSignalId= '" + msId + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<DssSelectedProsumer> dssProsumers = hqlServices.selectWhereCondition(query);
    if (dssProsumers.size() > 0) {
      dssProsumer = dssProsumers.get(0);
    }

    return dssProsumer;
  }

  @Override
  public List<Prosumer> getProsumerWithActionToImplement(MarketSignal marketSignal) throws DataAccessLayerException {
    // TODO: modify with query
    List<Prosumer> prosumers = new ArrayList<>();
    List<Plan> plans = getPlansFromMarketSignal(marketSignal);
    for (Plan plan : plans) {

      Set<PlanHasAction> planActions = plan.getPlanHasActions();
      for (PlanHasAction planAction : planActions) {
        Prosumer prosumer = getProsumerToImplementAction(planAction);
        if (!prosumers.contains(prosumer)) {
          prosumers.add(prosumer);
        }
      }
    }
    return prosumers;
  }

  @Override
  public List<PlanHasAction> getActionsAssignedToAProsumerForAMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException {
    // TODO: modify with query
    List<PlanHasAction> planActionsProsumer = new ArrayList<>();
    List<Plan> plans = getPlansFromMarketSignal(marketSignal);
    for (Plan plan : plans) {
      Set<PlanHasAction> planActions = plan.getPlanHasActions();
      for (PlanHasAction planAction : planActions) {
        Prosumer prosumerAction = getProsumerToImplementAction(planAction);
        if (prosumer.getId() == prosumerAction.getId()) {
          planActionsProsumer.add(planAction);
        }
      }
    }
    return planActionsProsumer;
  }

  @Override
  public Prosumer getProsumerToImplementAction(PlanHasAction planHasAction) throws DataAccessLayerException {
    // TODO: modify with query
    Prosumer prosumer = null;
    Action action = planHasAction.getAction();
    Equipment equipment = action.getEquipment();
    Site site = equipment.getSite();
    Set<ProsumerHasSite> prosumerHasSite = site.getProsumerHasSites();
    for (ProsumerHasSite phs : prosumerHasSite) {
      prosumer = phs.getProsumer();
      return prosumer;
    }
    return prosumer;
  }

  @Override
  public List<MarketSignal> getMarketSignalWithinOneDay() throws DataAccessLayerException {
    // TODO: modify with query
    MarketSignalService marketSignalService = new MarketSignalService(hibernateUtil);
    List<MarketSignal> msAll = (List<MarketSignal>) marketSignalService.findAll();
    List<MarketSignal> msSelected = new ArrayList<MarketSignal>();
    if (msAll.size() > 0) {
      for (MarketSignal ms : msAll) {

        DateTime start = ms.getStartTime();
        DateTime end = ms.getEndTime();

        Instant startMinusOneDay = start.minus(TimeHelpers.MS_IN_A_DAY).toInstant();
        Instant endPlusOneDay = end.plus(TimeHelpers.MS_IN_A_DAY).toInstant();

        if ((startMinusOneDay.isBefore(Instant.now())) && (endPlusOneDay.isAfter(Instant.now()))) {
          msSelected.add(ms);
        }
      }
    }
    return msSelected;
  }

  @Override
  public List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    // TODO: finish testing
    ProsumerService prosumerService = new ProsumerService(hibernateUtil);
    List<Prosumer> prosumers = new ArrayList<Prosumer>();
    Set<DssSelectedProsumer> dssProsumersSet = marketSignal.getDssSelectedProsumers();
    List<DssSelectedProsumer> dssProsumers = new ArrayList<DssSelectedProsumer>(dssProsumersSet);
    for (DssSelectedProsumer dssProsumer : dssProsumers) {
      if (dssProsumer.getIsPrimary()) {
        Prosumer prosumer = dssProsumer.getProsumer();
        prosumers.add(prosumerService.find(prosumer.getId()));
      }
    }
    return prosumers;
  }

  @Override
  public List<Plan> getPlansFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    Integer msId = marketSignal.getId();
    String query = "FROM Plan where marketSignal.id=" + msId.toString();
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Plan> plans = hqlServices.selectWhereCondition(query);

    return plans;
  }

  @Override
  public Prosumer getProsumerFromName(String prosumerName) throws DataAccessLayerException {
    Prosumer prosumer = null;
    String query = "FROM Prosumer as p where p.name= '" + prosumerName + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);
    prosumer = prosumers.get(0);
    return prosumer;
  }

  @Override
  public Equipment getEquipmentFromPlanHasAction(PlanHasAction planAction) throws DataAccessLayerException {
    Integer actionId = planAction.getAction().getId();
    Equipment eq = getEquipmentFromActionId(actionId);
    return eq;
  }

  @Override
  public ActionSla getActionSlaFromActionId(int actionId) throws DataAccessLayerException {
    ActionSla actionSla = null;
    String query = "FROM ActionSla as a where a.actionId= '" + actionId + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<ActionSla> acrionSlas = hqlServices.selectWhereCondition(query);
    actionSla = acrionSlas.get(0);

    return actionSla;
  }

}
