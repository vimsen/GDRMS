package com.wattics.vimsen.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.TimeHelpers;

public class LongReminderRecommendation extends RecommendationManager {

  // private static long MS_BEFORE_SEND_LONG_REMINDER = 1000 * 60 * 60 * 4;
  private static String END_TOPIC = "34a/event2";
  private static String staticMessage0 = "Just a reminder that you have been requested to deliver power surplus today between";
  private static String staticMessage1 = "using equipment";
  private static String staticMessage2 = ". Your equipment will be powered off during that period as follows:";

  @Override
  protected DssSelectedProsumer updateDssWithSentSentRecommendation(DssSelectedProsumer dssProsumer) {
    dssProsumer.setRecomLongReminderSent(true);
    return dssProsumer;
  }

  @Override
  protected boolean isTimeToSendRecommendation(MarketSignal marketSignal) {
    // Instant now = Instant.now();
    // Instant startMarketSignal = marketSignal.getStartTime().toInstant();
    //
    // long msBeforeStartMarketSignal = now.getMillis() -
    // (startMarketSignal.getMillis());
    //
    // return (msBeforeStartMarketSignal < MS_BEFORE_SEND_LONG_REMINDER &&
    // msBeforeStartMarketSignal > 0);
    return true;
  }

  @Override
  protected boolean recommendationHasBeenSent(DssSelectedProsumer dssSelectedProsumer) {
    return dssSelectedProsumer.getRecomLongReminderSent();
  }

  @Override
  protected String getRecommendationEndTopic() {
    return END_TOPIC;
  }

  @Override
  protected String getRecommendationMessage(MarketSignal marketSignal, Prosumer prosumer,
      GDRMDataGetterAndValidationInterface dataGetter)
      throws RecommendationException, DataAccessLayerException, NoValidDataException {
    int slotSizeSec = marketSignal.getTimeInterval();
    List<PlanHasAction> planActions = dataGetter.getActionsAssignedToAProsumerForAMarketSignal(marketSignal, prosumer);
    Map<Long, List<Equipment>> agggregatedActions = ActionsAggregator.aggregateActionsInTimeSlots(planActions, slotSizeSec,
        dataGetter);
    String perdiodReduction = ActionsAggregator.createMessageOfEquipmentToSwitchOff(agggregatedActions, slotSizeSec,
        marketSignal.getStartTimeText());
    String[] staticMessages = getStaticMessages();
    String introductionMessage = createIntroductionMessage(staticMessages, agggregatedActions, slotSizeSec,
        marketSignal.getStartTimeText());
    String completeMessage = composeMessage(introductionMessage, perdiodReduction);
    return completeMessage;
  }

  private String composeMessage(String staticMessages, String perdiodReduction) {
    String composedMessage = staticMessages + "\n" + perdiodReduction;
    return composedMessage;
  }

  private String createIntroductionMessage(String[] staticMessages, Map<Long, List<Equipment>> agggregatedActions,
      int slotSizeSec, String referenceTime) {
    List<Long> timeSlots = new ArrayList<>(agggregatedActions.keySet());
    Collections.sort(timeSlots);
    String startTime = TimeHelpers.getLocalTimeHourAndMinutes(referenceTime, new DateTime(timeSlots.get(0)));
    String endTime = TimeHelpers.getLocalTimeHourAndMinutes(referenceTime,
        new DateTime(timeSlots.get(timeSlots.size() - 1) + (TimeHelpers.MS_IN_A_SEC * slotSizeSec)));

    Set<Equipment> equipmentSet = ActionsAggregator.getAllEquipment(agggregatedActions);
    String equipmentString = "[ ";
    for (Equipment equipment : equipmentSet) {
      equipmentString = equipmentString + equipment.getName();
    }
    equipmentString = equipmentString + " ]";
    String message = staticMessages[0] + startTime + " and " + endTime + staticMessages[1] + equipmentString + staticMessages[2];
    return message;
  }

  private String[] getStaticMessages() {
    String[] messagesPieces = new String[3];
    messagesPieces[0] = staticMessage0;
    messagesPieces[1] = staticMessage1;
    messagesPieces[2] = staticMessage2;
    return messagesPieces;
  }

}
