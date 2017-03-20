package com.wattics.vimsen.LDRMdatamanager;

import org.testng.annotations.Test;

public class MqttRuleSenderTest {

//	@Test
//	public void mqttRuleSenderTest() throws LDRMRuleException {
//		MqttRuleSender ruleSender = new MqttRuleSender();
//
//		String header = "glimperop/b827eb4c14af/GDRM";
//		MqttRule mqttRule = new MqttRule();
//		mqttRule.dr = "OFF";
//		mqttRule.time = "14:30";
//		mqttRule.devices = new String[] { "fibaro1" };
//
//		String message = MqttControllerRuleMapper.serializeMqttRule(mqttRule);
//
//		ruleSender.sendNewConsumptionRule(header, message);
//	}

	@Test
	public void mqttReccomendationSenderTest() throws LDRMRuleException {
		MqttRuleSender ruleSender = new MqttRuleSender();

		String header = "messageM/1469180291/giulia.depoli@wattics.com/on/34a/event5";

		String message = "Test email message";

		ruleSender.sendNewConsumptionRule(header, message);
	}
}
