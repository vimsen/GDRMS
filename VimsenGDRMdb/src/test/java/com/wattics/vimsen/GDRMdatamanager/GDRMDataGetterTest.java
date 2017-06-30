package com.wattics.vimsen.GDRMdatamanager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.utils.TimeHelpers;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class GDRMDataGetterTest {

  // private String configurationFile = "hibernate.cfg.xml";
  private String testConfigurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  private final String marketSignalDate = "2015-11-19T13:00:00.000+02:00";

  private int storeMarketSignalAndPlan() throws DataAccessLayerException {
    String startTimeText = marketSignalDate;
    DateTime dateTimeStart = new DateTime(startTimeText);
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<String> primaryProsumers = VimsenTestUtil.asList("Pr_0", "Pr_1");
    List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_2", "Pr_3");
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, dateTimeStart, reduction,
        primaryProsumers, secondaryProsumers);
    Plan plan = DatabaseSetUp.storePlan(hibernateUtil, marketSignal);
    int planId = plan.getId();
    return planId;
  }

  private int storeMarketSignal() throws DataAccessLayerException {
    String startTimeText = marketSignalDate;
    DateTime dateTimeStart = new DateTime(startTimeText);
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<String> primaryProsumers = VimsenTestUtil.asList("Pr_0", "Pr_1");
    List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_2", "Pr_3");
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, dateTimeStart, reduction,
        primaryProsumers, secondaryProsumers);
    return marketSignal.getId();
  }

  @BeforeMethod
  public void createHibernateSessionFactory() throws DataAccessLayerException {
    int numberProsumers = 4;
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test
  public void getSiteFromProsumerId() throws DataAccessLayerException {
    int prosumerId = DatabaseSetUp.DEFAULT_PROSUMER_ID;
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    Site site = dataGetter.getSiteFromProsumerId(prosumerId);

    Assert.assertEquals(site.getId(), 1);
  }

  @Test
  public void getActionsFromProsumerId() throws DataAccessLayerException {
    int prosumerId = DatabaseSetUp.DEFAULT_PROSUMER_ID;
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    List<Action> actions = dataGetter.getActionsFromProsumerId(prosumerId);

    Assert.assertEquals(actions.size(), 1);
  }

  @Test
  public void getProsumersFromMarketSignal() throws DataAccessLayerException {
    int marketSignalId = storeMarketSignal();
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    MarketSignal marketSignal = dataGetter.getMarketSignal(marketSignalId);
    List<Prosumer> prosumers = dataGetter.getPrimaryProsumersFromMarketSignal(marketSignal);
    Assert.assertEquals(prosumers.size(), 2);
  }

  @Test
  public void getKwh15mnValues() throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    // TODO: modify test if it is necessary to store data from EDMS in GDRM
    // database
    // Kwh15mn kwh15mn = new Kwh15mn();
    // kwh15mn.setT0000(0.0);
    // kwh15mn.setT0015(0.15);
    // kwh15mn.setT0130(1.30);
    //
    // GDRMDataGetterInterface dataGetter = new
    // GDRMDataGetter(hibernateUtil);
    // Double[] values = dataGetter.getKwh15mnAllValues(kwh15mn);
    //
    // Assert.assertEquals(values[0], 0.0);
    // Assert.assertEquals(values[1], 0.15);
    // Assert.assertEquals(values[6], 1.30);
  }

  @Test
  public void getKwhForecastDateTimeNotMidnightFails() throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, DataAccessLayerException {
    // TODO: modify test if it is necessary to store data from EDMS in GDRM
    // database
    // Kwh15mn kwh15mn = new Kwh15mn();
    // kwh15mn.setT0000(0.0);
    // kwh15mn.setT0015(0.15);
    // kwh15mn.setT0130(1.30);
    //
    // GDRMDataGetterInterface dataGetter = new
    // GDRMDataGetter(hibernateUtil);
    // Equipment equipment =
    // dataGetter.getEquipment(DatabaseSetUp.DEFAULT_EQUIPMENT_ID, null,
    // null);
    // DateTime dateForecast = new
    // DateTime("2016-01-20T00:00:00.000+00:00");
    // KwhForecastId forecastId = new KwhForecastId(dateForecast,
    // equipment.getId(), DatabaseSetUp.DEFAULT_EQUIPMENT_NAME);
    // KwhForecast kwhForecast = dataGetter.getKwhForecast(forecastId,
    // equipment);
    //
    // Assert.assertEquals(kwhForecast.getT0430(), 71.424606323242,
    // 0.000000001);
  }

  @Test
  public void getMarketSignalTime() throws DataAccessLayerException {
    int planId = storeMarketSignalAndPlan();
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);

    DateTime expectedDateTime = TimeHelpers.timestampUtcFormatterISO8601.withOffsetParsed().parseDateTime(marketSignalDate);

    MarketSignal marketSignal = GDRMDataGetterNoHib.getMarketSignalFromPlanId(planId, dataGetter);
    Assert.assertEquals(marketSignal.getStartTime().toInstant(), expectedDateTime.toInstant());
    Assert.assertEquals(marketSignal.getStartTimeText(), marketSignalDate);
  }

  @Test
  public void getMarketSignalsWithinOneDay() throws DataAccessLayerException {
    DateTime msStart = Instant.now().minus(2000).toDateTime();
    List<String> prosumerIds = new ArrayList<>();
    prosumerIds.add("Pr_1");
    Double[] reduction = new Double[] { 2.0, 3.0 };
    DatabaseSetUp.storeMarketSignal(hibernateUtil, msStart.toString(), msStart, reduction, prosumerIds, prosumerIds);

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    List<MarketSignal> msInDay = dataGetter.getMarketSignalWithinOneDay();

    Assert.assertEquals(msInDay.size(), 1);
  }

  @Test
  public void doesNotGetMarketSignalInThePast() throws DataAccessLayerException {
    DateTime msStart = Instant.now().minus(26 * TimeHelpers.MS_IN_A_DAY).toDateTime();
    List<String> prosumerIds = new ArrayList<>();
    prosumerIds.add("Pr_1");
    Double[] reduction = new Double[] { 2.0, 3.0 };
    DatabaseSetUp.storeMarketSignal(hibernateUtil, msStart.toString(), msStart, reduction, prosumerIds, prosumerIds);

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    List<MarketSignal> msInDay = dataGetter.getMarketSignalWithinOneDay();

    Assert.assertEquals(msInDay.size(), 0);
  }

  @Test
  public void doesNotGetMarketSignalInTheFuture() throws DataAccessLayerException {
    DateTime msStart = Instant.now().plus(26 * TimeHelpers.MS_IN_A_DAY).toDateTime();
    List<String> prosumerIds = new ArrayList<>();
    prosumerIds.add("Pr_1");
    Double[] reduction = new Double[] { 2.0, 3.0 };
    DatabaseSetUp.storeMarketSignal(hibernateUtil, msStart.toString(), msStart, reduction, prosumerIds, prosumerIds);

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    List<MarketSignal> msInDay = dataGetter.getMarketSignalWithinOneDay();

    Assert.assertEquals(msInDay.size(), 0);
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
