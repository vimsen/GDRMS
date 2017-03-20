package com.wattics.vimsen.GDRMEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.LDRMdatamanager.LDRMRuleException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.City;
import com.wattics.vimsen.entities.Country;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.Transformer;
import com.wattics.vimsen.entities.validator.ValidationCheck;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.CityService;
import com.wattics.vimsen.entities_services.CountryService;
import com.wattics.vimsen.entities_services.DsoTerritoryService;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.entities_services.SiteService;
import com.wattics.vimsen.entities_services.TransformerService;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.MapperException;

public class TempTestForDRLimits {

  private String testConfigurationFile = "localhostJavaTest.cfg.xml";
  private HibernateUtil hibernateUtil = new HibernateUtil(testConfigurationFile);

  @Test
  public void getValidActions() throws LDRMRuleException, DataAccessLayerException, MapperException, EDMSDataGetterException {

    GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    MarketSignal marketSignal = new MarketSignal();
    marketSignal.setStartTime(new DateTime("2016-05-03T13:00:00+02:00"));
    marketSignal.setEndTime(new DateTime("2016-05-03T18:00:00+02:00"));
    marketSignal.setStartTimeText("2016-05-03T13:00:00.000+02:00");

    List<Action> actions = dataGetter.getActionsFromProsumerId(1);

    List<Action> selectedAction = DRReductionCalculator.selectValidActions(actions, marketSignal);

    Assert.assertEquals(selectedAction.size(), 1);
  }

  @Test
  public void getPlans() throws DataAccessLayerException {
    // GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    // List<MarketSignal> marketSignalAll =
    // dataGetter.getMarketSignalWithinOneDay();
    //
    // MarketSignal ms = marketSignalAll.get(0);
    //
    // List<Plan> plans = (List<Plan>) ms.getPlans();

    PlanService msService = new PlanService(hibernateUtil);
    Plan ms = msService.find(341);
    MarketSignal ms1 = ms.getMarketSignal();
  }

  @Test
  public void insertSiteWithEquipmentTest() throws DataAccessLayerException {
    //To be deleted
    CountryService countryService = new CountryService(hibernateUtil);
    Country country = new Country(1);
    countryService.insert(country);
    CityService cityservice = new CityService(hibernateUtil);
    City city = new City(1, country);
    cityservice.insert(city);
    DsoTerritoryService dsoService = new DsoTerritoryService(hibernateUtil);
    DsoTerritory dsoTerritory = new DsoTerritory(1);
    dsoService.insert(dsoTerritory);
    TransformerService transformerService = new TransformerService(hibernateUtil);
    Transformer transformer = new Transformer(1, dsoTerritory);
    transformerService.insert(transformer);

    Site site = new Site(1, city, transformer);

    Equipment equipment = new Equipment(1, site);
    equipment.setName("Test");
    equipment.setCategory("consumption");

    Set equipments = new HashSet<>();
    equipments.add(equipment);

    site.setEquipments(equipments);

    SiteService siteService = new SiteService(hibernateUtil);
    siteService.insert(site);
  }

  @Test
  public void getPlansToBeProcessed() throws DataAccessLayerException, NoValidDataException {
    Validation dataGetter = new Validation(hibernateUtil);

    // List<Plan> plans = dataGetter.getPlanInRegisteredStatus();
    List<Plan> plans = PlanManager.selectPlansInRegisteredStatusToBeProcessed(dataGetter, null);

    System.out.println("Numero piani registrati: " + plans.size());
  }

}
