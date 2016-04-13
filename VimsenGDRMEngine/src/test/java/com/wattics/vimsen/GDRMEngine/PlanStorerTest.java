package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class PlanStorerTest {
  private String testConfigurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;
  private int numberProsumers = 26;

  private int storeMarketSignalAndPlan() throws DataAccessLayerException {
    String startTimeText = "2015-11-19T13:00:00.000+02:00";
    DateTime dateTimeStart = new DateTime(startTimeText);
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<Integer> primaryProsumers = VimsenTestUtil.asList(1, 2);
    List<Integer> secondaryProsumers = VimsenTestUtil.asList(3, 4);
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, dateTimeStart, reduction,
        primaryProsumers, secondaryProsumers);
    Plan plan = DatabaseSetUp.storePlan(hibernateUtil, marketSignal);
    int planId = plan.getId();
    return planId;
  }

  @BeforeMethod
  public void createHibernateSessionFactory() throws DataAccessLayerException {
    System.out.println("BeforeTest Plan Storer");
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test
  public void generateAndStorePlanActions() throws DataAccessLayerException, EDMSDataGetterException, MapperException {
    System.out.println("Run test generateAndStorePlanActions in PlanStorer");
    int planId = storeMarketSignalAndPlan();
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    GDRMDataStorerInterface dataStorer = new GDRMDataStorer(hibernateUtil);
    EDMSDataGetterInterface edmsDataGetter = new EDMSDataGetter();
    List<PlanHasAction> plannedActions = PlanGenerator.generatePlanActionsMap(planId, dataGetter, edmsDataGetter);

    PlanStorer.storeGeneratedPlanActions(planId, plannedActions, dataStorer);

    Plan planSaved = dataGetter.getPlan(planId);
    @SuppressWarnings("unchecked")
    List<PlanHasAction> plannedActionsSaved = new ArrayList<PlanHasAction>(planSaved.getPlanHasActions());

    Assert.assertEquals(plannedActionsSaved.size(), plannedActions.size());
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    System.out.println("@AfterTest Plan Storer");
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
