package com.wattics.vimsen.LDRMdatamanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class MqttControllerRuleMapperTest {

  private String testConfigurationFile = "localhostJavaTest.cfg.xml";
  private HibernateUtil hibernateUtil;
  private int numberProsumers = 26;

  private MarketSignal storeMarketSignal() throws DataAccessLayerException {
    System.out.println("Run test storeMarketSignalAndPlan in PlanManager");
    DateTime dateTimeStart = DateTime.now().minusMinutes(20);
    String startTimeText = dateTimeStart.toString();
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<String> primaryProsumers = VimsenTestUtil.asList("Pr_1", "Pr_2");
    List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_3", "Pr_4");
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, startTimeText, dateTimeStart, reduction,
        primaryProsumers, secondaryProsumers);
    return marketSignal;
  }

  private int storePlan(MarketSignal marketSignal) throws DataAccessLayerException {
    Plan plan = DatabaseSetUp.storePlan(hibernateUtil, marketSignal);
    int planId = plan.getId();
    return planId;
  }

  @BeforeMethod
  public void createHibernateSessionFactory() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);

  }

  @Test
  public void generateMqttTopicForAControlAction() throws DataAccessLayerException, MapperException, NoValidDataException {
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    Validation getAndValidate = new Validation(hibernateUtil);
    MarketSignal marketSignal = storeMarketSignal();
    int planId = storePlan(marketSignal);
    storeActionsInPlan(dataGetter, planId, 1);
    Plan plan = dataGetter.getPlan(planId);
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActions = plan.getPlanHasActions();
    PlanHasAction planHasAction = planActions.iterator().next();
    List<PlanHasAction> planHasActions = new ArrayList<>();
    planHasActions.add(planHasAction);
    Pair<String, String> controlActions = MqttControllerRuleMapper.mapPlannedActionInControllerRulesMap(plan, planHasActions,
        getAndValidate);

    String expectedTopic = DatabaseSetUp.DEFAULT_USER;
    Assert.assertEquals(controlActions.getLeft(), expectedTopic);
  }

  @Test
  public void generateMqttMessageForAControlAction() throws DataAccessLayerException, MapperException, NoValidDataException {
    DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    Validation getAndValidate = new Validation(hibernateUtil);
    MarketSignal marketSignal = storeMarketSignal();
    int planId = storePlan(marketSignal);
    storeActionsInPlan(dataGetter, planId, 1);
    Plan plan = dataGetter.getPlan(planId);
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActions = plan.getPlanHasActions();
    PlanHasAction planHasAction = planActions.iterator().next();
    List<PlanHasAction> planHasActions = new ArrayList<>();
    planHasActions.add(planHasAction);
    Pair<String, String> controlActions = MqttControllerRuleMapper.mapPlannedActionInControllerRulesMap(plan, planHasActions,
        getAndValidate);

    String drAction = DatabaseSetUp.DEFAULT_DR_ACTION;
    String deviceName = "fibaro10";
    String actionTime = TimeHelpers.getLocalTimeHourAndMinutes(marketSignal.getStartTimeText(), marketSignal.getStartTime());
    String actionDate = TimeHelpers.getLocalDate(marketSignal.getStartTimeText(), marketSignal.getStartTime());

    String expectedTopic = "{\"dr\":\"" + drAction + "\",\"devices\":[\"" + deviceName + "\"],\"time\":\"" + actionTime
        + "\",\"date\":\"" + actionDate + "\"}";
    Assert.assertEquals(controlActions.getRight(), expectedTopic);
  }

  @Test
  public void oneProsumerWithTwoActionsAtSameTimeGeneratesOneMessage() throws DataAccessLayerException, MapperException {
    DatabaseSetUp.populateDbNumberEquipmentPerProsumer(hibernateUtil, 26, 2);
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    Validation getAndValidate = new Validation(hibernateUtil);
    MarketSignal marketSignal = storeMarketSignal();
    int planId = storePlan(marketSignal);
    storeActionsInPlan(dataGetter, planId, 1);
    storeActionsInPlan(dataGetter, planId, 2);
    Plan plan = dataGetter.getPlan(planId);
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActions = plan.getPlanHasActions();

    List<PlanHasAction> planHasActions = new ArrayList<>();
    Iterator<PlanHasAction> it = planActions.iterator();
    for (int i = 0; i < 2; i++) {
      PlanHasAction planHasAction = it.next();
      planHasActions.add(planHasAction);
    }
    List<Pair<String, String>> controlActions = MqttControllerRuleMapper.mapPlanActionsInControllerRulesJsonMap(plan,
        getAndValidate);

    Assert.assertEquals(controlActions.size(), 1);
  }

  @Test
  public void twoProsumersWithTwoActionsGenerateTwoMessages() throws DataAccessLayerException, MapperException {
    DatabaseSetUp.populateDbNumberEquipmentPerProsumer(hibernateUtil, 26, 1);
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    Validation getAndValidate = new Validation(hibernateUtil);
    MarketSignal marketSignal = storeMarketSignal();
    int planId = storePlan(marketSignal);
    storeActionsInPlan(dataGetter, planId, 1);
    storeActionsInPlan(dataGetter, planId, 2);
    Plan plan = dataGetter.getPlan(planId);
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActions = plan.getPlanHasActions();

    List<PlanHasAction> planHasActions = new ArrayList<>();
    Iterator<PlanHasAction> it = planActions.iterator();
    for (int i = 0; i < 2; i++) {
      PlanHasAction planHasAction = it.next();
      planHasActions.add(planHasAction);
    }
    List<Pair<String, String>> controlActions = MqttControllerRuleMapper.mapPlanActionsInControllerRulesJsonMap(plan,
        getAndValidate);

    Assert.assertEquals(controlActions.size(), 2);
  }

  @Test
  public void oneProsumerWithTwoActionsAtDifferentTimeGenerateTwoMessages() throws MapperException, DataAccessLayerException {
    DatabaseSetUp.populateDbNumberEquipmentPerProsumer(hibernateUtil, 26, 2);
    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);
    Validation getAndValidate = new Validation(hibernateUtil);
    MarketSignal marketSignal = storeMarketSignal();
    int planId = storePlan(marketSignal);
    storeActionsInPlan(dataGetter, planId, 1);
    storeActionsInPlan(dataGetter, planId, 2);
    Plan plan = dataGetter.getPlan(planId);
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActions = plan.getPlanHasActions();

    List<PlanHasAction> planHasActions = new ArrayList<>();
    Iterator<PlanHasAction> it = planActions.iterator();
    PlanHasAction planHasAction = it.next();
    planHasActions.add(planHasAction);
    PlanHasAction planHasAction2 = it.next();
    Double[] plannedAmount = FormatConverter.stringToArrayDouble(planHasAction2.getPlannedAmount());
    plannedAmount[0] = 0.0;
    planHasAction2.setPlannedAmount(Arrays.toString(plannedAmount));
    planHasActions.add(planHasAction2);

    List<Pair<String, String>> controlActions = MqttControllerRuleMapper.mapPlanActionsInControllerRulesJsonMap(plan,
        getAndValidate);

    Assert.assertEquals(controlActions.size(), 2);
  }

  private void storeActionsInPlan(GDRMDataGetterInterface dataGetter, int planId, int numberActions)
      throws DataAccessLayerException {
    Plan plan = dataGetter.getPlan(planId);
    DatabaseSetUp.storeActionsInPlan(hibernateUtil, plan, numberActions);
  }

  @AfterMethod
  public void cleanDBAndCloseHibernate() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
    hibernateUtil.closeSessionFactory();
    hibernateUtil.setSessionFactoryToNull();
  }
}
