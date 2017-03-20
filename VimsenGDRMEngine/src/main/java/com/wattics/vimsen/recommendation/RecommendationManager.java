package com.wattics.vimsen.recommendation;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.joda.time.Instant;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.mqtt.sender.MQTTSender;

public abstract class RecommendationManager {

  public boolean hasToSendRecommendation(MarketSignal marketSignal, Prosumer prosumer, DssSelectedProsumer dssSelectedProsumer) {
    boolean recomHasNotBeenSentYet = !recommendationHasBeenSent(dssSelectedProsumer);
    return (recomHasNotBeenSentYet && isTimeToSendRecommendation(marketSignal));
  }

  public void storeRecommendationSuccess(DssSelectedProsumer dssProsumer, GDRMDataStorerInterface dataStorer)
      throws DataAccessLayerException {
    DssSelectedProsumer dssProsumerUpdated = updateDssWithSentSentRecommendation(dssProsumer);
    dataStorer.storeDssSelectedProsumers(dssProsumerUpdated); // TODO: has not
                                                              // been
                                                              // implemented yet
  }

  protected abstract DssSelectedProsumer updateDssWithSentSentRecommendation(DssSelectedProsumer dssProsumer);

  public void sendRecommendation(Pair<String, String> message) {
    MQTTSender mqttSender = new MQTTSender();
    try {
      mqttSender.initializeMQTTSenderConnection();
      mqttSender.checkConnectionAndPublishMessage(message.getLeft(), message.getRight());
      // TODO: return false if it was not possible to send the message
    } catch (MqttSecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MqttException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  protected abstract boolean isTimeToSendRecommendation(MarketSignal marketSignal);

  protected abstract boolean recommendationHasBeenSent(DssSelectedProsumer dssSelectedProsumer);

  public Pair<String, String> generateRecommendation(MarketSignal marketSignal, Prosumer prosumer,
      GDRMDataGetterAndValidationInterface dataGetter)
      throws RecommendationException, DataAccessLayerException, NoValidDataException {
    String initialTopic = generateInitialTopic(prosumer);
    String recIdTopic = getRecommendationEndTopic();
    String topic = initialTopic + recIdTopic;

    String message = getRecommendationMessage(marketSignal, prosumer, dataGetter);

    Pair<String, String> messageToSend = new ImmutablePair<String, String>(topic, message);
    return messageToSend;
  }

  private String generateInitialTopic(Prosumer prosumer) {
    String timestamp = "" + Instant.now().getMillis();
    String userMail = prosumer.getEmail();
    return "messageM/" + timestamp + "/" + userMail + "/on/";
  }

  protected abstract String getRecommendationEndTopic();

  protected abstract String getRecommendationMessage(MarketSignal marketSignal, Prosumer prosumer,
      GDRMDataGetterAndValidationInterface dataGetter)
      throws RecommendationException, DataAccessLayerException, NoValidDataException;

}
