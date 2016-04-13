package com.wattics.entities_services;

import org.hibernate.ObjectNotFoundException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Country;
import com.wattics.vimsen.entities_services.CountryService;

public class CountryServiceTest {

  private String testConfigurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;
  // private int numberProsumers= 1;

  @BeforeMethod
  public void setUpDb() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    // DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test
  public void testInsert() throws DataAccessLayerException {
    int countryId = 3;
    String countryName = "Rome";
    Country country = new Country(countryId);
    country.setName(countryName);

    CountryService countryService = new CountryService(hibernateUtil);
    countryService.insert(country);
    Country countryFound = countryService.find(countryId);

    Assert.assertEquals(country.getId(), countryFound.getId());
    Assert.assertEquals(country.getName(), countryFound.getName());
  }

  @Test(expectedExceptions = ObjectNotFoundException.class)
  public void testDelete() throws DataAccessLayerException {
    int countryId = 3;
    String countryName = "Rome";
    Country country = new Country(countryId);
    country.setName(countryName);

    CountryService countryService = new CountryService(hibernateUtil);
    countryService.insert(country);
    countryService.delete(country);

    countryService.find(countryId);
  }

  @Test
  public void testFind() throws DataAccessLayerException {
    int countryId = 3;
    String countryName = "Rome";
    Country country = new Country(countryId);
    country.setName(countryName);

    CountryService countryService = new CountryService(hibernateUtil);
    countryService.insert(country);
    Country countryFound = countryService.find(countryId);

    Assert.assertEquals(country.getId(), countryFound.getId());
    Assert.assertEquals(country.getName(), countryFound.getName());
  }

  @Test
  public void testUpdate() throws DataAccessLayerException {
    int countryId = 3;
    String countryName = "Rome";
    String countryNewName = "Paris";
    Country country = new Country(countryId);
    country.setName(countryName);

    CountryService countryService = new CountryService(hibernateUtil);
    countryService.insert(country);
    country.setName(countryNewName);
    countryService.update(country);
    Country countryFound = countryService.find(countryId);

    Assert.assertEquals(country.getId(), countryFound.getId());
    Assert.assertEquals(country.getName(), countryFound.getName());

  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
