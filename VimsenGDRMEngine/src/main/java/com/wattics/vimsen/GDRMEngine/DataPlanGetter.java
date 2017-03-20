package com.wattics.vimsen.GDRMEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.EDMSdatamanager.EDMSManager;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.general.DefaultSettings;

public class DataPlanGetter {

  public static Map<Long, Double> getActionForecastConsumptionFromEDMSMap(EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterAndValidationInterface dataGetter, MarketSignal marketSignal, Plan plan, PlanHasAction planHasAction,
      DateTime startTime, DateTime endTime) throws EDMSDataGetterException, DataAccessLayerException, NoValidDataException {
    Action action = planHasAction.getAction();
    Equipment equipment = action.getEquipment();
    Prosumer prosumer = dataGetter.getProsumerFromActionId(action.getId());
    Map<Long, Double> valuesDuringDRPeriod = EDMSManager.getKwhForecastConsumptionVGWMap(prosumer, equipment.getName(),
        startTime.toInstant(), endTime.toInstant(), marketSignal.getTimeInterval(), edmsDataGetter);
    return valuesDuringDRPeriod;
  }

  public static Map<Long, Double> getActionActualConsumptionFromEDMSMap(EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterAndValidationInterface dataGetter, MarketSignal marketSignal, Plan plan, PlanHasAction planHasAction,
      DateTime startTime, DateTime endTime) throws DataAccessLayerException, EDMSDataGetterException, NoValidDataException {
    Action action = planHasAction.getAction();
    Equipment equipment = action.getEquipment();
    Prosumer prosumer = dataGetter.getProsumerFromActionId(action.getId());
    Map<Long, Double> valuesDuringDRPeriod = EDMSManager.getKwh15mnConsumptionVGWMap(prosumer, equipment.getName(),
        startTime.toInstant(), endTime.toInstant(), marketSignal.getTimeInterval(), edmsDataGetter);

    return valuesDuringDRPeriod;
  }

  public static List<Map<Long, Double>> getProsumersForecastConsumptionFromEDMSMap(List<Prosumer> prosumers, DateTime startTime,
      DateTime endTime, int intervalDurationSec, EDMSDataGetterInterface edmsDataGetter) throws EDMSDataGetterException {
    List<Map<Long, Double>> forecastAmounts = new ArrayList<Map<Long, Double>>();
    for (Prosumer prosumer : prosumers) {
      String parameterType = DefaultSettings.EQUIPMENT_NAME;
      Map<Long, Double> valuesDuringDRPeriod = EDMSManager.getKwhForecastConsumptionVGWMap(prosumer, parameterType,
          startTime.toInstant(), endTime.toInstant(), intervalDurationSec, edmsDataGetter);
      forecastAmounts.add(valuesDuringDRPeriod);
    }
    return forecastAmounts;
  }

  public static Action selectProsumerAction(List<Action> actions) {
    return actions.get(DefaultSettings.PROSUMER_ACTION_IDX);
  }

  public static Double[] getOrderedValuesFromMap(Map<Long, Double> map) {
    List<Double> list = new ArrayList<Double>();
    SortedSet<Long> timeKeys = new TreeSet<Long>(map.keySet());
    for (Long timeKey : timeKeys) {
      list.add(map.get(timeKey));
    }

    Double[] array = list.toArray(new Double[list.size()]);
    return array;
  }

}
