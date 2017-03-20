package com.wattics.vimsen.recommendation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.ValidationMock;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.PlanHasAction;

public class ActionsAggregatorTest {

  private Action setActionWithEquipmentName(String equipmentName) {
    Action action = new Action();
    Equipment equipment = new Equipment();
    equipment.setName(equipmentName);
    action.setEquipment(equipment);
    return action;
  }

  @Test
  public void generateMessageToSwitchOffEquipmentTest() throws RecommendationException {
    int slotSizeSec = 15 * 60;
    Map<Long, List<Equipment>> aggregatedActions = new HashMap<>();
    Equipment equipment = new Equipment();
    equipment.setName("Light");
    List<Equipment> equipments = new ArrayList<>();
    equipments.add(equipment);
    Long time = new DateTime("2016-09-09T12:00:00+00:00").getMillis();
    Long time2 = new DateTime("2016-09-09T12:30:00+00:00").getMillis();
    aggregatedActions.put(time, equipments);
    aggregatedActions.put(time2, equipments);

    String message = ActionsAggregator.createMessageOfEquipmentToSwitchOff(aggregatedActions, slotSizeSec,
        "2016-09-09T12:00:00.000+01:00");

    System.out.println(message);
  }

  @Test
  public void aggregateActionsInTimeSlotTest() throws DataAccessLayerException {
    GDRMDataGetterAndValidationInterface dataGetter = new ValidationMock();
    String startTimeText = "2016-09-25T12:00:00.000+00:00";
    Instant startTime = new Instant(startTimeText);
    int slotSizeSec = 15 * 60;
    PlanHasAction planAction = new PlanHasAction();
    planAction.setTStart(new DateTime(startTimeText));
    planAction.setPlannedAmount("[2.4 , 3.1]");
    planAction.setAction(setActionWithEquipmentName("Equipment 1"));

    PlanHasAction planAction2 = new PlanHasAction();
    planAction2.setTStart(new DateTime(startTimeText));
    planAction2.setPlannedAmount("[2.7 , 35.1]");
    planAction2.setAction(setActionWithEquipmentName("Equipment 2"));

    List<PlanHasAction> planActions = new ArrayList<>();
    planActions.add(planAction);
    planActions.add(planAction2);

    Map<Long, List<Equipment>> aggregatedActions = ActionsAggregator.aggregateActionsInTimeSlots(planActions, slotSizeSec,
        dataGetter);

    List<Equipment> firstSlotEquipment = aggregatedActions.get(startTime.getMillis());

    Assert.assertEquals(firstSlotEquipment.get(0).getName(), "Equipment 1");
    Assert.assertEquals(firstSlotEquipment.get(1).getName(), "Equipment 2");
  }

  @Test
  public void aggregateActionsDoesNotIncludeActionsStartingInAnotherTimeSlot() throws DataAccessLayerException {
    GDRMDataGetterAndValidationInterface dataGetter = new ValidationMock();
    String startTimeText = "2016-09-25T12:00:00.000+00:00";
    Instant startTime = new Instant(startTimeText);
    int slotSizeSec = 15 * 60;
    PlanHasAction planAction = new PlanHasAction();
    planAction.setTStart(new DateTime(startTimeText));
    planAction.setPlannedAmount("[0.0 , 3.1]");
    planAction.setAction(setActionWithEquipmentName("Equipment 1"));

    PlanHasAction planAction2 = new PlanHasAction();
    planAction2.setTStart(new DateTime(startTimeText));
    planAction2.setPlannedAmount("[2.7 , 35.1]");
    planAction2.setAction(setActionWithEquipmentName("Equipment 2"));

    List<PlanHasAction> planActions = new ArrayList<>();
    planActions.add(planAction);
    planActions.add(planAction2);

    Map<Long, List<Equipment>> aggregatedActions = ActionsAggregator.aggregateActionsInTimeSlots(planActions, slotSizeSec,
        dataGetter);

    List<Equipment> firstSlotEquipment = aggregatedActions.get(startTime.getMillis());

    Assert.assertEquals(firstSlotEquipment.get(0).getName(), "Equipment 2");
  }

}
