package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;

public class MappingClassGenerationTest {

  private String configurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  @BeforeMethod(enabled = false)
  public void setUpDb() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(configurationFile);
    int numberProsumers = 1;
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test(enabled = false)
  public void getSiteFromProsumerIdTest() throws DataAccessLayerException {
    int prosumerId = DatabaseSetUp.DEFAULT_PROSUMER_ID;
    GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    Site site = dataGetter.getSiteFromProsumerId(prosumerId);
    // If fails check if Prosumer.xml has lazy=false for itself and for
    // ProsumerHasSite
  }

  @Test(enabled = false)
  public void getKwh15mnTest() throws DataAccessLayerException {
    GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    Site site = dataGetter.getSite(DatabaseSetUp.DEFAULT_SITE_ID);
    Equipment equipment = dataGetter.getEquipment(1, "consumption", site);
    DateTime date = new DateTime("2015-10-19T13:00:00.000+02:00");
    Kwh15mn kwh15mn = dataGetter.getKwh15mn(equipment, date);
  }

  @Test(enabled = false)
  public void getSiteMetricTest() throws DataAccessLayerException {
    GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    Site site = dataGetter.getSite(DatabaseSetUp.DEFAULT_SITE_ID);
    SiteMetric siteMetric = dataGetter.getSiteMetric(site);
  }

  @Test(enabled = false)
  public void getEquipmentFromSiteTest() throws DataAccessLayerException {
    GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    Site site = dataGetter.getSite(DatabaseSetUp.DEFAULT_SITE_ID);
    List<Equipment> listEquipment = GDRMDataGetterNoHib.getEquipmentFromSite(site);

    Assert.assertEquals(listEquipment.size(), 1);
    // If fails check if Site.xml has lazy=false for itself and for
    // equipments
  }

  @Test(enabled = false)
  public void getActionsFromProsumerIdTest() throws DataAccessLayerException {
    GDRMDataGetter dataGetter = new GDRMDataGetter(hibernateUtil);
    List<Action> actions = dataGetter.getActionsFromProsumerId(DatabaseSetUp.DEFAULT_PROSUMER_ID);
    // If fails check if:
    // Prosumer.xml has lazy=false for itself and for ProsumerHasSite
    // Site.xml has lazy= false for itself and equipment
    // Equipment.xml has lazy= false for itself and actions
  }

  @AfterMethod(enabled = false)
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
