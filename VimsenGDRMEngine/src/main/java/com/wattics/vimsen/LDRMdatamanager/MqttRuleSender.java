package com.wattics.vimsen.LDRMdatamanager;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.wattics.vimsen.mqtt.sender.MQTTSender;

public class MqttRuleSender implements ControllerRuleSenderInterface {

  private MQTTSender mqttSender;

  public MqttRuleSender() {
    initializeMqttSender();
  }

  private void initializeMqttSender() {
    this.mqttSender = new MQTTSender();
    try {
      this.mqttSender.initializeMQTTSenderConnection();
    } catch (MqttSecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MqttException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public String sendNewConsumptionRule(String header, String ruleJson) throws LDRMRuleException {
    mqttSender.checkConnectionAndPublishMessage(header, ruleJson);
    return ruleJson;
  }

  @Override
  public String sendNewConsumptionRule(String ruleJson) throws LDRMRuleException {
    mqttSender.checkConnectionAndPublishMessage("", ruleJson);
    return ruleJson;
  }

}
