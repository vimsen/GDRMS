package com.wattics.vimsen.GDRMdatamanager;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Site;

public class GDRMSiteGetterTest {

  // private String configurationFile = "hibernate.cfg.xml";
  private String testConfigurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  @BeforeMethod
  public void createHibernateSessionFactory() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    int numberProsumers = 1;
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test
  public void getKwh15mnExistInDb() throws DataAccessLayerException {
    GDRMDataGetter siteGetter = new GDRMDataGetter(hibernateUtil);
    Site site = siteGetter.getSite(DatabaseSetUp.DEFAULT_SITE_ID);
    Equipment equipment = siteGetter.getEquipment(DatabaseSetUp.DEFAULT_EQUIPMENT_ID, DatabaseSetUp.DEFAULT_EQUIPMENT_CATEGORY, site);
    DateTime date = new DateTime(2015, 10, 21, 0, 0, 0);
    DatabaseSetUp.storeKwh15mn(hibernateUtil, equipment, date);

    Kwh15mn kwh15mn = siteGetter.getKwh15mn(equipment, date);

    Assert.assertEquals(kwh15mn.getId().getEquipmentId(), DatabaseSetUp.DEFAULT_EQUIPMENT_ID);
  }

  @Test
  public void getKwh15mnDoesNotExistInDb() throws DataAccessLayerException {
    Equipment eq = new Equipment();
    eq.setId(DatabaseSetUp.DEFAULT_EQUIPMENT_ID);
    DateTime date = new DateTime(2015, 10, 21, 0, 0, 0);
    GDRMDataGetter siteGetter = new GDRMDataGetter(hibernateUtil);

    Kwh15mn kwh15mn = siteGetter.getKwh15mn(eq, date);

    Assert.assertEquals(kwh15mn.getId().getEquipmentId(), DatabaseSetUp.DEFAULT_EQUIPMENT_ID);
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
