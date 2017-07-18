package com.wattics.vimsen.mqtt.sender;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTSender {

  private static final String CLIENT_ID = "GDRM_Publisher";
  //private static final String BROKER_URL = "tcp://94.70.239.217:1883";
  private static String BROKER_URL;
  private static String BROKER_USER;
  private static char[] BROKER_PASS;

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private MqttClient mqttClient;

  private static final int MESSAGE_QOS = 0;
  private static final boolean MESSAGE_RETAINED = false;
  private static final boolean CLEAN_SESSION = true;
  public static final int KEEPALIVE_INTERVAL = 60;

  public MQTTSender() {
  }

  public void initializeMQTTSenderConnection() throws MqttException, MqttSecurityException {
    initializeMqttClient();
  }

  private void initializeMqttClient() throws MqttException {
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      File file = new File(classLoader.getResource("mqtt_config").getFile());

      // create application properties with default
      Properties mqttProps = new Properties();
      InputStream fis = new FileInputStream(file);
      mqttProps.load(fis);
      fis.close();
      BROKER_URL = mqttProps.getProperty("url");
      BROKER_USER = mqttProps.getProperty("user");
      BROKER_PASS = mqttProps.getProperty("password").toCharArray();

    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }

    logger.debug("Try to create new Mqtt client with borker url: '{}', client id: '{}'", BROKER_URL, CLIENT_ID);
    mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
  }

  private void initializeConnection(MqttClient mqttClient) throws MqttSecurityException, MqttException {

    try {
      //Get file from resources folder

      MqttConnectOptions options = new MqttConnectOptions();
      options.setCleanSession(CLEAN_SESSION);
      options.setKeepAliveInterval(KEEPALIVE_INTERVAL);
      options.setUserName(BROKER_USER);
      options.setPassword(BROKER_PASS);

      SSLContext sslContext = SSLContext.getInstance("SSL");
      TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());;
      trustManagerFactory.init(keyStore);

      sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

      mqttClient.connect(options);
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  public void checkConnectionAndPublishMessage(String topic, String value) {
    boolean isConnected = mqttClient.isConnected();
    if (!isConnected) {
      isConnected = tryToReconnect();
    }

    if (isConnected) {
      publishMessage(topic, value);
    } else {
      logger.error("Error trying to publish the message: " + value + ", to the topic: " + topic
              + ". It was not possible to reconnect the client");
    }
  }

  private void publishMessage(String topic, String value) {
    MqttMessage message = new MqttMessage(value.getBytes());
    message.setQos(MESSAGE_QOS);
    message.setRetained(MESSAGE_RETAINED);
    try {
      mqttClient.publish(topic, new MqttMessage(value.getBytes()));
    } catch (MqttException e) {
      logger.error("Error trying to publish the message: " + value + ", to the topic: " + topic + ".", e);
    }
  }

  private boolean tryToReconnect() {
    try {
      initializeConnection(mqttClient);
    } catch (MqttException e) {
      return false;
    }
    return true;
  }

  public void disconnect() throws MqttException {
    mqttClient.disconnect();
  }

  public boolean isConnected() {
    return mqttClient.isConnected();
  }

}
