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
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class PlanGeneratorTest {

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
    System.out.println("Before test Plan Generator");
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  }

  @Test
  public void generatePlanActions() throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    System.out.println("Run test generatePlanActions in Plan Generator");
    int planId = storeMarketSignalAndPlan();

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    EDMSDataGetterInterface edmsDataGetter = new EDMSDataGetter();
    List<PlanHasAction> plannedActions = PlanGenerator.generatePlanActionsMap(planId, dataGetter, edmsDataGetter);

    Assert.assertEquals(plannedActions.size(), 2);
    PlanHasAction plannedAction = plannedActions.get(0);
    String plannedAmount = plannedAction.getPlannedAmount();
    Double[] amounts = FormatConverter.stringToArrayDouble(plannedAmount);
    for (Double amount : amounts) {
      Assert.assertEquals(amount, Double.NaN);
    }
  }

  @Test
  public void generatedActionsAreValid() throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    System.out.println("Run test generatedActionsAreValid in Plan Generator");
    String invalidPlanAmount = "[1.0, 2.0]";
    List<PlanHasAction> planActions = new ArrayList<PlanHasAction>();
    PlanHasAction planAction = new PlanHasAction();
    planAction.setPlannedAmount(invalidPlanAmount);
    planActions.add(planAction);

    boolean actionsAreValid = PlanGenerator.generatedActionsAreValid(planActions);

    Assert.assertTrue(actionsAreValid);
  }

  @Test
  public void generatedActionsNotValid() throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    System.out.println("Run test generatedActionsNotValid in Plan Generator");
    String invalidPlanAmount = "boh";
    List<PlanHasAction> planActions = new ArrayList<PlanHasAction>();
    PlanHasAction planAction = new PlanHasAction();
    planAction.setPlannedAmount(invalidPlanAmount);
    planActions.add(planAction);

    boolean actionsAreValid = PlanGenerator.generatedActionsAreValid(planActions);

    Assert.assertFalse(actionsAreValid);
  }

  @Test
  public void plansEnding24HFromNowAreSelected() throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    System.out.println("Run test plansEnding24HFromNowAreSelected in Plan Generator");
    DateTime dateTimeEnd = DateTime.now().plusDays(1).minusHours(1);
    DsoTerritory dsoTerritory = new DsoTerritory(DatabaseSetUp.DEFAULT_DSOTERRITORY_ID);
    MarketSignal marketSignal = new MarketSignal(dsoTerritory);
    marketSignal.setEndTime(dateTimeEnd);
    List<Plan> plans = new ArrayList<Plan>();
    plans.add(new Plan(marketSignal));

    List<Plan> plansToBeprocessed = PlanGenerator.selectPlansEndingBefore24HFromNow(plans);

    Assert.assertEquals(plansToBeprocessed.size(), 1);
  }

  @Test
  public void plansEndingAfter24HFromNowAreNotSelected()
      throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    System.out.println("Run test plansEndingAfter24HFromNowAreNotSelected in Plan Generator");
    DateTime dateTimeEnd = DateTime.now().plusDays(2);
    DsoTerritory dsoTerritory = new DsoTerritory(DatabaseSetUp.DEFAULT_DSOTERRITORY_ID);
    MarketSignal marketSignal = new MarketSignal(dsoTerritory);
    marketSignal.setEndTime(dateTimeEnd);
    List<Plan> plans = new ArrayList<Plan>();
    plans.add(new Plan(marketSignal));

    List<Plan> plansToBeprocessed = PlanGenerator.selectPlansEndingBefore24HFromNow(plans);

    Assert.assertEquals(plansToBeprocessed.size(), 0);
  }

  @Test
  public void plansEndingBeforeNowAreNotSelected() throws EDMSDataGetterException, MapperException, DataAccessLayerException {
    System.out.println("Run test plansEndingBeforeNowAreNotSelected in Plan Generator");
    DateTime dateTimeEnd = DateTime.now().minusHours(1);
    DsoTerritory dsoTerritory = new DsoTerritory(DatabaseSetUp.DEFAULT_DSOTERRITORY_ID);
    MarketSignal marketSignal = new MarketSignal(dsoTerritory);
    marketSignal.setEndTime(dateTimeEnd);
    List<Plan> plans = new ArrayList<Plan>();
    plans.add(new Plan(marketSignal));

    List<Plan> plansToBeprocessed = PlanGenerator.selectPlansEndingBefore24HFromNow(plans);

    Assert.assertEquals(plansToBeprocessed.size(), 0);
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    System.out.println("After test Plan Generator");
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
