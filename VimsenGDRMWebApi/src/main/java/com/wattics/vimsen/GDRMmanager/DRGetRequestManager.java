package com.wattics.vimsen.GDRMmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.wattics.vimen.DSSdata.DSSRequestsJsonMapper;
import com.wattics.vimen.DSSdata.PlanCurrentStatus;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;

public class DRGetRequestManager {

  public static String getPlanStatusJson(String planId, GDRMDataGetterInterface dataGetter)
      throws MapperException, DataAccessLayerException {
    PlanCurrentStatus planStatusCheck = getPlanCurrentStatus(planId, dataGetter);
    String jsonPlanStatus = DSSRequestsJsonMapper.serializePlanCurrentStatus(planStatusCheck);
    return jsonPlanStatus;
  }

  public static PlanCurrentStatus getPlanCurrentStatus(String planId, GDRMDataGetterInterface dataGetter)
      throws MapperException, DataAccessLayerException {
    int id = Integer.parseInt(planId);
    Plan plan = dataGetter.getPlan(id);
    MarketSignal marketSignal = plan.getMarketSignal();
    @SuppressWarnings("unchecked")
    Set<PlanHasAction> planActionsSet = plan.getPlanHasActions();
    List<PlanHasAction> planActions = new ArrayList<PlanHasAction>(planActionsSet);
    HashMap<Integer, Double[]> planned_dr_amount = new HashMap<Integer, Double[]>();
    HashMap<Integer, Double[]> actual_dr_amount = new HashMap<Integer, Double[]>();
    for (PlanHasAction planAction : planActions) {
      String plannedAmountString = planAction.getPlannedAmount();
      Double[] plannedDR = FormatConverter.stringToArrayDouble(plannedAmountString);
      Prosumer prosumer = dataGetter.getProsumerFromActionId(planAction.getId().getActionId());
      planned_dr_amount.put(prosumer.getId(), plannedDR);

      if (planAction.getActualAmount() != null) {
        String actualAmountString = planAction.getActualAmount();
        Double[] actualDR = FormatConverter.stringToArrayDouble(actualAmountString);
        actual_dr_amount.put(prosumer.getId(), actualDR);
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
    return planStatusCheck;
  }

}
