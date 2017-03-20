package com.wattics.vimsen.GDRMmanager;

//
//import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
//import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
//import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.general.DefaultSettings;
import com.wattics.vimsen.utils.MapperException;

public class DSSManager {

  private GDRMDataGetterAndValidationInterface dataGetter;
  private GDRMDataStorerInterface dataStorer;
  // private EDMSDataGetterInterface edmsDataGetter;

  public DSSManager(HibernateUtil hibernateUtil) {
    dataGetter = new Validation(hibernateUtil);
    dataStorer = new GDRMDataStorer(hibernateUtil);
    // edmsDataGetter = new EDMSDataGetter();
  }

  public String storeDRRequestReturnAck(String drRequest) throws MapperException, DataAccessLayerException, NoValidDataException {
    DsoTerritory dsoTerritory = dataGetter.getDsoTerritory(DefaultSettings.DSOTERRITORY_ID);
    String createdPlan = DRPostRequestManager.storeJsonDRRequestReturnJsonAck(dataStorer, dataGetter, drRequest, dsoTerritory);
    return createdPlan;
  }

  public String getPlanCurrentStatusMap(String planId) throws MapperException, DataAccessLayerException, NoValidDataException {
    String planJson = null;

    // PlanManager.updatePlanIfNotUpToDateMap(planId, dataGetter,
    // dataStorer, edmsDataGetter);
    planJson = DRGetRequestManager.getPlanStatusJson(planId, dataGetter);

    return planJson;
  }

}
