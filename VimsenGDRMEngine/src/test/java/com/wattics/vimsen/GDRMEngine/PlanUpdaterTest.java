package com.wattics.vimsen.GDRMEngine;

import java.util.List;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.EDMSdatamanager.EDMSMockDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.MockGDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.ValidationMock;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class PlanUpdaterTest {

  // Uses mock classes. To be modified

  String testConfigurationFile = "localhostJavaTest.cfg.xml";
  HibernateUtil hibernateUtil;
  int numberProsumers = 30;

  @Test
  public void planRegisteredIsNotUpdated() throws DataAccessLayerException, EDMSDataGetterException {

    EDMSDataGetterInterface edmsDataGetter = new EDMSMockDataGetter();
    ValidationMock dataGetter = new ValidationMock();
    GDRMDataStorerInterface dataStorer = new MockGDRMDataStorer();

    DateTime dateTimeStart = DateTime.now().minusMinutes(20);
    String startTimeText = dateTimeStart.toString();
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<Integer> primaryProsumers = VimsenTestUtil.asList(1, 2);
    List<Integer> secondaryProsumers = VimsenTestUtil.asList(3, 4);

    MarketSignal marketSignal = VimsenTestUtil.setUpMarketSignal(startTimeText, dateTimeStart, reduction, primaryProsumers,
        secondaryProsumers);
    Plan plan = new Plan(marketSignal);
    plan.setStatus(PlanStatusEnum.REGISTERED.toString());

    PlanUpdater.updatePlanMap(dataGetter, dataStorer, edmsDataGetter, plan, marketSignal);

    Assert.assertEquals(plan.getStatus(), PlanStatusEnum.REGISTERED.toString());
  }

  @Test(enabled  =false)
  public void planCreatedIsUpdatedToOngoingTest() throws DataAccessLayerException, EDMSDataGetterException, MapperException {

    EDMSDataGetterInterface edmsDataGetter = new EDMSMockDataGetter();
    ValidationMock dataGetter = new ValidationMock();
    GDRMDataStorerInterface dataStorer = new MockGDRMDataStorer();

    DateTime dateTimeStart = DateTime.now().minusMinutes(20);
    String startTimeText = dateTimeStart.toString();
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<Integer> primaryProsumers = VimsenTestUtil.asList(1, 2);
    List<Integer> secondaryProsumers = VimsenTestUtil.asList(3, 4);

    MarketSignal marketSignal = VimsenTestUtil.setUpMarketSignal(startTimeText, dateTimeStart, reduction, primaryProsumers,
        secondaryProsumers);
    Plan plan = new Plan(marketSignal);
    plan.setId(1);
    plan.setStatus(PlanStatusEnum.CREATED.toString());

    plan = VimsenTestUtil.addActionsToCreatedPlan(plan);

    PlanUpdater.updatePlanMap(dataGetter, dataStorer, edmsDataGetter, plan, marketSignal);

    Assert.assertEquals(plan.getStatus(), PlanStatusEnum.ONGOING.toString());
  }

  @Test(enabled  =false)
  public void planCreatedIsUpdatedToCompletedTest() throws DataAccessLayerException, EDMSDataGetterException, MapperException {

    EDMSDataGetterInterface edmsDataGetter = new EDMSMockDataGetter();
    ValidationMock dataGetter = new ValidationMock();
    GDRMDataStorerInterface dataStorer = new MockGDRMDataStorer();

    DateTime dateTimeStart = DateTime.now().minusMinutes(35);
    String startTimeText = dateTimeStart.toString();
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<Integer> primaryProsumers = VimsenTestUtil.asList(1, 2);
    List<Integer> secondaryProsumers = VimsenTestUtil.asList(3, 4);

    MarketSignal marketSignal = VimsenTestUtil.setUpMarketSignal(startTimeText, dateTimeStart, reduction, primaryProsumers,
        secondaryProsumers);
    Plan plan = new Plan(marketSignal);
    plan.setId(1);
    plan.setStatus(PlanStatusEnum.CREATED.toString());

    plan = VimsenTestUtil.addActionsToCreatedPlan(plan);

    PlanUpdater.updatePlanMap(dataGetter, dataStorer, edmsDataGetter, plan, marketSignal);

    Assert.assertEquals(plan.getStatus(), PlanStatusEnum.COMPLETED.toString());
  }

}
