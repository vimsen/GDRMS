package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HQLServices;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.City;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.Country;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.DssSelectedProsumerId;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.PlanHasActionId;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.ProsumerHasSite;
import com.wattics.vimsen.entities.ProsumerHasSiteId;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.Transformer;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.ActionSlaService;
import com.wattics.vimsen.entities_services.CityService;
import com.wattics.vimsen.entities_services.ControlSignalService;
import com.wattics.vimsen.entities_services.CountryService;
import com.wattics.vimsen.entities_services.DsoTerritoryService;
import com.wattics.vimsen.entities_services.DssSelectedProsumerService;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.Kwh15mnService;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanHasActionService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.entities_services.ProsumerHasSiteService;
import com.wattics.vimsen.entities_services.ProsumerService;
import com.wattics.vimsen.entities_services.SiteService;
import com.wattics.vimsen.entities_services.TransformerService;
import com.wattics.vimsen.general.PlanStatusEnum;

public class DatabaseSetUp {

  public static int DEFAULT_COUNTRY_ID = 1;
  public static int DEFAULT_CITY_ID = 1;
  public static int DEFAULT_DSOTERRITORY_ID = 1;
  public static int DEFAULT_TRANSFORMER_ID = 1;
  public static int DEFAULT_SITE_ID = 1;
  public static int DEFAULT_PROSUMER_ID = 1;
  public static String DEFAULT_PROSUMER_MAIL = "giulia.depoli@wattics.com";
  public static int DEFAULT_EQUIPMENT_ID = 1;
  public static String DEFAULT_EQUIPMENT_CATEGORY = "consumption";
  public static String DEFAULT_EQUIPMENT_NAME = "fibaro1";
  public static int DEFAULT_ACTION_ID = 1;
  public static int DEFAULT_CONTROLSIGNAL_ID = 1;
  public static int DEFAULT_MARKETSIGNAL_ID = 1;
  public static int DEFAULT_PLAN_ID = 1;
  public static int DEFAULT_DR_INTERVAL = 900;
  public static String DEFAULT_DR_UNIT = "Kw";
  public static String INITIAL_PLAN_STATUS = PlanStatusEnum.REGISTERED.toString();
  public static String DEFAULT_RULE_CONTROLLER_ID = "zwave.me";
  public static String DEFAULT_PROSUMER_REFERENCE = "Pr_";
  public static String DEFAULT_USER = "sedini";
  public static String DEFAULT_DR_ACTION = "OFF";

  public static void populateDb(HibernateUtil hibernateUtil, int numberProsumers) throws DataAccessLayerException {
    CountryService countryService = new CountryService(hibernateUtil);
    Country country = new Country(DEFAULT_COUNTRY_ID);
    countryService.insert(country);
    CityService cityservice = new CityService(hibernateUtil);
    City city = new City(DEFAULT_CITY_ID, country);
    cityservice.insert(city);
    DsoTerritoryService dsoService = new DsoTerritoryService(hibernateUtil);
    DsoTerritory dsoTerritory = new DsoTerritory(DEFAULT_DSOTERRITORY_ID);
    dsoService.insert(dsoTerritory);
    TransformerService transformerService = new TransformerService(hibernateUtil);
    Transformer transformer = new Transformer(DEFAULT_TRANSFORMER_ID, dsoTerritory);
    transformerService.insert(transformer);

    ControlSignal controlSignal = new ControlSignal(DEFAULT_CONTROLSIGNAL_ID);
    controlSignal.setType(DEFAULT_DR_ACTION);
    controlSignal.setRuleControllerId("openhab");
    ControlSignalService controlSignalService = new ControlSignalService(hibernateUtil);
    controlSignalService.insert(controlSignal);

    SiteService siteService = new SiteService(hibernateUtil);
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    ProsumerService prosumerService = new ProsumerService(hibernateUtil);

    ProsumerHasSiteService psService = new ProsumerHasSiteService(hibernateUtil);
    ActionService actionService = new ActionService(hibernateUtil);
    ActionSlaService actionslaService = new ActionSlaService(hibernateUtil);

    for (int i = 0; i < numberProsumers; i++) {

      Site site = new Site(DEFAULT_SITE_ID + i, city, transformer);

      Equipment equipment = new Equipment(DEFAULT_EQUIPMENT_ID + i, site);
      equipment.setName(DEFAULT_EQUIPMENT_NAME + i);
      equipment.setCategory(DEFAULT_EQUIPMENT_CATEGORY);
      site.setEquipments(new HashSet<Equipment>(Arrays.asList(equipment)));

      Prosumer pr = new Prosumer(DEFAULT_PROSUMER_ID + i);
      pr.setName(DEFAULT_PROSUMER_REFERENCE + i);
      pr.setSurname(DEFAULT_USER);
      pr.setEmail(DEFAULT_PROSUMER_MAIL);

      ProsumerHasSiteId psId = new ProsumerHasSiteId(pr.getId(), site.getId());
      ProsumerHasSite prosumerhasSite1 = new ProsumerHasSite(psId, site, pr);

      int actionId = DEFAULT_ACTION_ID + i;
      Action action = new Action(actionId, equipment, controlSignal);
      equipment.setActions(new HashSet<Action>(Arrays.asList(action)));

      ActionSla actionSla = new ActionSla(action);
      actionSla.setConsumptionTarget(i * 5.0);
      actionSla.setStartResponsePeriod(new DateTime("2016-05-10T01:00:00"));
      actionSla.setEndResponsePeriod(new DateTime("2016-05-10T23:00:00"));
      action.setActionSla(actionSla);

      siteService.insert(site);
      equipmentService.insert(equipment);
      prosumerService.insert(pr);

      psService.insert(prosumerhasSite1);
      actionService.insert(action);
      actionslaService.insert(actionSla);

    }
  }

  public static void populateDbNumberEquipmentPerProsumer(HibernateUtil hibernateUtil, int numberProsumers, int numberEquipment)
      throws DataAccessLayerException {
    CountryService countryService = new CountryService(hibernateUtil);
    Country country = new Country(DEFAULT_COUNTRY_ID);
    countryService.insert(country);
    CityService cityservice = new CityService(hibernateUtil);
    City city = new City(DEFAULT_CITY_ID, country);
    cityservice.insert(city);
    DsoTerritoryService dsoService = new DsoTerritoryService(hibernateUtil);
    DsoTerritory dsoTerritory = new DsoTerritory(DEFAULT_DSOTERRITORY_ID);
    dsoService.insert(dsoTerritory);
    TransformerService transformerService = new TransformerService(hibernateUtil);
    Transformer transformer = new Transformer(DEFAULT_TRANSFORMER_ID, dsoTerritory);
    transformerService.insert(transformer);

    ControlSignal controlSignal = new ControlSignal(DEFAULT_CONTROLSIGNAL_ID);
    controlSignal.setType(DEFAULT_DR_ACTION);
    controlSignal.setRuleControllerId("openhab");
    ControlSignalService controlSignalService = new ControlSignalService(hibernateUtil);
    controlSignalService.insert(controlSignal);

    for (int i = 0; i < numberProsumers; i++) {
      Site site = new Site(DEFAULT_SITE_ID + i, city, transformer);

      List<Equipment> equipments = new ArrayList<>();
      List<Action> actions = new ArrayList<>();
      List<ActionSla> actionSlas = new ArrayList<>();
      for (int j = 0; j < numberEquipment; j++) {
        Equipment equipment = new Equipment(DEFAULT_EQUIPMENT_ID + (i * numberEquipment) + j, site);
        equipment.setName(DEFAULT_EQUIPMENT_NAME + j);
        equipment.setCategory(DEFAULT_EQUIPMENT_CATEGORY);

        Action action = new Action(DEFAULT_ACTION_ID + (i * numberEquipment) + j, equipment, controlSignal);

        equipment.setActions(new HashSet<Action>(Arrays.asList(action)));

        ActionSla actionSla = new ActionSla(action);
        actionSla.setConsumptionTarget(i * 5.0);
        actionSla.setStartResponsePeriod(new DateTime("2016-05-10T01:00:00"));
        actionSla.setEndResponsePeriod(new DateTime("2016-05-10T23:00:00"));

        action.setActionSla(actionSla);

        equipments.add(equipment);
        actions.add(action);
        actionSlas.add(actionSla);
      }
      site.setEquipments(new HashSet<Equipment>(equipments));
      SiteService siteService = new SiteService(hibernateUtil);

      ProsumerService prosumerService = new ProsumerService(hibernateUtil);
      Prosumer pr = new Prosumer(DEFAULT_PROSUMER_ID + i);
      pr.setName(DEFAULT_PROSUMER_REFERENCE + i);
      pr.setSurname(DEFAULT_USER + i);
      pr.setEmail(DEFAULT_PROSUMER_MAIL);

      ProsumerHasSiteId psId = new ProsumerHasSiteId(pr.getId(), site.getId());
      ProsumerHasSite prosumerhasSite1 = new ProsumerHasSite(psId, site, pr);
      ProsumerHasSiteService psService = new ProsumerHasSiteService(hibernateUtil);

      siteService.insert(site);

      EquipmentService equipmentService = new EquipmentService(hibernateUtil);
      ActionService actionService = new ActionService(hibernateUtil);
      ActionSlaService actionSlaService = new ActionSlaService(hibernateUtil);

      for (int index = 0; index < equipments.size(); index++) {
        equipmentService.insert(equipments.get(index));
      }
      prosumerService.insert(pr);
      psService.insert(prosumerhasSite1);

      for (int index = 0; index < actions.size(); i++) {
        actionService.insert(actions.get(index));
        actionSlaService.insert(actionSlas.get(index));
      }
    }
  }

  private static long getEndDRTime(DateTime startTime, int numberIntervals) {
    return startTime.getMillis() + 1000 * DEFAULT_DR_INTERVAL * numberIntervals;
  }

  public static MarketSignal storeMarketSignal(HibernateUtil hibernateUtil, String startTimeText, DateTime dateTimeStart,
      Double[] reduction, List<String> primaryProsumers, List<String> secondaryProsumers) throws DataAccessLayerException {
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    DsoTerritoryService dsoService = new DsoTerritoryService(hibernateUtil);
    DsoTerritory dsoTerritory = dsoService.find(DEFAULT_DSOTERRITORY_ID);
    MarketSignal marketSignal = new MarketSignal(dsoTerritory);

    marketSignal.setStartTime(dateTimeStart);
    DateTime endTime = new DateTime(getEndDRTime(dateTimeStart, reduction.length));
    marketSignal.setEndTime(endTime);

    marketSignal.setStartTimeText(startTimeText);
    marketSignal.setTimeInterval(DEFAULT_DR_INTERVAL);
    marketSignal.setUnit(DEFAULT_DR_UNIT);
    String targetReduction = Arrays.toString(reduction);
    marketSignal.setAmountReduction(targetReduction);

    MarketSignalService marketSignalService = new MarketSignalService(hibernateUtil);
    marketSignalService.insert(marketSignal);

    int marketSignalId = marketSignal.getId();
    DEFAULT_MARKETSIGNAL_ID = marketSignalId;

    DssSelectedProsumerService dssProsumerService = new DssSelectedProsumerService(hibernateUtil);
    for (String prosumerName : primaryProsumers) {
      Prosumer prosumer = dataGetter.getProsumerFromName(prosumerName);
      int prosumerId = prosumer.getId();
      DssSelectedProsumerId dssProsumerId = new DssSelectedProsumerId(prosumerId, marketSignalId);
      DssSelectedProsumer dssProsumer = new DssSelectedProsumer(dssProsumerId, marketSignal, prosumer, true, false, false, false,
          false, false);
      dssProsumerService.insert(dssProsumer);
    }
    for (String prosumerName : secondaryProsumers) {
      Prosumer prosumer = dataGetter.getProsumerFromName(prosumerName);
      int prosumerId = prosumer.getId();
      DssSelectedProsumerId dssProsumerId = new DssSelectedProsumerId(prosumerId, marketSignalId);
      DssSelectedProsumer dssProsumer = new DssSelectedProsumer(dssProsumerId, marketSignal, prosumer, false, false, false, false,
          false, false);
      dssProsumerService.insert(dssProsumer);
    }

    return marketSignal;

  }

  public static Plan storePlan(HibernateUtil hibernateUtil, MarketSignal marketSignal) throws DataAccessLayerException {
    Plan plan = new Plan(marketSignal);
    plan.setStatus(INITIAL_PLAN_STATUS);
    PlanService planService = new PlanService(hibernateUtil);
    planService.insert(plan);
    return plan;
  }

  public static void removeMarketSignalAndPlans(HibernateUtil hibernateUtil) throws DataAccessLayerException {
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    hqlServices.hqlTruncate("DssSelectedProsumer");
    hqlServices.hqlTruncate("PlanHasAction");
    hqlServices.hqlTruncate("Plan");
    hqlServices.hqlTruncate("MarketSignal");
  }

  public static void cleanDb(HibernateUtil hibernateUtil) throws DataAccessLayerException {
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    // remove data in tables with 1 to many relationship with Action

    hqlServices.hqlTruncate("ProsumerPreference");
    hqlServices.hqlTruncate("ActionSla");
    hqlServices.hqlTruncate("ActionMetric");
    hqlServices.hqlTruncate("DailyAvailableReduction");
    // remove data in tables with 1 to many relationship with Equipment
    hqlServices.hqlTruncate("EquipmentConditionProfile");
    hqlServices.hqlTruncate("ReschedulableFootprint");
    hqlServices.hqlTruncate("ReducibleFootprint");
    hqlServices.hqlTruncate("OperatingState");
    hqlServices.hqlTruncate("GeneratorFootprint");
    hqlServices.hqlTruncate("StorableFootprint");
    hqlServices.hqlTruncate("ElectricityRaw");
    hqlServices.hqlTruncate("Kwh5mn");
    hqlServices.hqlTruncate("Kwh15mn");
    hqlServices.hqlTruncate("KwhHourly");
    hqlServices.hqlTruncate("KwhBoundaries");
    hqlServices.hqlTruncate("EquipmentConditionRaw");
    hqlServices.hqlTruncate("KwhForecast");
    // remove data in tables with 1 to many relationship with City
    hqlServices.hqlTruncate("WeatherCondition");
    // remove data in tables with 1 to many relationship with Site
    hqlServices.hqlTruncate("SiteSla");
    hqlServices.hqlTruncate("SiteMetric");
    hqlServices.hqlTruncate("SiteConditionRaw");
    hqlServices.hqlTruncate("SiteConditionProfile");
    // remove data in tables in many to many connections
    hqlServices.hqlTruncate("ProsumerHasSite");
    hqlServices.hqlTruncate("DssSelectedProsumer");
    hqlServices.hqlTruncate("PlanHasAction");
    hqlServices.hqlTruncate("PlanWeight");
    hqlServices.hqlTruncate("ActionHasActionAttribute");
    // remove data from remaining tables
    hqlServices.hqlTruncate("Action");
    hqlServices.hqlTruncate("ControlSignal");
    hqlServices.hqlTruncate("Equipment");
    hqlServices.hqlTruncate("Site");
    hqlServices.hqlTruncate("City");
    hqlServices.hqlTruncate("Country");
    hqlServices.hqlTruncate("Transformer");
    hqlServices.hqlTruncate("Plan");
    hqlServices.hqlTruncate("MarketSignal");
    hqlServices.hqlTruncate("DsoTerritory");

    hqlServices.hqlTruncate("Prosumer");
    hqlServices.hqlTruncate("ActionAttribute");
  }

  public static Kwh15mn storeKwh15mn(HibernateUtil hibernateUtil, Equipment equipment, DateTime date)
      throws DataAccessLayerException {
    Kwh15mnId kwhId = new Kwh15mnId(equipment.getId(), date);
    Kwh15mn kwh = new Kwh15mn(kwhId, equipment);
    Kwh15mnService kwh15mnService = new Kwh15mnService(hibernateUtil);
    kwh15mnService.insert(kwh);
    return kwh;
  }

  public static void storeActionsInPlan(HibernateUtil hibernateUtil, Plan plan, int numberActions)
      throws DataAccessLayerException {
    ActionService actionService = new ActionService(hibernateUtil);
    Action action = actionService.find(numberActions);
    MarketSignalService marketSignalService = new MarketSignalService(hibernateUtil);
    MarketSignal marketSignal = marketSignalService.find(DEFAULT_MARKETSIGNAL_ID);
    PlanHasActionId planHasActionId = new PlanHasActionId(plan.getId(), action.getId());
    PlanHasAction planAction = new PlanHasAction();
    planAction.setId(planHasActionId);
    planAction.setPlan(plan);
    planAction.setAction(action);
    planAction.setPlannedAmount("[2.0,5.0]");
    planAction.setTStart(marketSignal.getStartTime());
    planAction.setAmountRecProgressToSend(1);
    planAction.setAmountRecProgressToSend(0);
    planAction.setAmountRecProgressSent(0);

    PlanHasActionService planActionService = new PlanHasActionService(hibernateUtil);
    planActionService.insert(planAction);
  }

}
