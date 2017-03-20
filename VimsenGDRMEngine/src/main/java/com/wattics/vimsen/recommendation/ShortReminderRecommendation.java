package com.wattics.vimsen.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.utils.TimeHelpers;

public class ShortReminderRecommendation extends RecommendationManager {

  // private static long MS_BEFORE_SEND_SHORT_REMINDER = 1000 * 60 * 5;
  private static String END_TOPIC = "34a/event3";
  private static String staticMessage0 = "As previously notified, your equipment will be powered off shortly as follows:";

  @Override
  protected DssSelectedProsumer updateDssWithSentSentRecommendation(DssSelectedProsumer dssProsumer) {
    dssProsumer.setRecomReminderSent(true);
    return dssProsumer;
  }

  @Override
  protected boolean isTimeToSendRecommendation(MarketSignal marketSignal) {
    return true;
  }

  @Override
  protected boolean recommendationHasBeenSent(DssSelectedProsumer dssSelectedProsumer) {
    return dssSelectedProsumer.getRecomReminderSent();
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

    String introductionMessage = staticMessage0;
    String completeMessage = composeMessage(introductionMessage, perdiodReduction);
    return completeMessage;
  }

  private String composeMessage(String staticMessages, String perdiodReduction) {
    String composedMessage = staticMessages + "\n" + perdiodReduction;
    return composedMessage;
  }

}
