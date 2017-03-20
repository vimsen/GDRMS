package com.wattics.vimsen.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.DssSelectedProsumerId;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.PlanHasActionId;
import com.wattics.vimsen.entities.Prosumer;

public class VimsenTestUtil {

  public static MarketSignal setUpMarketSignal(String startTimeText, DateTime dateTimeStart, Double[] reduction,
      List<Integer> primaryProsumers, List<Integer> secondaryProsumers) {
    DsoTerritory dsoTerritory = new DsoTerritory(DatabaseSetUp.DEFAULT_DSOTERRITORY_ID);
    MarketSignal marketSignal = new MarketSignal(dsoTerritory);

    marketSignal.setStartTime(dateTimeStart);
    DateTime endTime = new DateTime(getEndDRTime(dateTimeStart, reduction.length));
    marketSignal.setEndTime(endTime);

    marketSignal.setStartTimeText(startTimeText);
    marketSignal.setTimeInterval(DatabaseSetUp.DEFAULT_DR_INTERVAL);
    marketSignal.setUnit(DatabaseSetUp.DEFAULT_DR_UNIT);
    String targetReduction = Arrays.toString(reduction);
    marketSignal.setAmountReduction(targetReduction);
    marketSignal.setId(1);

    Set<DssSelectedProsumer> dssProsumers = new HashSet<DssSelectedProsumer>();

    for (int prosumerId : primaryProsumers) {
      Prosumer prosumer = new Prosumer(prosumerId);
      DssSelectedProsumerId dssProsumerId = new DssSelectedProsumerId(prosumerId, marketSignal.getId());
      DssSelectedProsumer dssProsumer = new DssSelectedProsumer(dssProsumerId, marketSignal, prosumer, true, false, false, false,
          false, false);
      dssProsumers.add(dssProsumer);
    }
    for (int prosumerId : secondaryProsumers) {
      Prosumer prosumer = new Prosumer(prosumerId);
      DssSelectedProsumerId dssProsumerId = new DssSelectedProsumerId(prosumerId, marketSignal.getId());
      DssSelectedProsumer dssProsumer = new DssSelectedProsumer(dssProsumerId, marketSignal, prosumer, false);
      dssProsumers.add(dssProsumer);
    }

    marketSignal.setDssSelectedProsumers(dssProsumers);

    return marketSignal;
  }

  private static long getEndDRTime(DateTime startTime, int numberIntervals) {
    return startTime.getMillis() + 1000 * DatabaseSetUp.DEFAULT_DR_INTERVAL * numberIntervals;
  }

  public static Plan addActionsToCreatedPlan(Plan plan) throws MapperException {

    MarketSignal marketSignal = plan.getMarketSignal();
    String stringAmount = marketSignal.getAmountReduction();
    Double[] reduction = FormatConverter.stringToArrayDouble(stringAmount);

    Set<DssSelectedProsumer> dssProsumers = marketSignal.getDssSelectedProsumers();
    Set<PlanHasAction> planActions = new HashSet<PlanHasAction>();
    for (DssSelectedProsumer dssProsumer : dssProsumers) {
      if (dssProsumer.getIsPrimary()) {
        Equipment equipment = new Equipment();
        ControlSignal controlSignal = new ControlSignal(DatabaseSetUp.DEFAULT_CONTROLSIGNAL_ID);
        controlSignal.setRuleControllerId(DatabaseSetUp.DEFAULT_RULE_CONTROLLER_ID);
        int actionId = dssProsumer.getProsumer().getId();
        Action action = new Action(actionId, equipment, controlSignal);
        PlanHasActionId phaId = new PlanHasActionId(plan.getId(), actionId);
        PlanHasAction planAction = new PlanHasAction(phaId, plan, action);
        planAction.setUpdatedActualAt(marketSignal.getStartTime());
        planAction.setTStart(marketSignal.getStartTime());
        planAction.setPlannedAmount("[1.0,2.0]");
        planActions.add(planAction);
      }
    }

    plan.setPlanHasActions(planActions);

    return plan;
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> asList(T... items) {
    List<T> list = new ArrayList<T>();
    for (T item : items) {
      list.add(item);
    }
    return list;
  }

}
