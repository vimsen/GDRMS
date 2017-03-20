package com.wattics.vimsen.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.utils.FormatConverter;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;

public class ActionsAggregator {

  public static Map<Long, List<Equipment>> aggregateActionsInTimeSlots(List<PlanHasAction> planActions, int slotSizeSec,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException {
    Map<Long, List<Equipment>> equipmentActions = new HashMap<>();

    for (PlanHasAction planAction : planActions) {
      try {
        Equipment equipment = dataGetter.getEquipmentFromPlanHasAction(planAction);
        Long timeStart = planAction.getTStart().getMillis();
        Double[] plannedAmount = FormatConverter.stringToArrayDouble(planAction.getPlannedAmount());

        for (int timeSlotIndex = 0; timeSlotIndex < plannedAmount.length; timeSlotIndex++) {
          if (plannedAmount[timeSlotIndex] > 0.0) {
            Long startSlot = timeStart + (timeSlotIndex * slotSizeSec * TimeHelpers.MS_IN_A_SEC);
            equipmentActions = updateEquipmentActions(equipmentActions, startSlot, slotSizeSec, equipment);
          }
        }
      } catch (MapperException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NoValidDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return equipmentActions;
  }

  private static Map<Long, List<Equipment>> updateEquipmentActions(Map<Long, List<Equipment>> equipmentActions, Long slotStart,
      int slotSize, Equipment equipment) {
    if (equipmentActions.keySet().contains(slotStart)) {
      List<Equipment> equipments = equipmentActions.get(slotStart);
      equipments.add(equipment);
      equipmentActions.put(slotStart, equipments);
    } else {

      List<Equipment> equipmentList = new ArrayList<>();
      equipmentList.add(equipment);
      equipmentActions.put(slotStart, equipmentList);
    }
    return equipmentActions;
  }

  public static Set<Equipment> getAllEquipment(Map<Long, List<Equipment>> agggregatedActions) {
    Set<Equipment> equipmentSet = new HashSet<>();
    for (List<Equipment> equipmentList : agggregatedActions.values()) {

      for (Equipment equipment : equipmentList) {
        equipmentSet.add(equipment);
      }

    }
    return equipmentSet;
  }

  public static String createMessageOfEquipmentToSwitchOff(Map<Long, List<Equipment>> agggregatedActions, int slotSizeSec,
      String referenceTime) throws RecommendationException {
    String message = "";
    List<Long> timeStart = new ArrayList<Long>(agggregatedActions.keySet());
    Collections.sort(timeStart);
    for (Long time : timeStart) {
      List<Equipment> equipmentList = agggregatedActions.get(time);
      String startPeriod = TimeHelpers.getLocalTimeHourAndMinutes(referenceTime, new DateTime(time)); // TODO:
                                                                                                      // conversion
                                                                                                      // to
                                                                                                      // local
                                                                                                      // time
      String endPeriod = TimeHelpers.getLocalTimeHourAndMinutes(referenceTime,
          new DateTime(time + (slotSizeSec * TimeHelpers.MS_IN_A_SEC)));
      message = message + startPeriod + " to " + endPeriod + ": ";
      for (Equipment equipment : equipmentList) {
        message = message + equipment.getName() + " ";
      }
      message = message + " OFF\n";
    }

    return message;
  }

}
