package com.wattics.entities_services;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.OperatingState;
import com.wattics.vimsen.entities.OperatingStateId;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.OperatingStateService;

public class OperatingStateServiceTest {

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
    Double kwValue = 10.0;
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    OperatingStateId operatingStateId = new OperatingStateId(1, equipment.getId());
    OperatingState operatingState = new OperatingState(operatingStateId, equipment, kwValue);

    OperatingStateService operatingStateService = new OperatingStateService(hibernateUtil);
    operatingStateService.insert(operatingState);

    OperatingState operatigStateFound = operatingStateService.find(operatingStateId);
    Assert.assertEquals(operatingState.getId(), operatigStateFound.getId());
    Assert.assertEquals(operatingState.getKw(), operatigStateFound.getKw());
  }

  @Test(expectedExceptions = DataAccessLayerException.class)
  public void testDelete() throws DataAccessLayerException {
    Double kwValue = 10.0;
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    OperatingStateId operatingStateId = new OperatingStateId(1, equipment.getId());
    OperatingState operatingState = new OperatingState(operatingStateId, equipment, kwValue);
    OperatingStateService operatingStateService = new OperatingStateService(hibernateUtil);
    operatingStateService.insert(operatingState);

    operatingStateService.delete(operatingState);

    operatingStateService.find(operatingStateId);
  }

  @Test
  public void testFind() throws DataAccessLayerException {
    Double kwValue = 10.0;
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    OperatingStateId operatingStateId = new OperatingStateId(1, equipment.getId());
    OperatingState operatingState = new OperatingState(operatingStateId, equipment, kwValue);

    OperatingStateService operatingStateService = new OperatingStateService(hibernateUtil);
    operatingStateService.insert(operatingState);

    OperatingState operatigStateFound = operatingStateService.find(operatingStateId);
    Assert.assertEquals(operatingState.getId(), operatigStateFound.getId());
    Assert.assertEquals(operatingState.getKw(), operatigStateFound.getKw());
  }

  @Test
  public void testUpdate() throws DataAccessLayerException {
    Double kwValue = 10.0;
    Double kwValueNew = 20.5;
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Equipment equipment = equipmentService.find(1);
    OperatingStateId operatingStateId = new OperatingStateId(1, equipment.getId());
    OperatingState operatingState = new OperatingState(operatingStateId, equipment, kwValue);

    OperatingStateService operatingStateService = new OperatingStateService(hibernateUtil);
    operatingStateService.insert(operatingState);
    operatingState.setKw(kwValueNew);
    operatingStateService.update(operatingState);

    OperatingState operatigStateFound = operatingStateService.find(operatingStateId);
    Assert.assertEquals(kwValueNew, operatigStateFound.getKw());
  }

  @AfterMethod
  public void closeHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }

}