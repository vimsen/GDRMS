package com.wattics.vimsen.GDRMmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.wattics.vimen.DSSdata.DSSRequestsJsonMapper;
import com.wattics.vimen.DSSdata.PlanCurrentStatus;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;

public class DRGetRequestManager {

  public static String getPlanStatusJson(String planId, GDRMDataGetterAndValidationInterface dataGetter)
      throws MapperException, DataAccessLayerException, NoValidDataException {
    PlanCurrentStatus planStatusCheck = getPlanCurrentStatus(planId, dataGetter);
    String jsonPlanStatus = DSSRequestsJsonMapper.serializePlanCurrentStatus(planStatusCheck);
    return jsonPlanStatus;
  }

  public static PlanCurrentStatus getPlanCurrentStatus(String planId, GDRMDataGetterAndValidationInterface dataGetter)
      throws MapperException, DataAccessLayerException, NoValidDataException {
    int id = Integer.parseInt(planId);
    Plan plan = dataGetter.getPlan(id);
    MarketSignal marketSignal = plan.getMarketSignal();
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActionsSet = plan.getPlanHasActions();
    List<PlanHasAction> planActions = new ArrayList<PlanHasAction>(planActionsSet);
    HashMap<String, Double[]> planned_dr_amount = new HashMap<String, Double[]>();
    HashMap<String, Double[]> actual_dr_amount = new HashMap<String, Double[]>();
    for (PlanHasAction planAction : planActions) {
      String plannedAmountString = planAction.getPlannedAmount();
      Double[] plannedDR = FormatConverter.stringToArrayDouble(plannedAmountString);
      try {
        Prosumer prosumer = dataGetter.getProsumerFromActionId(planAction.getId().getActionId());

        planned_dr_amount = updateMapValue(planned_dr_amount, prosumer.getName(), plannedDR);

        if (planAction.getActualAmount() != null) {
          String actualAmountString = planAction.getActualAmount();
          Double[] actualDR = FormatConverter.stringToArrayDouble(actualAmountString);
          actual_dr_amount = updateMapValue(actual_dr_amount, prosumer.getName(), actualDR);
        }
      } catch (NoValidDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    PlanCurrentStatus planStatusCheck = new PlanCurrentStatus();
    planStatusCheck.interval = marketSignal.getTimeInterval();
    planStatusCheck.start_time = marketSignal.getStartTimeText();
    planStatusCheck.status = plan.getStatus();
    planStatusCheck.plan_id = id;
    planStatusCheck.unit = marketSignal.getUnit();
    planStatusCheck.planned_dr = planned_dr_amount;
    planStatusCheck.actual_dr = actual_dr_amount;
    planStatusCheck.type = marketSignal.getType();
    return planStatusCheck;
  }

  private static HashMap<String, Double[]> updateMapValue(HashMap<String, Double[]> originalMap, String id, Double[] newValue) {
    HashMap<String, Double[]> updatedMap = originalMap;

    if (originalMap.containsKey(id)) {
      Double[] value = originalMap.get(id);
      Double[] updatedValue = new Double[value.length];
      for (int i = 0; i < value.length; i++) {
        updatedValue[i] = value[i] + newValue[i];
      }
      updatedMap.put(id, updatedValue);
    } else {
      updatedMap.put(id, newValue);
    }

    return updatedMap;
  }

}
