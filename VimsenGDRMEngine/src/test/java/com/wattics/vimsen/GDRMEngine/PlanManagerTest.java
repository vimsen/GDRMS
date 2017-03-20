package com.wattics.vimsen.GDRMEngine;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class PlanManagerTest {

  String testConfigurationFile = "localhostJavaTest.cfg.xml";
  HibernateUtil hibernateUtil;
  int numberProsumers = 30;

  int planId;

  @BeforeMethod
  public void initializeHibernate() throws DataAccessLayerException {
    System.out.println("BeforeTest Plan Manager");
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
    planId = storeMarketSignalAndPlan();
  }

  private int storeMarketSignalAndPlan() throws DataAccessLayerException {
    System.out.println("Run test storeMarketSignalAndPlan in PlanManager");
    DateTime dateTimeStart = DateTime.now().minusMinutes(20);
    String startTimeText = dateTimeStart.toString();
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<String> primaryProsumers = VimsenTestUtil.asList("Pr_1", "Pr_2");
    List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_3", "Pr_4");
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, dateTimeStart, reduction,
        primaryProsumers, secondaryProsumers);
    Plan plan = DatabaseSetUp.storePlan(hibernateUtil, marketSignal);
    int planId = plan.getId();
    return planId;
  }

  @Test
  public void updatePlanIfNotUpdateTest()
      throws DataAccessLayerException, EDMSDataGetterException, NoValidDataException, MapperException, InterruptedException {
    System.out.println("Run test updatePlanIfNotUpdateTest in PlanManager");
    int planId = storeMarketSignalAndPlan();
    String planIdString = "" + planId;
    Validation dataGetter = new Validation(hibernateUtil);
    GDRMDataStorer dataStorer = new GDRMDataStorer(hibernateUtil);
    EDMSDataGetterInterface edmsDataGetter = new EDMSDataGetter();

    PlanManager.updatePlanIfNotUpToDateMap(planIdString, dataGetter, dataStorer, edmsDataGetter);

    DatabaseSetUp.removeMarketSignalAndPlans(hibernateUtil);
  }

  @Test(expectedExceptions = ObjectNotFoundException.class)
  public void throwExceptionIfPlanIsNotFound()
      throws DataAccessLayerException, EDMSDataGetterException, NoValidDataException, MapperException, InterruptedException {
    System.out.println("Run test throwExceptionIfPlanIsNotFound in PlanManager");
    String planId = "10";
    Validation dataGetter = new Validation(hibernateUtil);
    GDRMDataStorer dataStorer = new GDRMDataStorer(hibernateUtil);
    EDMSDataGetterInterface edmsDataGetter = new EDMSDataGetter();

    PlanManager.updatePlanIfNotUpToDateMap(planId, dataGetter, dataStorer, edmsDataGetter);
  }

  @Test
  public void selectPlansInRegisteredStatus() throws DataAccessLayerException, NoValidDataException {
    System.out.println("Run test selectPlansInRegisteredStatus in PlanManager");
    storeMarketSignalAndPlan();
    Validation dataGetter = new Validation(hibernateUtil);
    GDRMDataStorerInterface dataStorer = new GDRMDataStorer(hibernateUtil);

    List<Plan> plans = PlanManager.selectPlansInRegisteredStatusToBeProcessed(dataGetter, dataStorer);

    Assert.assertEquals(plans.size(), 1);

    DatabaseSetUp.removeMarketSignalAndPlans(hibernateUtil);
  }

  @AfterMethod
  public void closeHibernate() throws DataAccessLayerException {
    System.out.println("AfterTest Plan Manager");
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
