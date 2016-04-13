package com.wattics.vimsen.GDRMmanager;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMEngine.PlanManager;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.general.DefaultSettings;
import com.wattics.vimsen.utils.MapperException;

public class DSSManager {

  private GDRMDataGetterInterface dataGetter;
  private GDRMDataStorerInterface dataStorer;
  private EDMSDataGetterInterface edmsDataGetter;

  public DSSManager(HibernateUtil hibernateUtil) {
    dataGetter = new GDRMDataGetter(hibernateUtil);
    dataStorer = new GDRMDataStorer(hibernateUtil);
    edmsDataGetter = new EDMSDataGetter();
  }

  public String storeDRRequestReturnAck(String drRequest) throws MapperException, DataAccessLayerException {
    DsoTerritory dsoTerritory = dataGetter.getDsoTerritory(DefaultSettings.DSOTERRITORY_ID);
    String createdPlan = DRPostRequestManager.storeJsonDRRequestReturnJsonAck(dataStorer, drRequest, dsoTerritory);
    return createdPlan;
  }

  public String getPlanCurrentStatusMap(String planId) throws MapperException, DataAccessLayerException, EDMSDataGetterException {
    String planJson = null;

    PlanManager.updatePlanIfNotUpToDateMap(planId, dataGetter, dataStorer, edmsDataGetter);
    planJson = DRGetRequestManager.getPlanStatusJson(planId, dataGetter);

    return planJson;
  }

}
