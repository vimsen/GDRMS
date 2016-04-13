package com.wattics.entities_services;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Country;
import com.wattics.vimsen.entities_services.CountryService;

public class SchemaSelectionTest {

  private String configurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  @BeforeMethod
  public void setUpDb() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(configurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
  }

  @Test
  public void selectDbSchemaFromConfigurationFile() throws DataAccessLayerException {
    // this test insert a new country entry. It has to be checked manually the
    // db where it was inserted
    int countryId = 3;
    String countryName = "Spain";
    Country country = new Country(countryId);
    country.setName(countryName);

    CountryService countryService = new CountryService(hibernateUtil);
    countryService.insert(country);
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
