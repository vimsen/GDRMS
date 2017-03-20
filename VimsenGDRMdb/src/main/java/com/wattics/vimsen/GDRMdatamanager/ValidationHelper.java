package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.validator.ValidationCheck;

public class ValidationHelper {

  GDRMDataGetterInterface dataGetter;
  BasicDataGetter basicDataGetter;

  public ValidationHelper(HibernateUtil hibernateUtil) {
    this.dataGetter = new GDRMDataGetter(hibernateUtil);
    this.basicDataGetter = new BasicDataGetter(hibernateUtil);
  }

  public Site getSiteFromProsumerId(int prosumerId) throws DataAccessLayerException, NoValidDataException {
    Site site = dataGetter.getSiteFromProsumerId(prosumerId);
    if (ValidationCheck.isObjectValid(site)) {
      return site;
    }
    throw new NoValidDataException("No valid site available for the selected prosumer");
  }

  public List<Action> getActionsFromProsumerId(int prosumerId) throws DataAccessLayerException, NoValidDataException {
    List<Action> actions = dataGetter.getActionsFromProsumerId(prosumerId);
    List<Action> validActions = (List<Action>) (Object) ValidationCheck.returnValidListObject(actions);
    if (validActions.size() > 0) {
      return validActions;
    }
    throw new NoValidDataException("No valid actions available for the selected prosumer");
  }

  public Equipment getEquipment(int id, String name, Site site) throws DataAccessLayerException, NoValidDataException {
    Equipment equipment = dataGetter.getEquipment(id, name, site);
    if (ValidationCheck.isObjectValid(equipment)) {
      return equipment;
    }
    throw new NoValidDataException("No valid equipment");
  }

  public MarketSignal getMarketSignal(int marketSignalId) throws DataAccessLayerException, NoValidDataException {
    MarketSignal ms = dataGetter.getMarketSignal(marketSignalId);
    if (ValidationCheck.isObjectValid(ms)) {
      return ms;
    }
    throw new NoValidDataException("No valid marketSignal for the selected id");
  }

  public List<Plan> getPlansWithStatus(String planStatus) throws DataAccessLayerException, NoValidDataException {
    List<Plan> plans = dataGetter.getPlansWithStatus(planStatus);
    List<Plan> validPlans = (List<Plan>) (Object) ValidationCheck.returnValidListObject(plans);
    if (validPlans.size() > 0) {
      return validPlans;
    }
    throw new NoValidDataException("No valid plans for the selected status");
  }

  public List<Plan> getPlansFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException, NoValidDataException {
    List<Plan> plans = basicDataGetter.getPlansFromMarketSignal(marketSignal);
    List<Plan> validPlans = (List<Plan>) (Object) ValidationCheck.returnValidListObject(plans);
    if (validPlans.size() > 0) {
      return validPlans;
    }
    throw new NoValidDataException("No valid plans for the selected market signal");
  }

  public static List<Equipment> getEquipmentFromSite(Site site) throws NoValidDataException {
    List<Equipment> equipment = GDRMDataGetterNoHib.getEquipmentFromSite(site);
    List<Equipment> validEquipment = (List<Equipment>) (Object) ValidationCheck.returnValidListObject(equipment);
    if (validEquipment.size() > 0) {
      return validEquipment;
    }
    throw new NoValidDataException("No valid equipment for the selected site");
  }

  public static List<Action> getActionsFromEquipment(Equipment equipment) throws NoValidDataException {
    List<Action> actions = GDRMDataGetterNoHib.getActionsFromEquipment(equipment);
    List<Action> validActions = (List<Action>) (Object) ValidationCheck.returnValidListObject(actions);
    if (validActions.size() > 0) {
      return validActions;
    }
    throw new NoValidDataException("No valid equipment for the selected site");
  }

}
