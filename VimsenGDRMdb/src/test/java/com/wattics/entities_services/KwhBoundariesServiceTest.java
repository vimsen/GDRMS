package com.wattics.entities_services;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.KwhBoundaries;
import com.wattics.vimsen.entities.KwhBoundariesId;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.KwhBoundariesService;

public class KwhBoundariesServiceTest {

  private String configurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  @BeforeMethod
  public void setUpDb() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(configurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    int numberProsumers = 1;
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test
  public void testInsert() throws DataAccessLayerException {
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    int boundariesWeekday = 3;
    DateTime boundariesDate = new DateTime(2015, 07, 30, 0, 0);
    KwhBoundariesId kwhBoundariesId = new KwhBoundariesId(boundariesDate, boundariesWeekday, equipment.getId());
    KwhBoundaries kwhBoundaries = new KwhBoundaries(kwhBoundariesId, equipment);

    KwhBoundariesService kwhBoundariesService = new KwhBoundariesService(hibernateUtil);
    kwhBoundariesService.insert(kwhBoundaries);

    KwhBoundaries kwhBoundariesFound = kwhBoundariesService.find(kwhBoundariesId);
    Assert.assertEquals(kwhBoundaries.getId(), kwhBoundariesFound.getId());
  }

  @Test(expectedExceptions = DataAccessLayerException.class)
  public void testDelete() throws DataAccessLayerException {
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    int boundariesWeekday = 3;
    DateTime boundariesDate = new DateTime(2015, 07, 30, 0, 0);
    KwhBoundariesId kwhBoundariesId = new KwhBoundariesId(boundariesDate, boundariesWeekday, equipment.getId());
    KwhBoundaries kwhBoundaries = new KwhBoundaries(kwhBoundariesId, equipment);

    KwhBoundariesService kwhBoundariesService = new KwhBoundariesService(hibernateUtil);
    kwhBoundariesService.insert(kwhBoundaries);

    kwhBoundariesService.delete(kwhBoundaries);
    kwhBoundariesService.find(kwhBoundariesId);
  }

  @Test
  public void testFind() throws DataAccessLayerException {
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    int boundariesWeekday = 3;
    DateTime boundariesDate = new DateTime(2015, 07, 30, 0, 0);
    KwhBoundariesId kwhBoundariesId = new KwhBoundariesId(boundariesDate, boundariesWeekday, equipment.getId());
    KwhBoundaries kwhBoundaries = new KwhBoundaries(kwhBoundariesId, equipment);

    KwhBoundariesService kwhBoundariesService = new KwhBoundariesService(hibernateUtil);
    kwhBoundariesService.insert(kwhBoundaries);

    KwhBoundaries kwhBoundariesFound = kwhBoundariesService.find(kwhBoundariesId);
    Assert.assertEquals(kwhBoundaries.getId(), kwhBoundariesFound.getId());
  }

  @Test
  public void testUpdate() throws DataAccessLayerException {
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    int boundariesWeekday = 3;
    DateTime boundariesDate = new DateTime(2015, 07, 30, 0, 0);
    Double minCons = 30.0;
    Double minConsChange = 40.0;
    KwhBoundariesId kwhBoundariesId = new KwhBoundariesId(boundariesDate, boundariesWeekday, equipment.getId());
    KwhBoundaries kwhBoundaries = new KwhBoundaries(kwhBoundariesId, equipment);
    kwhBoundaries.setMinCons0000(minCons);

    KwhBoundariesService kwhBoundariesService = new KwhBoundariesService(hibernateUtil);
    kwhBoundariesService.insert(kwhBoundaries);
    kwhBoundaries.setMinCons0000(minConsChange);
    kwhBoundariesService.update(kwhBoundaries);

    KwhBoundaries kwhBoundariesFound = kwhBoundariesService.find(kwhBoundariesId);
    Assert.assertEquals(kwhBoundaries.getId(), kwhBoundariesFound.getId());
    Assert.assertEquals(40.0, kwhBoundariesFound.getMinCons0000());
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }

}
