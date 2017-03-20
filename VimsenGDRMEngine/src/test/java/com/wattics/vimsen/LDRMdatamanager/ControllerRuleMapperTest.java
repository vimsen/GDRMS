package com.wattics.vimsen.LDRMdatamanager;

import java.util.List;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.EDMSdatamanager.EDMSMockDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.MockGDRMDataGetter;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class ControllerRuleMapperTest {

  private Plan getPlan() throws MapperException {
    DateTime dateTimeStart = new DateTime("2016-02-10T13:00:00.000+02:00");
    String startTimeText = dateTimeStart.toString();
    Double[] reduction = new Double[] { 1.0, 2.0 };
    List<Integer> primaryProsumers = VimsenTestUtil.asList(1, 2);
    List<Integer> secondaryProsumers = VimsenTestUtil.asList(3, 4);

    MarketSignal marketSignal = VimsenTestUtil.setUpMarketSignal(startTimeText, dateTimeStart, reduction, primaryProsumers,
        secondaryProsumers);
    Plan plan = new Plan(marketSignal);
    plan.setId(1);
    plan.setStatus(PlanStatusEnum.CREATED.toString());

    return plan;
  }

  @Test
  public void mapRuleControllerId() throws DataAccessLayerException, EDMSDataGetterException, MapperException {
    Plan plan = getPlan();
    EDMSDataGetterInterface edmsDataGetter = new EDMSMockDataGetter();
    GDRMDataGetterInterface dataGetter = new MockGDRMDataGetter();
    String jsonRule = ControllerRuleMapper.mapPlanActionsInControllerRulesJsonMap(plan, edmsDataGetter, dataGetter);

    System.out.println(jsonRule);
  }

}
