package com.wattics.vimsen.entities.validator;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
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
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.TimeHelpers;
import com.wattics.vimsen.utils.VimsenTestUtil;
import org.joda.time.DateTime;

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
    
    hibernateUtil = new HibernateUtil(configurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, 4);
    
    String startTimeText = "2016-01-10T13:00:00.000+02:00";                     
    DateTime startTime = TimeHelpers.createDateTimePreservingTimeZone(startTimeText);
                                                                                
    Double[] reduction = new Double[] { 10.0, 2.0 };                            
    List<String> primaryProsumers = VimsenTestUtil.asList("Pr_0", "Pr_1");      
    List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_2", "Pr_3");    
                                                                                
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, startTime, reduction,
        primaryProsumers, secondaryProsumers);        
    
    Plan p = new Plan();
    p.setStatus(PlanStatusEnum.CREATED.toString());
    p.setMarketSignal(marketSignal);
    PlanService planService = new PlanService(hibernateUtil);
    planService.insert(p);
            
    Validation validation = new Validation(hibernateUtil);
    List<Plan> plans = validation.getPlanInCreatedAndOngoingStatus();

    Assert.assertTrue(plans.size() > 0);

  }
}
