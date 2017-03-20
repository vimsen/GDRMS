package com.wattics.vimsen.GDRMdatamanager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.general.PlanStatusEnum;

public class GDRMDataGetterNoHib {

  private final static DecimalFormat df = new DecimalFormat("00");

  public static List<Equipment> getEquipmentFromSite(Site site) {
    Set<Equipment> equipment = site.getEquipments();
    return new ArrayList<Equipment>(equipment);
  }

  public static List<Action> getActionsFromEquipment(Equipment equipment) {
    Set<Action> actions = equipment.getActions();
    return new ArrayList<Action>(actions);
  }

//  public static List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal) {
//    List<Prosumer> prosumers = new ArrayList<Prosumer>();
//    Set<DssSelectedProsumer> dssProsumersSet = marketSignal.getDssSelectedProsumers();
//    List<DssSelectedProsumer> dssProsumers = new ArrayList<DssSelectedProsumer>(dssProsumersSet);
//    for (DssSelectedProsumer dssProsumer : dssProsumers) {
//      if (dssProsumer.getIsPrimary()) {
//        prosumers.add(dssProsumer.getProsumer());
//      }
//    }
//    return prosumers;
//  }

  public static Equipment selectEquipmentByCategory(List<Equipment> equipmentList, String name) {
    Equipment equipmentSelected = null;
    for (Equipment equipment : equipmentList) {
      if (equipment.getCategory().equals(name)) {
        equipmentSelected = equipment;
      }
    }
    return equipmentSelected;
  }

  public static MarketSignal getMarketSignalFromPlanId(int planId, GDRMDataGetterInterface dataGetter)
      throws DataAccessLayerException {
    Plan plan = dataGetter.getPlan(planId);
    MarketSignal marketSignal = plan.getMarketSignal();

    return marketSignal;
  }

  public static List<Plan> getPlanInRegisteredStatus(GDRMDataGetterInterface dataGetter) throws DataAccessLayerException {
    List<Plan> plans = dataGetter.getPlansWithStatus(PlanStatusEnum.REGISTERED.toString());
    return plans;
  }

  public static List<Integer> getListPlansId(List<Plan> plans) {
    List<Integer> plansId = new ArrayList<Integer>();
    for (Plan plan : plans) {
      Integer planId = plan.getId();
      plansId.add(planId);
    }
    return plansId;
  }

  public static List<Plan> getPlanInCreatedAndOngoingStatus(GDRMDataGetterInterface dataGetter) throws DataAccessLayerException {
    List<Plan> plans = new ArrayList<>();
    List<Plan> plansCreated = dataGetter.getPlansWithStatus(PlanStatusEnum.CREATED.toString());
    plans.addAll(plansCreated);
    List<Plan> plansOngoing = dataGetter.getPlansWithStatus(PlanStatusEnum.ONGOING.toString());
    plans.addAll(plansOngoing);
    return plans;
  }

}
