package com.wattics.vimsen.GDRMdatamanager;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HQLServices;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
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
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.ProsumerHasSite;
import com.wattics.vimsen.entities.ProsumerHasSiteId;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.Transformer;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.CityService;
import com.wattics.vimsen.entities_services.ControlSignalService;
import com.wattics.vimsen.entities_services.CountryService;
import com.wattics.vimsen.entities_services.DsoTerritoryService;
import com.wattics.vimsen.entities_services.DssSelectedProsumerService;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.Kwh15mnService;
import com.wattics.vimsen.entities_services.MarketSignalService;
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
  public static int DEFAULT_EQUIPMENT_ID = 1;
  public static String DEFAULT_EQUIPMENT_NAME = "consumption";
  public static int DEFAULT_ACTION_ID = 1;
  public static int DEFAULT_CONTROLSIGNAL_ID = 1;
  public static int DEFAULT_MARKETSIGNAL_ID = 1;
  public static int DEFAULT_PLAN_ID = 1;
  public static int DEFAULT_DR_INTERVAL = 900;
  public static String DEFAULT_DR_UNIT = "Kw";
  public static String INITIAL_PLAN_STATUS = PlanStatusEnum.REGISTERED.toString();
  public static String DEFAULT_RULE_CONTROLLER_ID = "zwave.me";
  public static String DEFAULT_PROSUMER_REFERENCE = "b827eb4c14af";

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
    ControlSignalService controlSignalService = new ControlSignalService(hibernateUtil);
    controlSignalService.insert(controlSignal);

    for (int i = 0; i < numberProsumers; i++) {
      SiteService siteService = new SiteService(hibernateUtil);
      Site site = new Site(DEFAULT_SITE_ID + i, city, transformer);
      siteService.insert(site);
      EquipmentService equipmentService = new EquipmentService(hibernateUtil);
      Equipment equipment = new Equipment(DEFAULT_EQUIPMENT_ID + i, site);
      equipment.setName(DEFAULT_EQUIPMENT_NAME);
      equipmentService.insert(equipment);

      ProsumerService prosumerService = new ProsumerService(hibernateUtil);
      Prosumer pr = new Prosumer(DEFAULT_PROSUMER_ID + i);
      pr.setName(DEFAULT_PROSUMER_REFERENCE);
      prosumerService.insert(pr);
      ProsumerHasSiteId psId = new ProsumerHasSiteId(pr.getId(), site.getId());
      ProsumerHasSite prosumerhasSite1 = new ProsumerHasSite(psId, site, pr);
      ProsumerHasSiteService psService = new ProsumerHasSiteService(hibernateUtil);
      psService.insert(prosumerhasSite1);

      Action action = new Action(DEFAULT_ACTION_ID + i, equipment, controlSignal);
      ActionService actionService = new ActionService(hibernateUtil);
      actionService.insert(action);
    }
  }

  private static long getEndDRTime(DateTime startTime, int numberIntervals) {
    return startTime.getMillis() + 1000 * DEFAULT_DR_INTERVAL * numberIntervals;
  }

  public static MarketSignal storeMarketSignal(HibernateUtil hibernateUtil, String startTimeText, DateTime dateTimeStart,
      Double[] reduction, List<Integer> primaryProsumers, List<Integer> secondaryProsumers) throws DataAccessLayerException {
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

    ProsumerService prosumerService = new ProsumerService(hibernateUtil);
    DssSelectedProsumerService dssProsumerService = new DssSelectedProsumerService(hibernateUtil);
    for (int prosumerId : primaryProsumers) {
      Prosumer prosumer = prosumerService.find(prosumerId);
      DssSelectedProsumerId dssProsumerId = new DssSelectedProsumerId(prosumerId, marketSignalId);
      DssSelectedProsumer dssProsumer = new DssSelectedProsumer(dssProsumerId, marketSignal, prosumer, true);
      dssProsumerService.insert(dssProsumer);
    }
    for (int prosumerId : secondaryProsumers) {
      Prosumer prosumer = prosumerService.find(prosumerId);
      DssSelectedProsumerId dssProsumerId = new DssSelectedProsumerId(prosumerId, marketSignalId);
      DssSelectedProsumer dssProsumer = new DssSelectedProsumer(dssProsumerId, marketSignal, prosumer, false);
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

}
