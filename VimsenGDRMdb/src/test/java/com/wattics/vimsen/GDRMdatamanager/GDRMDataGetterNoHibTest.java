package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.general.PlanStatusEnum;

public class GDRMDataGetterNoHibTest {

  private String testConfigurationFile = "schemaTestConfig.cfg.xml";
  private HibernateUtil hibernateUtil;

  @BeforeMethod
  public void createHibernateSessionFactory() throws DataAccessLayerException {
    hibernateUtil = new HibernateUtil(testConfigurationFile);
    DatabaseSetUp.cleanDb(hibernateUtil);
    DatabaseSetUp.populateDb(hibernateUtil, 4);
  }

  private MarketSignal insertMarketSignal() throws DataAccessLayerException {
    List<String> prosumers = new ArrayList<String>();
    prosumers.add("Pr_1");
    prosumers.add("Pr_2");
    MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil, DateTime.now().toString(), DateTime.now(),
        new Double[] { 1.0 }, prosumers, prosumers);
    return marketSignal;
  }

  @Test
  public void getOnePlanInOngoingStatus() throws DataAccessLayerException {
    MarketSignal marketSignal = insertMarketSignal();
    PlanService planService = new PlanService(hibernateUtil);
    insertPlanWithStatus(marketSignal, planService, PlanStatusEnum.ONGOING);

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);

    List<Plan> plansCreatedAndOngoing = GDRMDataGetterNoHib.getPlanInCreatedAndOngoingStatus(dataGetter);

    Assert.assertEquals(plansCreatedAndOngoing.size(), 1);
  }

  private void insertPlanWithStatus(MarketSignal marketSignal, PlanService planService, PlanStatusEnum planStatus)
      throws DataAccessLayerException {
    Plan planOngoing = new Plan(marketSignal);
    planOngoing.setStatus(planStatus.toString());
    planService.insert(planOngoing);
  }

  @Test
  public void getTwoPlanInCreatedAndOngoingStatus() throws DataAccessLayerException {
    MarketSignal marketSignal = insertMarketSignal();
    PlanService planService = new PlanService(hibernateUtil);
    insertPlanWithStatus(marketSignal, planService, PlanStatusEnum.ONGOING);
    insertPlanWithStatus(marketSignal, planService, PlanStatusEnum.CREATED);

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);

    List<Plan> plansCreatedAndOngoing = GDRMDataGetterNoHib.getPlanInCreatedAndOngoingStatus(dataGetter);

    Assert.assertEquals(plansCreatedAndOngoing.size(), 2);
  }

  @Test
  public void getTwoPlanInCreatedAndOngoingStatusDiscardCompleted() throws DataAccessLayerException {
    MarketSignal marketSignal = insertMarketSignal();
    PlanService planService = new PlanService(hibernateUtil);
    insertPlanWithStatus(marketSignal, planService, PlanStatusEnum.ONGOING);
    insertPlanWithStatus(marketSignal, planService, PlanStatusEnum.CREATED);
    insertPlanWithStatus(marketSignal, planService, PlanStatusEnum.COMPLETED);

    GDRMDataGetterInterface dataGetter = new GDRMDataGetter(hibernateUtil);

    List<Plan> plansCreatedAndOngoing = GDRMDataGetterNoHib.getPlanInCreatedAndOngoingStatus(dataGetter);

    Assert.assertEquals(plansCreatedAndOngoing.size(), 2);
  }

  @AfterMethod
  public void cleanHibernateSessionFactory() throws DataAccessLayerException {
    DatabaseSetUp.cleanDb(hibernateUtil);
  }

}
