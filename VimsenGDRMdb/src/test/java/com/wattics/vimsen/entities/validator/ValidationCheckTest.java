package com.wattics.vimsen.entities.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Site;

public class ValidationCheckTest {

  // static String testConfigurationFile = "schemaTestConfig.cfg.xml";
  static String configurationFile = "hibernate.cfg.xml";
  // static String localhostConftestFile = "localhostTestConfig.cfg.xml";
  private static HibernateUtil hibernateUtil = new HibernateUtil(configurationFile);

  @Test
  public void siteWithoutIdIsNotValid() {
    Site site = new Site();
    site.setName("Rome");

    boolean isSiteValid = ValidationCheck.isObjectValid(site);

    Assert.assertFalse(isSiteValid);
  }

  @Test
  public void siteWithoutEquipmentIsNotValid() {
    Site site = new Site();
    site.setId(1);

    boolean isSiteValid = ValidationCheck.isObjectValid(site);

    Assert.assertFalse(isSiteValid);
  }

  @Test
  public void validSite() {
    Site site = new Site();
    site.setId(1);
    Set<Equipment> equipments = new HashSet<Equipment>();
    equipments.add(new Equipment());
    site.setEquipments(equipments);

    boolean isSiteValid = ValidationCheck.isObjectValid(site);

    Assert.assertTrue(isSiteValid);
  }

  @Test
  public void temprorayTestForDataGetterAndValidation() throws NoValidDataException, DataAccessLayerException {
    Validation validation = new Validation(hibernateUtil);
    List<Plan> plans = validation.getPlanInCreatedAndOngoingStatus();

    Assert.assertTrue(plans.size() > 0);

  }
}
