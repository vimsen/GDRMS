package com.wattics.vimsen.GDRMEngine;

import java.util.List;

import org.testng.annotations.Test;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.LDRMdatamanager.LDRMRuleException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.utils.MapperException;

public class PlanGeneratorTest {

  private String testConfigurationFile = "localhostJavaTest.cfg.xml";
  private HibernateUtil hibernateUtil = new HibernateUtil(testConfigurationFile);

  @Test
  public void debugPlanCreation()
      throws LDRMRuleException, DataAccessLayerException, MapperException, EDMSDataGetterException, NoValidDataException {

    Validation dataGetter = new Validation(hibernateUtil);
    GDRMDataStorerInterface dataStorer = new GDRMDataStorer(hibernateUtil);
    List<Plan> plans = PlanManager.selectPlansInRegisteredStatusToBeProcessed(dataGetter, dataStorer);

    PlanManager.generateAndStorePlansMap(plans, dataGetter, dataStorer);
  }

  @Test
  public void debugPlanUpdate()
      throws DataAccessLayerException, EDMSDataGetterException, NoValidDataException, MapperException, InterruptedException {
    String planId = "310";
    Validation dataGetter = new Validation(hibernateUtil);
    GDRMDataStorerInterface dataStorer = new GDRMDataStorer(hibernateUtil);
    EDMSDataGetterInterface edmsDataGetter = new EDMSDataGetter();
    PlanManager.updatePlanIfNotUpToDateMap(planId, dataGetter, dataStorer, edmsDataGetter);
  }

}
