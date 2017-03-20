package com.wattics.vimsen.recommendation;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Prosumer;

public class RecommendationManagerTest {
  @Test
  public void sendRecommendation() {
    String topic = "messageM/2016-11-18T13:03:56.186Z/giulia.depoli@wattics.com/on/34a/event1";
    String messageText = " You have to reduce your consumption betwen 14:0 and 15:30";
    Pair<String, String> message = new ImmutablePair<String, String>(topic, messageText);

    RecommendationManager manager = new RecommendationManager() {

      @Override
      protected DssSelectedProsumer updateDssWithSentSentRecommendation(DssSelectedProsumer dssProsumer) {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      protected boolean recommendationHasBeenSent(DssSelectedProsumer dssSelectedProsumer) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      protected boolean isTimeToSendRecommendation(MarketSignal marketSignal) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      protected String getRecommendationMessage(MarketSignal marketSignal, Prosumer prosumer,
          GDRMDataGetterAndValidationInterface dataGetter)
          throws RecommendationException, DataAccessLayerException, NoValidDataException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      protected String getRecommendationEndTopic() {
        // TODO Auto-generated method stub
        return null;
      }
    };

    manager.sendRecommendation(message);
  }
}
