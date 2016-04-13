package com.wattics.entities_services;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities_services.EquipmentService;
import com.wattics.vimsen.entities_services.Kwh15mnService;
import com.wattics.vimsen.entities_services.SiteService;

public class OneToManyMappingTest {

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
  public void addElementToCollection() throws DataAccessLayerException {
    int siteId = 1;
    SiteService siteService = new SiteService(hibernateUtil);
    Site siteFound = siteService.find(siteId);
    int equipmentSize = siteFound.getEquipments().size();
    Equipment newEquipment = new Equipment();

    siteFound.getEquipments().add(newEquipment);

    Set<Equipment> equipments = siteFound.getEquipments();
    Assert.assertEquals(equipments.size(), equipmentSize + 1);
  }

  @Test
  public void insertNewElement() throws DataAccessLayerException {
    int siteId = 1;
    SiteService siteService = new SiteService(hibernateUtil);
    Site siteFound = siteService.find(siteId);
    int equipmentSize = siteFound.getEquipments().size();
    Equipment newEquipment = new Equipment(7, siteFound);
    siteFound.getEquipments().add(newEquipment);

    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    equipmentService.insert(newEquipment);

    Site updatedSite = siteService.find(siteId);
    Set<Equipment> equipments = updatedSite.getEquipments();
    Assert.assertEquals(equipments.size(), equipmentSize + 1);
  }

  @Test
  public void updateOrInsertElement() throws DataAccessLayerException {
    int siteId = 1;
    SiteService siteService = new SiteService(hibernateUtil);
    Site siteFound = siteService.find(siteId);
    int equipmentSize = siteFound.getEquipments().size();
    int updatesNumberEq = equipmentSize;

    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    try {
      Equipment newEquipment = equipmentService.find(10);
      equipmentService.update(newEquipment);
    } catch (ObjectNotFoundException e) {
      Equipment newEq = new Equipment(10, siteFound);
      equipmentService.insert(newEq);
    }
  }

  @Test
  public void updateElementCompositeId() throws DataAccessLayerException {
    EquipmentService equipmentService = new EquipmentService(hibernateUtil);
    Kwh15mnService kwh15mnService = new Kwh15mnService(hibernateUtil);
    Equipment eq = equipmentService.find(1);
    DateTime time = new DateTime(2015, 10, 21, 0, 0);
    Kwh15mnId id = new Kwh15mnId(eq.getId(), time);
    Kwh15mn kw = new Kwh15mn(id, eq);
    kw.setT0000(20.0);
    kwh15mnService.insert(kw);

    Kwh15mn kwFound = kwh15mnService.find(id);
    kwFound.setT0000(10.0);
    kwh15mnService.update(kwFound);
  }

  @Test
  public void collectionAddingElements() {
    Set<Integer> set = new HashSet<Integer>();
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(1);

    Assert.assertEquals(set.size(), 3);
  }

  @AfterMethod
  public void closeHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }

}
