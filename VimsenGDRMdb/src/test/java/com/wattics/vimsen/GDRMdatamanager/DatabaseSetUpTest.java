package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.ProsumerService;
import com.wattics.vimsen.utils.TimeHelpers;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class DatabaseSetUpTest {

  private String testConfigurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  @BeforeMethod
  public void createHibernateSessionFactory() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(testConfigurationFile);
  }

  @Test
  public void cleanDbTest() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
  }

  @Test
  public void populateDb() throws DataAccessLayerException {
    int numberProsumers = 2;
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);

    ProsumerService prosumerService = new ProsumerService(hibernateUtil);
    List<?> prosumers = prosumerService.findAll();

    Assert.assertEquals(prosumers.size(), numberProsumers);
  }

  @Test
  public void addMarketSignal() throws DataAccessLayerException {
    int numberProsumers = 5;
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);

    String startTimeText = "2016-01-10T13:00:00.000+02:00";
    DateTime startTime = TimeHelpers.createDateTimePreservingTimeZone(startTimeText);

    Double[] reduction = new Double[] { 10.0, 2.0 };
    List<String> primaryProsumers = VimsenTestUtil.asList("Pr_1", "Pr_2");
    List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_3", "Pr_4");

    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, startTime, reduction,
        primaryProsumers, secondaryProsumers);

    MarketSignalService marketSignalService = new MarketSignalService(hibernateUtil);
    MarketSignal marketSignalStored = marketSignalService.find(marketSignal.getId());

    Assert.assertEquals(marketSignalStored.getStartTimeText(), startTimeText);
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    // DatabaseSetUp.cleanDb(hibernateUtil);
    // hibernateUtil.closeSessionFactory();
    // hibernateUtil.setSessionFactoryToNull();
  }
}
