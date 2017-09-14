-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: gdrms_engine_tests
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DSO_territory`
--

DROP TABLE IF EXISTS `DSO_territory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DSO_territory` (
  `id` int(11) NOT NULL,
  `DSO_name` varchar(255) DEFAULT NULL,
  `DSO_territory` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DSO_territory`
--

LOCK TABLES `DSO_territory` WRITE;
/*!40000 ALTER TABLE `DSO_territory` DISABLE KEYS */;
INSERT INTO `DSO_territory` VALUES (1,NULL,NULL);
/*!40000 ALTER TABLE `DSO_territory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action` (
  `id` int(11) NOT NULL,
  `control_signal_id` int(11) NOT NULL,
  `equipment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l70t7vv0p41m78vcye7517d31` (`control_signal_id`),
  KEY `FK_jmh69nbmklq83bpey4yvu2dus` (`equipment_id`),
  CONSTRAINT `FK_jmh69nbmklq83bpey4yvu2dus` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`),
  CONSTRAINT `FK_l70t7vv0p41m78vcye7517d31` FOREIGN KEY (`control_signal_id`) REFERENCES `control_signal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action`
--

LOCK TABLES `action` WRITE;
/*!40000 ALTER TABLE `action` DISABLE KEYS */;
INSERT INTO `action` VALUES (1,1,1);
/*!40000 ALTER TABLE `action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_SLA`
--

DROP TABLE IF EXISTS `action_SLA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_SLA` (
  `action_id` int(11) NOT NULL,
  `Mandatory` bit(1) DEFAULT NULL COMMENT 'Mandatory vs voluntary',
  `Day_ahead_notice` bit(1) DEFAULT NULL,
  `Intra_day_notice` bit(1) DEFAULT NULL,
  `Time_intra_day` double DEFAULT NULL,
  `Runtime_notice` bit(1) DEFAULT NULL,
  `Automated` bit(1) DEFAULT NULL,
  `Manual` bit(1) DEFAULT NULL COMMENT 'Manual and semi automated',
  `Deadline` datetime DEFAULT NULL COMMENT 'Time of the day',
  `Consumption_target` double NOT NULL,
  `Start_response_period` datetime NOT NULL,
  `End_response_period` datetime NOT NULL,
  `Maximum_duration` double DEFAULT NULL,
  `Sla_capacity` double DEFAULT NULL COMMENT 'Maximum reduction during one day',
  `max_number_call` int(11) DEFAULT NULL,
  `max_number_call_daily` int(11) DEFAULT NULL,
  `action_registration` bit(1) DEFAULT NULL,
  PRIMARY KEY (`action_id`),
  CONSTRAINT `FK_li0m16lehp9mfdyjq2414fgnh` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_SLA`
--

LOCK TABLES `action_SLA` WRITE;
/*!40000 ALTER TABLE `action_SLA` DISABLE KEYS */;
INSERT INTO `action_SLA` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2016-05-10 01:00:00','2016-05-10 23:00:00',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `action_SLA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_attribute`
--

DROP TABLE IF EXISTS `action_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_attribute` (
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_attribute`
--

LOCK TABLES `action_attribute` WRITE;
/*!40000 ALTER TABLE `action_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `action_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_has_action_attribute`
--

DROP TABLE IF EXISTS `action_has_action_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_has_action_attribute` (
  `action_id` int(11) NOT NULL,
  `action_attribute_name` varchar(255) NOT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`action_id`,`action_attribute_name`),
  KEY `FK_8800ibnnjw07xkn5dn435cdiu` (`action_attribute_name`),
  CONSTRAINT `FK_8800ibnnjw07xkn5dn435cdiu` FOREIGN KEY (`action_attribute_name`) REFERENCES `action_attribute` (`name`),
  CONSTRAINT `FK_jvs6muqqn9l03dhb9r1s59on2` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_has_action_attribute`
--

LOCK TABLES `action_has_action_attribute` WRITE;
/*!40000 ALTER TABLE `action_has_action_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `action_has_action_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_metric`
--

DROP TABLE IF EXISTS `action_metric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_metric` (
  `action_id` int(11) NOT NULL,
  `action_reliability` double DEFAULT NULL,
  `action_balance` double DEFAULT NULL,
  `number_request` int(11) DEFAULT NULL,
  PRIMARY KEY (`action_id`),
  CONSTRAINT `FK_cn4xn8n0d8y2bc7b8114ktqm3` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_metric`
--

LOCK TABLES `action_metric` WRITE;
/*!40000 ALTER TABLE `action_metric` DISABLE KEYS */;
/*!40000 ALTER TABLE `action_metric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` int(11) NOT NULL,
  `country_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `county` varchar(255) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dqdsiek23hleiulhpmnf98hwj` (`country_id`),
  CONSTRAINT `FK_dqdsiek23hleiulhpmnf98hwj` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `control_signal`
--

DROP TABLE IF EXISTS `control_signal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `control_signal` (
  `id` int(11) NOT NULL,
  `class_signal` varchar(255) DEFAULT NULL COMMENT '3 values: CHARGE DISPATCH CONTROL',
  `type` varchar(255) DEFAULT NULL COMMENT 'list of values, different for different names',
  `unit` varchar(255) DEFAULT NULL COMMENT 'Values available: Energy None power',
  `range_signal` longtext,
  `description` longtext,
  `rule_controller_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `control_signal`
--

LOCK TABLES `control_signal` WRITE;
/*!40000 ALTER TABLE `control_signal` DISABLE KEYS */;
INSERT INTO `control_signal` VALUES (1,NULL,'OFF',NULL,NULL,NULL,'openhab');
/*!40000 ALTER TABLE `control_signal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_available_reduction`
--

DROP TABLE IF EXISTS `daily_available_reduction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_available_reduction` (
  `date_red` datetime NOT NULL,
  `start_time` datetime NOT NULL,
  `action_id` int(11) NOT NULL,
  `reduction_capacity` longtext,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`date_red`,`start_time`),
  KEY `FK_fccumvsbimmwtdjw2o7cjrs9p` (`action_id`),
  CONSTRAINT `FK_fccumvsbimmwtdjw2o7cjrs9p` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_available_reduction`
--

LOCK TABLES `daily_available_reduction` WRITE;
/*!40000 ALTER TABLE `daily_available_reduction` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_available_reduction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dss_selected_prosumer`
--

DROP TABLE IF EXISTS `dss_selected_prosumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dss_selected_prosumer` (
  `prosumer_id` int(11) NOT NULL,
  `market_signal_id` int(11) NOT NULL,
  `is_primary` bit(1) NOT NULL,
  `recom_registered_sent` bit(1) NOT NULL,
  `recom_reminder_sent` bit(1) NOT NULL,
  `recom_summary_sent` bit(1) NOT NULL,
  `recom_long_reminder_sent` bit(1) DEFAULT NULL,
  `is_selected` bit(1) DEFAULT NULL,
  PRIMARY KEY (`prosumer_id`,`market_signal_id`),
  KEY `FK_2s5yauk1w6aws95kor619qr8r` (`market_signal_id`),
  CONSTRAINT `FK_2s5yauk1w6aws95kor619qr8r` FOREIGN KEY (`market_signal_id`) REFERENCES `market_signal` (`id`),
  CONSTRAINT `FK_isr1sl2ynuhc1nsn3e4v1yu1m` FOREIGN KEY (`prosumer_id`) REFERENCES `prosumer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dss_selected_prosumer`
--

LOCK TABLES `dss_selected_prosumer` WRITE;
/*!40000 ALTER TABLE `dss_selected_prosumer` DISABLE KEYS */;
/*!40000 ALTER TABLE `dss_selected_prosumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `electricity_raw`
--

DROP TABLE IF EXISTS `electricity_raw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `electricity_raw` (
  `equipment_id` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `active_power_A` double DEFAULT NULL,
  `reactive_power_A` double DEFAULT NULL,
  `active_power_B` double DEFAULT NULL,
  `reactive_power_B` double DEFAULT NULL,
  `active_power_C` double DEFAULT NULL,
  `reactive_power_C` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`),
  CONSTRAINT `FK_ivpuvn0ooglssaap0xdkopu10` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `electricity_raw`
--

LOCK TABLES `electricity_raw` WRITE;
/*!40000 ALTER TABLE `electricity_raw` DISABLE KEYS */;
/*!40000 ALTER TABLE `electricity_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment` (
  `id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `class` varchar(255) DEFAULT NULL COMMENT 'Equipment class can be: Self generation Storage Reschedule Curtailment Reduction None',
  `category` longtext NOT NULL COMMENT 'Example: Lighting Air conditioning Air handling unit Refrigeration Laundry machine Kitchen appliance Production unit Storage unit',
  `brand` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2spkb78yewkvrlohupnuahaoh` (`site_id`),
  CONSTRAINT `FK_2spkb78yewkvrlohupnuahaoh` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,1,'fibaro10',NULL,'consumption',NULL,NULL),(2,1,'fibaro11',NULL,'consumption',NULL,NULL);
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment_condition_profile`
--

DROP TABLE IF EXISTS `equipment_condition_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment_condition_profile` (
  `equipment_id` int(11) NOT NULL,
  `weekday` tinyint(4) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `t_00_00` double DEFAULT NULL,
  `t_00_15` double DEFAULT NULL,
  `t_00_30` double DEFAULT NULL,
  `t_00_45` double DEFAULT NULL,
  `t_01_00` double DEFAULT NULL,
  `t_01_15` double DEFAULT NULL,
  `t_01_30` double DEFAULT NULL,
  `t_01_45` double DEFAULT NULL,
  `t_02_00` double DEFAULT NULL,
  `t_02_15` double DEFAULT NULL,
  `t_02_30` double DEFAULT NULL,
  `t_02_45` double DEFAULT NULL,
  `t_03_00` double DEFAULT NULL,
  `t_03_15` double DEFAULT NULL,
  `t_03_30` double DEFAULT NULL,
  `t_03_45` double DEFAULT NULL,
  `t_04_00` double DEFAULT NULL,
  `t_04_15` double DEFAULT NULL,
  `t_04_30` double DEFAULT NULL,
  `t_04_45` double DEFAULT NULL,
  `t_05_00` double DEFAULT NULL,
  `t_05_15` double DEFAULT NULL,
  `t_05_30` double DEFAULT NULL,
  `t_05_45` double DEFAULT NULL,
  `t_06_00` double DEFAULT NULL,
  `t_06_15` double DEFAULT NULL,
  `t_06_30` double DEFAULT NULL,
  `t_06_45` double DEFAULT NULL,
  `t_07_00` double DEFAULT NULL,
  `t_07_15` double DEFAULT NULL,
  `t_07_30` double DEFAULT NULL,
  `t_07_45` double DEFAULT NULL,
  `t_08_00` double DEFAULT NULL,
  `t_08_15` double DEFAULT NULL,
  `t_08_30` double DEFAULT NULL,
  `t_08_45` double DEFAULT NULL,
  `t_09_00` double DEFAULT NULL,
  `t_09_15` double DEFAULT NULL,
  `t_09_30` double DEFAULT NULL,
  `t_09_45` double DEFAULT NULL,
  `t_10_00` double DEFAULT NULL,
  `t_10_15` double DEFAULT NULL,
  `t_10_30` double DEFAULT NULL,
  `t_10_45` double DEFAULT NULL,
  `t_11_00` double DEFAULT NULL,
  `t_11_15` double DEFAULT NULL,
  `t_11_30` double DEFAULT NULL,
  `t_11_45` double DEFAULT NULL,
  `t_12_00` double DEFAULT NULL,
  `t_12_15` double DEFAULT NULL,
  `t_12_30` double DEFAULT NULL,
  `t_12_45` double DEFAULT NULL,
  `t_13_00` double DEFAULT NULL,
  `t_13_15` double DEFAULT NULL,
  `t_13_30` double DEFAULT NULL,
  `t_13_45` double DEFAULT NULL,
  `t_14_00` double DEFAULT NULL,
  `t_14_15` double DEFAULT NULL,
  `t_14_30` double DEFAULT NULL,
  `t_14_45` double DEFAULT NULL,
  `t_15_00` double DEFAULT NULL,
  `t_15_15` double DEFAULT NULL,
  `t_15_30` double DEFAULT NULL,
  `t_15_45` double DEFAULT NULL,
  `t_16_00` double DEFAULT NULL,
  `t_16_15` double DEFAULT NULL,
  `t_16_30` double DEFAULT NULL,
  `t_16_45` double DEFAULT NULL,
  `t_17_00` double DEFAULT NULL,
  `t_17_15` double DEFAULT NULL,
  `t_17_30` double DEFAULT NULL,
  `t_17_45` double DEFAULT NULL,
  `t_18_00` double DEFAULT NULL,
  `t_18_15` double DEFAULT NULL,
  `t_18_30` double DEFAULT NULL,
  `t_18_45` double DEFAULT NULL,
  `t_19_00` double DEFAULT NULL,
  `t_19_15` double DEFAULT NULL,
  `t_19_30` double DEFAULT NULL,
  `t_19_45` double DEFAULT NULL,
  `t_20_00` double DEFAULT NULL,
  `t_20_15` double DEFAULT NULL,
  `t_20_30` double DEFAULT NULL,
  `t_20_45` double DEFAULT NULL,
  `t_21_00` double DEFAULT NULL,
  `t_21_15` double DEFAULT NULL,
  `t_21_30` double DEFAULT NULL,
  `t_21_45` double DEFAULT NULL,
  `t_22_00` double DEFAULT NULL,
  `t_22_15` double DEFAULT NULL,
  `t_22_30` double DEFAULT NULL,
  `t_22_45` double DEFAULT NULL,
  `t_23_00` double DEFAULT NULL,
  `t_23_15` double DEFAULT NULL,
  `t_23_30` double DEFAULT NULL,
  `t_23_45` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`),
  CONSTRAINT `FK_eujqm7yjgfmywtpiaw0ec7vwh` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment_condition_profile`
--

LOCK TABLES `equipment_condition_profile` WRITE;
/*!40000 ALTER TABLE `equipment_condition_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipment_condition_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment_condition_raw`
--

DROP TABLE IF EXISTS `equipment_condition_raw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment_condition_raw` (
  `id` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `equipment_id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fetpu8e2pk4nutaoantiv16q5` (`equipment_id`),
  CONSTRAINT `FK_fetpu8e2pk4nutaoantiv16q5` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment_condition_raw`
--

LOCK TABLES `equipment_condition_raw` WRITE;
/*!40000 ALTER TABLE `equipment_condition_raw` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipment_condition_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generator_footprint`
--

DROP TABLE IF EXISTS `generator_footprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generator_footprint` (
  `equipment_id` int(11) NOT NULL,
  `nominal_operating_voltage_dc` int(11) DEFAULT NULL,
  `operating_voltage_min_dc` int(11) DEFAULT NULL,
  `operating_voltage_max_dc` int(11) DEFAULT NULL,
  `max_input_current_dc` float DEFAULT NULL,
  `nominal_operating_voltage_ac` int(11) DEFAULT NULL,
  `nominal_operating_frequency_ac` int(11) DEFAULT NULL,
  `nominal_output_power_ac` int(11) DEFAULT NULL,
  `max_output_power_ac` int(11) DEFAULT NULL,
  `max_output_current_ac` int(11) DEFAULT NULL,
  PRIMARY KEY (`equipment_id`),
  CONSTRAINT `FK_7fkep0br3lbbebcj7dvfu2uc` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generator_footprint`
--

LOCK TABLES `generator_footprint` WRITE;
/*!40000 ALTER TABLE `generator_footprint` DISABLE KEYS */;
/*!40000 ALTER TABLE `generator_footprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kWh_15mn`
--

DROP TABLE IF EXISTS `kWh_15mn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kWh_15mn` (
  `equipment_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `t_00_00` double DEFAULT NULL,
  `t_00_15` double DEFAULT NULL,
  `t_00_30` double DEFAULT NULL,
  `t_00_45` double DEFAULT NULL,
  `t_01_00` double DEFAULT NULL,
  `t_01_15` double DEFAULT NULL,
  `t_01_30` double DEFAULT NULL,
  `t_01_45` double DEFAULT NULL,
  `t_02_00` double DEFAULT NULL,
  `t_02_15` double DEFAULT NULL,
  `t_02_30` double DEFAULT NULL,
  `t_02_45` double DEFAULT NULL,
  `t_03_00` double DEFAULT NULL,
  `t_03_15` double DEFAULT NULL,
  `t_03_30` double DEFAULT NULL,
  `t_03_45` double DEFAULT NULL,
  `t_04_00` double DEFAULT NULL,
  `t_04_15` double DEFAULT NULL,
  `t_04_30` double DEFAULT NULL,
  `t_04_45` double DEFAULT NULL,
  `t_05_00` double DEFAULT NULL,
  `t_05_15` double DEFAULT NULL,
  `t_05_30` double DEFAULT NULL,
  `t_05_45` double DEFAULT NULL,
  `t_06_00` double DEFAULT NULL,
  `t_06_15` double DEFAULT NULL,
  `t_06_30` double DEFAULT NULL,
  `t_06_45` double DEFAULT NULL,
  `t_07_00` double DEFAULT NULL,
  `t_07_15` double DEFAULT NULL,
  `t_07_30` double DEFAULT NULL,
  `t_07_45` double DEFAULT NULL,
  `t_08_00` double DEFAULT NULL,
  `t_08_15` double DEFAULT NULL,
  `t_08_30` double DEFAULT NULL,
  `t_08_45` double DEFAULT NULL,
  `t_09_00` double DEFAULT NULL,
  `t_09_15` double DEFAULT NULL,
  `t_09_30` double DEFAULT NULL,
  `t_09_45` double DEFAULT NULL,
  `t_10_00` double DEFAULT NULL,
  `t_10_15` double DEFAULT NULL,
  `t_10_30` double DEFAULT NULL,
  `t_10_45` double DEFAULT NULL,
  `t_11_00` double DEFAULT NULL,
  `t_11_15` double DEFAULT NULL,
  `t_11_30` double DEFAULT NULL,
  `t_11_45` double DEFAULT NULL,
  `t_12_00` double DEFAULT NULL,
  `t_12_15` double DEFAULT NULL,
  `t_12_30` double DEFAULT NULL,
  `t_12_45` double DEFAULT NULL,
  `t_13_00` double DEFAULT NULL,
  `t_13_15` double DEFAULT NULL,
  `t_13_30` double DEFAULT NULL,
  `t_13_45` double DEFAULT NULL,
  `t_14_00` double DEFAULT NULL,
  `t_14_15` double DEFAULT NULL,
  `t_14_30` double DEFAULT NULL,
  `t_14_45` double DEFAULT NULL,
  `t_15_00` double DEFAULT NULL,
  `t_15_15` double DEFAULT NULL,
  `t_15_30` double DEFAULT NULL,
  `t_15_45` double DEFAULT NULL,
  `t_16_00` double DEFAULT NULL,
  `t_16_15` double DEFAULT NULL,
  `t_16_30` double DEFAULT NULL,
  `t_16_45` double DEFAULT NULL,
  `t_17_00` double DEFAULT NULL,
  `t_17_15` double DEFAULT NULL,
  `t_17_30` double DEFAULT NULL,
  `t_17_45` double DEFAULT NULL,
  `t_18_00` double DEFAULT NULL,
  `t_18_15` double DEFAULT NULL,
  `t_18_30` double DEFAULT NULL,
  `t_18_45` double DEFAULT NULL,
  `t_19_00` double DEFAULT NULL,
  `t_19_15` double DEFAULT NULL,
  `t_19_30` double DEFAULT NULL,
  `t_19_45` double DEFAULT NULL,
  `t_20_00` double DEFAULT NULL,
  `t_20_15` double DEFAULT NULL,
  `t_20_30` double DEFAULT NULL,
  `t_20_45` double DEFAULT NULL,
  `t_21_00` double DEFAULT NULL,
  `t_21_15` double DEFAULT NULL,
  `t_21_30` double DEFAULT NULL,
  `t_21_45` double DEFAULT NULL,
  `t_22_00` double DEFAULT NULL,
  `t_22_15` double DEFAULT NULL,
  `t_22_30` double DEFAULT NULL,
  `t_22_45` double DEFAULT NULL,
  `t_23_00` double DEFAULT NULL,
  `t_23_15` double DEFAULT NULL,
  `t_23_30` double DEFAULT NULL,
  `t_23_45` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`,`date`),
  CONSTRAINT `FK_esq520t47gubbcl5ctevwgr9b` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kWh_15mn`
--

LOCK TABLES `kWh_15mn` WRITE;
/*!40000 ALTER TABLE `kWh_15mn` DISABLE KEYS */;
/*!40000 ALTER TABLE `kWh_15mn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kWh_5mn`
--

DROP TABLE IF EXISTS `kWh_5mn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kWh_5mn` (
  `equipment_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `t_00_00` double DEFAULT NULL,
  `t_00_05` double DEFAULT NULL,
  `t_00_10` double DEFAULT NULL,
  `t_00_15` double DEFAULT NULL,
  `t_00_20` double DEFAULT NULL,
  `t_00_25` double DEFAULT NULL,
  `t_00_30` double DEFAULT NULL,
  `t_00_35` double DEFAULT NULL,
  `t_00_40` double DEFAULT NULL,
  `t_00_45` double DEFAULT NULL,
  `t_00_50` double DEFAULT NULL,
  `t_00_55` double DEFAULT NULL,
  `t_01_00` double DEFAULT NULL,
  `t_01_05` double DEFAULT NULL,
  `t_01_10` double DEFAULT NULL,
  `t_01_15` double DEFAULT NULL,
  `t_01_20` double DEFAULT NULL,
  `t_01_25` double DEFAULT NULL,
  `t_01_30` double DEFAULT NULL,
  `t_01_35` double DEFAULT NULL,
  `t_01_40` double DEFAULT NULL,
  `t_01_45` double DEFAULT NULL,
  `t_01_50` double DEFAULT NULL,
  `t_01_55` double DEFAULT NULL,
  `t_02_00` double DEFAULT NULL,
  `t_02_05` double DEFAULT NULL,
  `t_02_10` double DEFAULT NULL,
  `t_02_15` double DEFAULT NULL,
  `t_02_20` double DEFAULT NULL,
  `t_02_25` double DEFAULT NULL,
  `t_02_30` double DEFAULT NULL,
  `t_02_35` double DEFAULT NULL,
  `t_02_40` double DEFAULT NULL,
  `t_02_45` double DEFAULT NULL,
  `t_02_50` double DEFAULT NULL,
  `t_02_55` double DEFAULT NULL,
  `t_03_00` double DEFAULT NULL,
  `t_03_05` double DEFAULT NULL,
  `t_03_10` double DEFAULT NULL,
  `t_03_15` double DEFAULT NULL,
  `t_03_20` double DEFAULT NULL,
  `t_03_25` double DEFAULT NULL,
  `t_03_30` double DEFAULT NULL,
  `t_03_35` double DEFAULT NULL,
  `t_03_40` double DEFAULT NULL,
  `t_03_45` double DEFAULT NULL,
  `t_03_50` double DEFAULT NULL,
  `t_03_55` double DEFAULT NULL,
  `t_04_00` double DEFAULT NULL,
  `t_04_05` double DEFAULT NULL,
  `t_04_10` double DEFAULT NULL,
  `t_04_15` double DEFAULT NULL,
  `t_04_20` double DEFAULT NULL,
  `t_04_25` double DEFAULT NULL,
  `t_04_30` double DEFAULT NULL,
  `t_04_35` double DEFAULT NULL,
  `t_04_40` double DEFAULT NULL,
  `t_04_45` double DEFAULT NULL,
  `t_04_50` double DEFAULT NULL,
  `t_04_55` double DEFAULT NULL,
  `t_05_00` double DEFAULT NULL,
  `t_05_05` double DEFAULT NULL,
  `t_05_10` double DEFAULT NULL,
  `t_05_15` double DEFAULT NULL,
  `t_05_20` double DEFAULT NULL,
  `t_05_25` double DEFAULT NULL,
  `t_05_30` double DEFAULT NULL,
  `t_05_35` double DEFAULT NULL,
  `t_05_40` double DEFAULT NULL,
  `t_05_45` double DEFAULT NULL,
  `t_05_50` double DEFAULT NULL,
  `t_05_55` double DEFAULT NULL,
  `t_06_00` double DEFAULT NULL,
  `t_06_05` double DEFAULT NULL,
  `t_06_10` double DEFAULT NULL,
  `t_06_15` double DEFAULT NULL,
  `t_06_20` double DEFAULT NULL,
  `t_06_25` double DEFAULT NULL,
  `t_06_30` double DEFAULT NULL,
  `t_06_35` double DEFAULT NULL,
  `t_06_40` double DEFAULT NULL,
  `t_06_45` double DEFAULT NULL,
  `t_06_50` double DEFAULT NULL,
  `t_06_55` double DEFAULT NULL,
  `t_07_00` double DEFAULT NULL,
  `t_07_05` double DEFAULT NULL,
  `t_07_10` double DEFAULT NULL,
  `t_07_15` double DEFAULT NULL,
  `t_07_20` double DEFAULT NULL,
  `t_07_25` double DEFAULT NULL,
  `t_07_30` double DEFAULT NULL,
  `t_07_35` double DEFAULT NULL,
  `t_07_40` double DEFAULT NULL,
  `t_07_45` double DEFAULT NULL,
  `t_07_50` double DEFAULT NULL,
  `t_07_55` double DEFAULT NULL,
  `t_08_00` double DEFAULT NULL,
  `t_08_05` double DEFAULT NULL,
  `t_08_10` double DEFAULT NULL,
  `t_08_15` double DEFAULT NULL,
  `t_08_20` double DEFAULT NULL,
  `t_08_25` double DEFAULT NULL,
  `t_08_30` double DEFAULT NULL,
  `t_08_35` double DEFAULT NULL,
  `t_08_40` double DEFAULT NULL,
  `t_08_45` double DEFAULT NULL,
  `t_08_50` double DEFAULT NULL,
  `t_08_55` double DEFAULT NULL,
  `t_09_00` double DEFAULT NULL,
  `t_09_05` double DEFAULT NULL,
  `t_09_10` double DEFAULT NULL,
  `t_09_15` double DEFAULT NULL,
  `t_09_20` double DEFAULT NULL,
  `t_09_25` double DEFAULT NULL,
  `t_09_30` double DEFAULT NULL,
  `t_09_35` double DEFAULT NULL,
  `t_09_40` double DEFAULT NULL,
  `t_09_45` double DEFAULT NULL,
  `t_09_50` double DEFAULT NULL,
  `t_09_55` double DEFAULT NULL,
  `t_10_00` double DEFAULT NULL,
  `t_10_05` double DEFAULT NULL,
  `t_10_10` double DEFAULT NULL,
  `t_10_15` double DEFAULT NULL,
  `t_10_20` double DEFAULT NULL,
  `t_10_25` double DEFAULT NULL,
  `t_10_30` double DEFAULT NULL,
  `t_10_35` double DEFAULT NULL,
  `t_10_40` double DEFAULT NULL,
  `t_10_45` double DEFAULT NULL,
  `t_10_50` double DEFAULT NULL,
  `t_10_55` double DEFAULT NULL,
  `t_11_00` double DEFAULT NULL,
  `t_11_05` double DEFAULT NULL,
  `t_11_10` double DEFAULT NULL,
  `t_11_15` double DEFAULT NULL,
  `t_11_20` double DEFAULT NULL,
  `t_11_25` double DEFAULT NULL,
  `t_11_30` double DEFAULT NULL,
  `t_11_35` double DEFAULT NULL,
  `t_11_40` double DEFAULT NULL,
  `t_11_45` double DEFAULT NULL,
  `t_11_50` double DEFAULT NULL,
  `t_11_55` double DEFAULT NULL,
  `t_12_00` double DEFAULT NULL,
  `t_12_05` double DEFAULT NULL,
  `t_12_10` double DEFAULT NULL,
  `t_12_15` double DEFAULT NULL,
  `t_12_20` double DEFAULT NULL,
  `t_12_25` double DEFAULT NULL,
  `t_12_30` double DEFAULT NULL,
  `t_12_35` double DEFAULT NULL,
  `t_12_40` double DEFAULT NULL,
  `t_12_45` double DEFAULT NULL,
  `t_12_50` double DEFAULT NULL,
  `t_12_55` double DEFAULT NULL,
  `t_13_00` double DEFAULT NULL,
  `t_13_05` double DEFAULT NULL,
  `t_13_10` double DEFAULT NULL,
  `t_13_15` double DEFAULT NULL,
  `t_13_20` double DEFAULT NULL,
  `t_13_25` double DEFAULT NULL,
  `t_13_30` double DEFAULT NULL,
  `t_13_35` double DEFAULT NULL,
  `t_13_40` double DEFAULT NULL,
  `t_13_45` double DEFAULT NULL,
  `t_13_50` double DEFAULT NULL,
  `t_13_55` double DEFAULT NULL,
  `t_14_00` double DEFAULT NULL,
  `t_14_05` double DEFAULT NULL,
  `t_14_10` double DEFAULT NULL,
  `t_14_15` double DEFAULT NULL,
  `t_14_20` double DEFAULT NULL,
  `t_14_25` double DEFAULT NULL,
  `t_14_30` double DEFAULT NULL,
  `t_14_35` double DEFAULT NULL,
  `t_14_40` double DEFAULT NULL,
  `t_14_45` double DEFAULT NULL,
  `t_14_50` double DEFAULT NULL,
  `t_14_55` double DEFAULT NULL,
  `t_15_00` double DEFAULT NULL,
  `t_15_05` double DEFAULT NULL,
  `t_15_10` double DEFAULT NULL,
  `t_15_15` double DEFAULT NULL,
  `t_15_20` double DEFAULT NULL,
  `t_15_25` double DEFAULT NULL,
  `t_15_30` double DEFAULT NULL,
  `t_15_35` double DEFAULT NULL,
  `t_15_40` double DEFAULT NULL,
  `t_15_45` double DEFAULT NULL,
  `t_15_50` double DEFAULT NULL,
  `t_15_55` double DEFAULT NULL,
  `t_16_00` double DEFAULT NULL,
  `t_16_05` double DEFAULT NULL,
  `t_16_10` double DEFAULT NULL,
  `t_16_15` double DEFAULT NULL,
  `t_16_20` double DEFAULT NULL,
  `t_16_25` double DEFAULT NULL,
  `t_16_30` double DEFAULT NULL,
  `t_16_35` double DEFAULT NULL,
  `t_16_40` double DEFAULT NULL,
  `t_16_45` double DEFAULT NULL,
  `t_16_50` double DEFAULT NULL,
  `t_16_55` double DEFAULT NULL,
  `t_17_00` double DEFAULT NULL,
  `t_17_05` double DEFAULT NULL,
  `t_17_10` double DEFAULT NULL,
  `t_17_15` double DEFAULT NULL,
  `t_17_20` double DEFAULT NULL,
  `t_17_25` double DEFAULT NULL,
  `t_17_30` double DEFAULT NULL,
  `t_17_35` double DEFAULT NULL,
  `t_17_40` double DEFAULT NULL,
  `t_17_45` double DEFAULT NULL,
  `t_17_50` double DEFAULT NULL,
  `t_17_55` double DEFAULT NULL,
  `t_18_00` double DEFAULT NULL,
  `t_18_05` double DEFAULT NULL,
  `t_18_10` double DEFAULT NULL,
  `t_18_15` double DEFAULT NULL,
  `t_18_20` double DEFAULT NULL,
  `t_18_25` double DEFAULT NULL,
  `t_18_30` double DEFAULT NULL,
  `t_18_35` double DEFAULT NULL,
  `t_18_40` double DEFAULT NULL,
  `t_18_45` double DEFAULT NULL,
  `t_18_50` double DEFAULT NULL,
  `t_18_55` double DEFAULT NULL,
  `t_19_00` double DEFAULT NULL,
  `t_19_05` double DEFAULT NULL,
  `t_19_10` double DEFAULT NULL,
  `t_19_15` double DEFAULT NULL,
  `t_19_20` double DEFAULT NULL,
  `t_19_25` double DEFAULT NULL,
  `t_19_30` double DEFAULT NULL,
  `t_19_35` double DEFAULT NULL,
  `t_19_40` double DEFAULT NULL,
  `t_19_45` double DEFAULT NULL,
  `t_19_50` double DEFAULT NULL,
  `t_19_55` double DEFAULT NULL,
  `t_20_00` double DEFAULT NULL,
  `t_20_05` double DEFAULT NULL,
  `t_20_10` double DEFAULT NULL,
  `t_20_15` double DEFAULT NULL,
  `t_20_20` double DEFAULT NULL,
  `t_20_25` double DEFAULT NULL,
  `t_20_30` double DEFAULT NULL,
  `t_20_35` double DEFAULT NULL,
  `t_20_40` double DEFAULT NULL,
  `t_20_45` double DEFAULT NULL,
  `t_20_50` double DEFAULT NULL,
  `t_20_55` double DEFAULT NULL,
  `t_21_00` double DEFAULT NULL,
  `t_21_05` double DEFAULT NULL,
  `t_21_10` double DEFAULT NULL,
  `t_21_15` double DEFAULT NULL,
  `t_21_20` double DEFAULT NULL,
  `t_21_25` double DEFAULT NULL,
  `t_21_30` double DEFAULT NULL,
  `t_21_35` double DEFAULT NULL,
  `t_21_40` double DEFAULT NULL,
  `t_21_45` double DEFAULT NULL,
  `t_21_50` double DEFAULT NULL,
  `t_21_55` double DEFAULT NULL,
  `t_22_00` double DEFAULT NULL,
  `t_22_05` double DEFAULT NULL,
  `t_22_10` double DEFAULT NULL,
  `t_22_15` double DEFAULT NULL,
  `t_22_20` double DEFAULT NULL,
  `t_22_25` double DEFAULT NULL,
  `t_22_30` double DEFAULT NULL,
  `t_22_35` double DEFAULT NULL,
  `t_22_40` double DEFAULT NULL,
  `t_22_45` double DEFAULT NULL,
  `t_22_50` double DEFAULT NULL,
  `t_22_55` double DEFAULT NULL,
  `t_23_00` double DEFAULT NULL,
  `t_23_05` double DEFAULT NULL,
  `t_23_10` double DEFAULT NULL,
  `t_23_15` double DEFAULT NULL,
  `t_23_20` double DEFAULT NULL,
  `t_23_25` double DEFAULT NULL,
  `t_23_30` double DEFAULT NULL,
  `t_23_35` double DEFAULT NULL,
  `t_23_40` double DEFAULT NULL,
  `t_23_45` double DEFAULT NULL,
  `t_23_50` double DEFAULT NULL,
  `t_23_55` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`,`date`),
  CONSTRAINT `FK_2ko4xw5npexsu3949m79of4td` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kWh_5mn`
--

LOCK TABLES `kWh_5mn` WRITE;
/*!40000 ALTER TABLE `kWh_5mn` DISABLE KEYS */;
/*!40000 ALTER TABLE `kWh_5mn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kWh_boundaries`
--

DROP TABLE IF EXISTS `kWh_boundaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kWh_boundaries` (
  `date_creation` datetime NOT NULL,
  `weekday` int(11) NOT NULL,
  `equipment_id` int(11) NOT NULL,
  `min_cons_00_00` double DEFAULT NULL,
  `min_cons_00_15` double DEFAULT NULL,
  `min_cons_00_30` double DEFAULT NULL,
  `min_cons_00_45` double DEFAULT NULL,
  `min_cons_01_00` double DEFAULT NULL,
  `min_cons_01_15` double DEFAULT NULL,
  `min_cons_01_30` double DEFAULT NULL,
  `min_cons_01_45` double DEFAULT NULL,
  `min_cons_02_00` double DEFAULT NULL,
  `min_cons_02_15` double DEFAULT NULL,
  `min_cons_02_30` double DEFAULT NULL,
  `min_cons_02_45` double DEFAULT NULL,
  `min_cons_03_00` double DEFAULT NULL,
  `min_cons_03_15` double DEFAULT NULL,
  `min_cons_03_30` double DEFAULT NULL,
  `min_cons_03_45` double DEFAULT NULL,
  `min_cons_04_00` double DEFAULT NULL,
  `min_cons_04_15` double DEFAULT NULL,
  `min_cons_04_30` double DEFAULT NULL,
  `min_cons_04_45` double DEFAULT NULL,
  `min_cons_05_00` double DEFAULT NULL,
  `min_cons_05_15` double DEFAULT NULL,
  `min_cons_05_30` double DEFAULT NULL,
  `min_cons_05_45` double DEFAULT NULL,
  `min_cons_06_00` double DEFAULT NULL,
  `min_cons_06_15` double DEFAULT NULL,
  `min_cons_06_30` double DEFAULT NULL,
  `min_cons_06_45` double DEFAULT NULL,
  `min_cons_07_00` double DEFAULT NULL,
  `min_cons_07_15` double DEFAULT NULL,
  `min_cons_07_30` double DEFAULT NULL,
  `min_cons_07_45` double DEFAULT NULL,
  `min_cons_08_00` double DEFAULT NULL,
  `min_cons_08_15` double DEFAULT NULL,
  `min_cons_08_30` double DEFAULT NULL,
  `min_cons_08_45` double DEFAULT NULL,
  `min_cons_09_00` double DEFAULT NULL,
  `min_cons_09_15` double DEFAULT NULL,
  `min_cons_09_30` double DEFAULT NULL,
  `min_cons_09_45` double DEFAULT NULL,
  `min_cons_10_00` double DEFAULT NULL,
  `min_cons_10_15` double DEFAULT NULL,
  `min_cons_10_30` double DEFAULT NULL,
  `min_cons_10_45` double DEFAULT NULL,
  `min_cons_11_00` double DEFAULT NULL,
  `min_cons_11_15` double DEFAULT NULL,
  `min_cons_11_30` double DEFAULT NULL,
  `min_cons_11_45` double DEFAULT NULL,
  `min_cons_12_00` double DEFAULT NULL,
  `min_cons_12_15` double DEFAULT NULL,
  `min_cons_12_30` double DEFAULT NULL,
  `min_cons_12_45` double DEFAULT NULL,
  `min_cons_13_00` double DEFAULT NULL,
  `min_cons_13_15` double DEFAULT NULL,
  `min_cons_13_30` double DEFAULT NULL,
  `min_cons_13_45` double DEFAULT NULL,
  `min_cons_14_00` double DEFAULT NULL,
  `min_cons_14_15` double DEFAULT NULL,
  `min_cons_14_30` double DEFAULT NULL,
  `min_cons_14_45` double DEFAULT NULL,
  `min_cons_15_00` double DEFAULT NULL,
  `min_cons_15_15` double DEFAULT NULL,
  `min_cons_15_30` double DEFAULT NULL,
  `min_cons_15_45` double DEFAULT NULL,
  `min_cons_16_00` double DEFAULT NULL,
  `min_cons_16_15` double DEFAULT NULL,
  `min_cons_16_30` double DEFAULT NULL,
  `min_cons_16_45` double DEFAULT NULL,
  `min_cons_17_00` double DEFAULT NULL,
  `min_cons_17_15` double DEFAULT NULL,
  `min_cons_17_30` double DEFAULT NULL,
  `min_cons_17_45` double DEFAULT NULL,
  `min_cons_18_00` double DEFAULT NULL,
  `min_cons_18_15` double DEFAULT NULL,
  `min_cons_18_30` double DEFAULT NULL,
  `min_cons_18_45` double DEFAULT NULL,
  `min_cons_19_00` double DEFAULT NULL,
  `min_cons_19_15` double DEFAULT NULL,
  `min_cons_19_30` double DEFAULT NULL,
  `min_cons_19_45` double DEFAULT NULL,
  `min_cons_20_00` double DEFAULT NULL,
  `min_cons_20_15` double DEFAULT NULL,
  `min_cons_20_30` double DEFAULT NULL,
  `min_cons_20_45` double DEFAULT NULL,
  `min_cons_21_00` double DEFAULT NULL,
  `min_cons_21_15` double DEFAULT NULL,
  `min_cons_21_30` double DEFAULT NULL,
  `min_cons_21_45` double DEFAULT NULL,
  `min_cons_22_00` double DEFAULT NULL,
  `min_cons_22_15` double DEFAULT NULL,
  `min_cons_22_30` double DEFAULT NULL,
  `min_cons_22_45` double DEFAULT NULL,
  `min_cons_23_00` double DEFAULT NULL,
  `min_cons_23_15` double DEFAULT NULL,
  `min_cons_23_30` double DEFAULT NULL,
  `min_cons_23_45` double DEFAULT NULL,
  `max_cons_00_00` double DEFAULT NULL,
  `max_cons_00_30` double DEFAULT NULL,
  `max_cons_01_00` double DEFAULT NULL,
  `max_cons_01_30` double DEFAULT NULL,
  `max_cons_02_00` double DEFAULT NULL,
  `max_cons_02_30` double DEFAULT NULL,
  `max_cons_03_00` double DEFAULT NULL,
  `max_cons_03_30` double DEFAULT NULL,
  `max_cons_04_00` double DEFAULT NULL,
  `max_cons_04_30` double DEFAULT NULL,
  `max_cons_05_00` double DEFAULT NULL,
  `max_cons_05_30` double DEFAULT NULL,
  `max_cons_06_00` double DEFAULT NULL,
  `max_cons_06_30` double DEFAULT NULL,
  `max_cons_07_00` double DEFAULT NULL,
  `max_cons_07_30` double DEFAULT NULL,
  `max_cons_08_00` double DEFAULT NULL,
  `max_cons_08_30` double DEFAULT NULL,
  `max_cons_09_00` double DEFAULT NULL,
  `max_cons_09_30` double DEFAULT NULL,
  `max_cons_10_00` double DEFAULT NULL,
  `max_cons_10_30` double DEFAULT NULL,
  `max_cons_11_00` double DEFAULT NULL,
  `max_cons_11_30` double DEFAULT NULL,
  `max_cons_12_00` double DEFAULT NULL,
  `max_cons_12_30` double DEFAULT NULL,
  `max_cons_13_00` double DEFAULT NULL,
  `max_cons_13_30` double DEFAULT NULL,
  `max_cons_14_00` double DEFAULT NULL,
  `max_cons_14_30` double DEFAULT NULL,
  `max_cons_15_00` double DEFAULT NULL,
  `max_cons_15_30` double DEFAULT NULL,
  `max_cons_16_00` double DEFAULT NULL,
  `max_cons_16_30` double DEFAULT NULL,
  `max_cons_17_00` double DEFAULT NULL,
  `max_cons_17_30` double DEFAULT NULL,
  `max_cons_18_00` double DEFAULT NULL,
  `max_cons_18_30` double DEFAULT NULL,
  `max_cons_19_00` double DEFAULT NULL,
  `max_cons_19_30` double DEFAULT NULL,
  `max_cons_20_00` double DEFAULT NULL,
  `max_cons_20_30` double DEFAULT NULL,
  `max_cons_21_00` double DEFAULT NULL,
  `max_cons_21_30` double DEFAULT NULL,
  `max_cons_22_00` double DEFAULT NULL,
  `max_cons_22_30` double DEFAULT NULL,
  `max_cons_23_00` double DEFAULT NULL,
  `max_cons_23_30` double DEFAULT NULL,
  PRIMARY KEY (`date_creation`,`weekday`,`equipment_id`),
  KEY `FK_svfmqos1lqpmxokwewv5j99pk` (`equipment_id`),
  CONSTRAINT `FK_svfmqos1lqpmxokwewv5j99pk` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kWh_boundaries`
--

LOCK TABLES `kWh_boundaries` WRITE;
/*!40000 ALTER TABLE `kWh_boundaries` DISABLE KEYS */;
/*!40000 ALTER TABLE `kWh_boundaries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kWh_forecast`
--

DROP TABLE IF EXISTS `kWh_forecast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kWh_forecast` (
  `date_forecast` datetime NOT NULL,
  `equipment_id` int(11) NOT NULL,
  `parameter_type` varchar(45) NOT NULL,
  `type` varchar(45) DEFAULT NULL COMMENT 'Can assume three values: dayahead intra day short term (depending when the prediction was done)',
  `t_00_00` double DEFAULT NULL,
  `t_00_15` double DEFAULT NULL,
  `t_00_30` double DEFAULT NULL,
  `t_00_45` double DEFAULT NULL,
  `t_01_00` double DEFAULT NULL,
  `t_01_15` double DEFAULT NULL,
  `t_01_30` double DEFAULT NULL,
  `t_01_45` double DEFAULT NULL,
  `t_02_00` double DEFAULT NULL,
  `t_02_15` double DEFAULT NULL,
  `t_02_30` double DEFAULT NULL,
  `t_02_45` double DEFAULT NULL,
  `t_03_00` double DEFAULT NULL,
  `t_03_15` double DEFAULT NULL,
  `t_03_30` double DEFAULT NULL,
  `t_03_45` double DEFAULT NULL,
  `t_04_00` double DEFAULT NULL,
  `t_04_15` double DEFAULT NULL,
  `t_04_30` double DEFAULT NULL,
  `t_04_45` double DEFAULT NULL,
  `t_05_00` double DEFAULT NULL,
  `t_05_15` double DEFAULT NULL,
  `t_05_30` double DEFAULT NULL,
  `t_05_45` double DEFAULT NULL,
  `t_06_00` double DEFAULT NULL,
  `t_06_15` double DEFAULT NULL,
  `t_06_30` double DEFAULT NULL,
  `t_06_45` double DEFAULT NULL,
  `t_07_00` double DEFAULT NULL,
  `t_07_15` double DEFAULT NULL,
  `t_07_30` double DEFAULT NULL,
  `t_07_45` double DEFAULT NULL,
  `t_08_00` double DEFAULT NULL,
  `t_08_15` double DEFAULT NULL,
  `t_08_30` double DEFAULT NULL,
  `t_08_45` double DEFAULT NULL,
  `t_09_00` double DEFAULT NULL,
  `t_09_15` double DEFAULT NULL,
  `t_09_30` double DEFAULT NULL,
  `t_09_45` double DEFAULT NULL,
  `t_10_00` double DEFAULT NULL,
  `t_10_15` double DEFAULT NULL,
  `t_10_30` double DEFAULT NULL,
  `t_10_45` double DEFAULT NULL,
  `t_11_00` double DEFAULT NULL,
  `t_11_15` double DEFAULT NULL,
  `t_11_30` double DEFAULT NULL,
  `t_11_45` double DEFAULT NULL,
  `t_12_00` double DEFAULT NULL,
  `t_12_15` double DEFAULT NULL,
  `t_12_30` double DEFAULT NULL,
  `t_12_45` double DEFAULT NULL,
  `t_13_00` double DEFAULT NULL,
  `t_13_15` double DEFAULT NULL,
  `t_13_30` double DEFAULT NULL,
  `t_13_45` double DEFAULT NULL,
  `t_14_00` double DEFAULT NULL,
  `t_14_15` double DEFAULT NULL,
  `t_14_30` double DEFAULT NULL,
  `t_14_45` double DEFAULT NULL,
  `t_15_00` double DEFAULT NULL,
  `t_15_15` double DEFAULT NULL,
  `t_15_30` double DEFAULT NULL,
  `t_15_45` double DEFAULT NULL,
  `t_16_00` double DEFAULT NULL,
  `t_16_15` double DEFAULT NULL,
  `t_16_30` double DEFAULT NULL,
  `t_16_45` double DEFAULT NULL,
  `t_17_00` double DEFAULT NULL,
  `t_17_15` double DEFAULT NULL,
  `t_17_30` double DEFAULT NULL,
  `t_17_45` double DEFAULT NULL,
  `t_18_00` double DEFAULT NULL,
  `t_18_15` double DEFAULT NULL,
  `t_18_30` double DEFAULT NULL,
  `t_18_45` double DEFAULT NULL,
  `t_19_00` double DEFAULT NULL,
  `t_19_15` double DEFAULT NULL,
  `t_19_30` double DEFAULT NULL,
  `t_19_45` double DEFAULT NULL,
  `t_20_00` double DEFAULT NULL,
  `t_20_15` double DEFAULT NULL,
  `t_20_30` double DEFAULT NULL,
  `t_20_45` double DEFAULT NULL,
  `t_21_00` double DEFAULT NULL,
  `t_21_15` double DEFAULT NULL,
  `t_21_30` double DEFAULT NULL,
  `t_21_45` double DEFAULT NULL,
  `t_22_00` double DEFAULT NULL,
  `t_22_15` double DEFAULT NULL,
  `t_22_30` double DEFAULT NULL,
  `t_22_45` double DEFAULT NULL,
  `t_23_00` double DEFAULT NULL,
  `t_23_15` double DEFAULT NULL,
  `t_23_30` double DEFAULT NULL,
  `t_23_45` double DEFAULT NULL,
  PRIMARY KEY (`date_forecast`,`equipment_id`,`parameter_type`),
  KEY `FK_cm0tbx13b9c5uespdkgr83u1l` (`equipment_id`),
  CONSTRAINT `FK_cm0tbx13b9c5uespdkgr83u1l` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kWh_forecast`
--

LOCK TABLES `kWh_forecast` WRITE;
/*!40000 ALTER TABLE `kWh_forecast` DISABLE KEYS */;
/*!40000 ALTER TABLE `kWh_forecast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kWh_hourly`
--

DROP TABLE IF EXISTS `kWh_hourly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kWh_hourly` (
  `equipment_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `t_00_00` double DEFAULT NULL,
  `t_01_00` double DEFAULT NULL,
  `t_02_00` double DEFAULT NULL,
  `t_03_00` double DEFAULT NULL,
  `t_04_00` double DEFAULT NULL,
  `t_05_00` double DEFAULT NULL,
  `t_06_00` double DEFAULT NULL,
  `t_07_00` double DEFAULT NULL,
  `t_08_00` double DEFAULT NULL,
  `t_09_00` double DEFAULT NULL,
  `t_10_00` double DEFAULT NULL,
  `t_11_00` double DEFAULT NULL,
  `t_12_00` double DEFAULT NULL,
  `t_13_00` double DEFAULT NULL,
  `t_14_00` double DEFAULT NULL,
  `t_15_00` double DEFAULT NULL,
  `t_16_00` double DEFAULT NULL,
  `t_17_00` double DEFAULT NULL,
  `t_18_00` double DEFAULT NULL,
  `t_19_00` double DEFAULT NULL,
  `t_20_00` double DEFAULT NULL,
  `t_21_00` double DEFAULT NULL,
  `t_22_00` double DEFAULT NULL,
  `t_23_00` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`,`date`),
  CONSTRAINT `FK_hx8qkk9hjubqyhneyd2eryl17` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kWh_hourly`
--

LOCK TABLES `kWh_hourly` WRITE;
/*!40000 ALTER TABLE `kWh_hourly` DISABLE KEYS */;
/*!40000 ALTER TABLE `kWh_hourly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `market_signal`
--

DROP TABLE IF EXISTS `market_signal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `market_signal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `DSO_territory_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL COMMENT 'reduction staritng time',
  `end_time` datetime DEFAULT NULL,
  `amount_reduction` longtext NOT NULL,
  `signal_date` datetime DEFAULT NULL COMMENT 'time the signal is received',
  `achieved` bit(1) DEFAULT NULL,
  `time_interval` int(11) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `start_time_text` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fanb6qkrw0dam2vccppy84306` (`DSO_territory_id`),
  CONSTRAINT `FK_fanb6qkrw0dam2vccppy84306` FOREIGN KEY (`DSO_territory_id`) REFERENCES `DSO_territory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `market_signal`
--

LOCK TABLES `market_signal` WRITE;
/*!40000 ALTER TABLE `market_signal` DISABLE KEYS */;
/*!40000 ALTER TABLE `market_signal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operating_state`
--

DROP TABLE IF EXISTS `operating_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operating_state` (
  `state` int(11) NOT NULL,
  `equipment_id` int(11) NOT NULL,
  `kW` double DEFAULT NULL,
  PRIMARY KEY (`state`,`equipment_id`),
  KEY `FK_ny9twhibp30omlhj8rk97ttlx` (`equipment_id`),
  CONSTRAINT `FK_ny9twhibp30omlhj8rk97ttlx` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operating_state`
--

LOCK TABLES `operating_state` WRITE;
/*!40000 ALTER TABLE `operating_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `operating_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `market_signal_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gy8dvqf7cpmtg86cvv2pchod8` (`market_signal_id`),
  CONSTRAINT `FK_gy8dvqf7cpmtg86cvv2pchod8` FOREIGN KEY (`market_signal_id`) REFERENCES `market_signal` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_has_action`
--

DROP TABLE IF EXISTS `plan_has_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_has_action` (
  `plan_id` int(11) NOT NULL,
  `action_id` int(11) NOT NULL,
  `t_start` datetime NOT NULL,
  `t_end` datetime DEFAULT NULL,
  `planned_amount` longtext NOT NULL,
  `implemented` bit(1) DEFAULT NULL,
  `signal_value` varchar(45) DEFAULT NULL,
  `actual_amount` longtext,
  `updated_actual_at` datetime DEFAULT NULL,
  `amount_rec_progress_to_send` int(11) NOT NULL,
  `amount_rec_progress_sent` int(11) NOT NULL,
  PRIMARY KEY (`plan_id`,`action_id`),
  KEY `FK_jlw9n60msgd8806x1yy97idj7` (`action_id`),
  CONSTRAINT `FK_e94yhg1y1dol76ppkj37d7bgr` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`),
  CONSTRAINT `FK_jlw9n60msgd8806x1yy97idj7` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_has_action`
--

LOCK TABLES `plan_has_action` WRITE;
/*!40000 ALTER TABLE `plan_has_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_has_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_weight`
--

DROP TABLE IF EXISTS `plan_weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_weight` (
  `plan_id` int(11) NOT NULL,
  `action_attribute_name` varchar(255) NOT NULL,
  `weight` decimal(6,5) DEFAULT NULL,
  PRIMARY KEY (`plan_id`,`action_attribute_name`),
  KEY `FK_nut3llrbj5sgm6s1hiw8mpdjt` (`action_attribute_name`),
  CONSTRAINT `FK_lir3yf2q1i6oc2n1gmql74ymq` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`),
  CONSTRAINT `FK_nut3llrbj5sgm6s1hiw8mpdjt` FOREIGN KEY (`action_attribute_name`) REFERENCES `action_attribute` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_weight`
--

LOCK TABLES `plan_weight` WRITE;
/*!40000 ALTER TABLE `plan_weight` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_weight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prosumer`
--

DROP TABLE IF EXISTS `prosumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prosumer` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prosumer`
--

LOCK TABLES `prosumer` WRITE;
/*!40000 ALTER TABLE `prosumer` DISABLE KEYS */;
INSERT INTO `prosumer` VALUES (1,'Pr_0','sedini0','giulia.depoli@wattics.com',NULL);
/*!40000 ALTER TABLE `prosumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prosumer_has_site`
--

DROP TABLE IF EXISTS `prosumer_has_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prosumer_has_site` (
  `prosumer_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prosumer_id`,`site_id`),
  KEY `FK_q1h7gs6fgifsi745vqkbwve6n` (`site_id`),
  CONSTRAINT `FK_3a4pf26ih02g60pdxasq91bpe` FOREIGN KEY (`prosumer_id`) REFERENCES `prosumer` (`id`),
  CONSTRAINT `FK_q1h7gs6fgifsi745vqkbwve6n` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prosumer_has_site`
--

LOCK TABLES `prosumer_has_site` WRITE;
/*!40000 ALTER TABLE `prosumer_has_site` DISABLE KEYS */;
INSERT INTO `prosumer_has_site` VALUES (1,1,NULL);
/*!40000 ALTER TABLE `prosumer_has_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prosumer_preference`
--

DROP TABLE IF EXISTS `prosumer_preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prosumer_preference` (
  `action_id` int(11) NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `pUmin_00_00` double DEFAULT NULL,
  `pUmin_00_15` double DEFAULT NULL,
  `pUmin_00_30` double DEFAULT NULL,
  `pUmin_00_45` double DEFAULT NULL,
  `pUmin_01_00` double DEFAULT NULL,
  `pUmin_01_15` double DEFAULT NULL,
  `pUmin_01_30` double DEFAULT NULL,
  `pUmin_01_45` double DEFAULT NULL,
  `pUmin_02_00` double DEFAULT NULL,
  `pUmin_02_15` double DEFAULT NULL,
  `pUmin_02_30` double DEFAULT NULL,
  `pUmin_02_45` double DEFAULT NULL,
  `pUmin_03_00` double DEFAULT NULL,
  `pUmin_03_15` double DEFAULT NULL,
  `pUmin_03_30` double DEFAULT NULL,
  `pUmin_03_45` double DEFAULT NULL,
  `pUmin_04_00` double DEFAULT NULL,
  `pUmin_04_15` double DEFAULT NULL,
  `pUmin_04_30` double DEFAULT NULL,
  `pUmin_04_45` double DEFAULT NULL,
  `pUmin_05_00` double DEFAULT NULL,
  `pUmin_05_15` double DEFAULT NULL,
  `pUmin_05_30` double DEFAULT NULL,
  `pUmin_05_45` double DEFAULT NULL,
  `pUmin_06_00` double DEFAULT NULL,
  `pUmin_06_15` double DEFAULT NULL,
  `pUmin_06_30` double DEFAULT NULL,
  `pUmin_06_45` double DEFAULT NULL,
  `pUmin_07_00` double DEFAULT NULL,
  `pUmin_07_15` double DEFAULT NULL,
  `pUmin_07_30` double DEFAULT NULL,
  `pUmin_07_45` double DEFAULT NULL,
  `pUmin_08_00` double DEFAULT NULL,
  `pUmin_08_15` double DEFAULT NULL,
  `pUmin_08_30` double DEFAULT NULL,
  `pUmin_08_45` double DEFAULT NULL,
  `pUmin_09_00` double DEFAULT NULL,
  `pUmin_09_15` double DEFAULT NULL,
  `pUmin_09_30` double DEFAULT NULL,
  `pUmin_09_45` double DEFAULT NULL,
  `pUmin_10_00` double DEFAULT NULL,
  `pUmin_10_15` double DEFAULT NULL,
  `pUmin_10_30` double DEFAULT NULL,
  `pUmin_10_45` double DEFAULT NULL,
  `pUmin_11_00` double DEFAULT NULL,
  `pUmin_11_15` double DEFAULT NULL,
  `pUmin_11_30` double DEFAULT NULL,
  `pUmin_11_45` double DEFAULT NULL,
  `pUmin_12_00` double DEFAULT NULL,
  `pUmin_12_15` double DEFAULT NULL,
  `pUmin_12_30` double DEFAULT NULL,
  `pUmin_12_45` double DEFAULT NULL,
  `pUmin_13_00` double DEFAULT NULL,
  `pUmin_13_15` double DEFAULT NULL,
  `pUmin_13_30` double DEFAULT NULL,
  `pUmin_13_45` double DEFAULT NULL,
  `pUmin_14_00` double DEFAULT NULL,
  `pUmin_14_15` double DEFAULT NULL,
  `pUmin_14_30` double DEFAULT NULL,
  `pUmin_14_45` double DEFAULT NULL,
  `pUmin_15_00` double DEFAULT NULL,
  `pUmin_15_15` double DEFAULT NULL,
  `pUmin_15_30` double DEFAULT NULL,
  `pUmin_15_45` double DEFAULT NULL,
  `pUmin_16_00` double DEFAULT NULL,
  `pUmin_16_15` double DEFAULT NULL,
  `pUmin_16_30` double DEFAULT NULL,
  `pUmin_16_45` double DEFAULT NULL,
  `pUmin_17_00` double DEFAULT NULL,
  `pUmin_17_15` double DEFAULT NULL,
  `pUmin_17_30` double DEFAULT NULL,
  `pUmin_17_45` double DEFAULT NULL,
  `pUmin_18_00` double DEFAULT NULL,
  `pUmin_18_15` double DEFAULT NULL,
  `pUmin_18_30` double DEFAULT NULL,
  `pUmin_18_45` double DEFAULT NULL,
  `pUmin_19_00` double DEFAULT NULL,
  `pUmin_19_15` double DEFAULT NULL,
  `pUmin_19_30` double DEFAULT NULL,
  `pUmin_19_45` double DEFAULT NULL,
  `pUmin_20_00` double DEFAULT NULL,
  `pUmin_20_15` double DEFAULT NULL,
  `pUmin_20_30` double DEFAULT NULL,
  `pUmin_20_45` double DEFAULT NULL,
  `pUmin_21_00` double DEFAULT NULL,
  `pUmin_21_15` double DEFAULT NULL,
  `pUmin_21_30` double DEFAULT NULL,
  `pUmin_21_45` double DEFAULT NULL,
  `pUmin_22_00` double DEFAULT NULL,
  `pUmin_22_15` double DEFAULT NULL,
  `pUmin_22_30` double DEFAULT NULL,
  `pUmin_22_45` double DEFAULT NULL,
  `pUmin_23_00` double DEFAULT NULL,
  `pUmin_23_15` double DEFAULT NULL,
  `pUmin_23_30` double DEFAULT NULL,
  `pUmin_23_45` double DEFAULT NULL,
  PRIMARY KEY (`action_id`),
  CONSTRAINT `FK_nv6yiph7tmahjxbs7kh75yfoa` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prosumer_preference`
--

LOCK TABLES `prosumer_preference` WRITE;
/*!40000 ALTER TABLE `prosumer_preference` DISABLE KEYS */;
/*!40000 ALTER TABLE `prosumer_preference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reducible_footprint`
--

DROP TABLE IF EXISTS `reducible_footprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reducible_footprint` (
  `equipment_id` int(11) NOT NULL,
  `min_load` double DEFAULT NULL,
  `max_load` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`),
  CONSTRAINT `FK_esibvs20r74np1jm39qra5vud` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reducible_footprint`
--

LOCK TABLES `reducible_footprint` WRITE;
/*!40000 ALTER TABLE `reducible_footprint` DISABLE KEYS */;
/*!40000 ALTER TABLE `reducible_footprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reschedulable_footprint`
--

DROP TABLE IF EXISTS `reschedulable_footprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reschedulable_footprint` (
  `equipment_id` int(11) NOT NULL,
  `length_cycle` int(11) DEFAULT NULL COMMENT 'length of the cycle period in minutes',
  `kwh_cycle` double DEFAULT NULL,
  `demand_pattern` longtext,
  `min_load` double DEFAULT NULL,
  `max_load` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`),
  CONSTRAINT `FK_19ywjh3neqr5v53nunx5xd5ln` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reschedulable_footprint`
--

LOCK TABLES `reschedulable_footprint` WRITE;
/*!40000 ALTER TABLE `reschedulable_footprint` DISABLE KEYS */;
/*!40000 ALTER TABLE `reschedulable_footprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site` (
  `id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `transformer_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_19fgvo1we8shbhwth99tvvk9p` (`city_id`),
  KEY `FK_dfq5g64k85lfp6ybe8k1u9yic` (`transformer_id`),
  CONSTRAINT `FK_19fgvo1we8shbhwth99tvvk9p` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `FK_dfq5g64k85lfp6ybe8k1u9yic` FOREIGN KEY (`transformer_id`) REFERENCES `transformer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site`
--

LOCK TABLES `site` WRITE;
/*!40000 ALTER TABLE `site` DISABLE KEYS */;
INSERT INTO `site` VALUES (1,1,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_SLA`
--

DROP TABLE IF EXISTS `site_SLA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_SLA` (
  `site_id` int(11) NOT NULL,
  `max_number_call` int(11) DEFAULT NULL,
  `max_number_call_daily` int(11) DEFAULT NULL,
  PRIMARY KEY (`site_id`),
  CONSTRAINT `FK_nf4ehloee1wgk52k2t28lmpsk` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_SLA`
--

LOCK TABLES `site_SLA` WRITE;
/*!40000 ALTER TABLE `site_SLA` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_SLA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_condition_profile`
--

DROP TABLE IF EXISTS `site_condition_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_condition_profile` (
  `id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `weekday` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `t_00_00` double DEFAULT NULL,
  `t_00_15` double DEFAULT NULL,
  `t_00_30` double DEFAULT NULL,
  `t_00_45` double DEFAULT NULL,
  `t_01_00` double DEFAULT NULL,
  `t_01_15` double DEFAULT NULL,
  `t_01_30` double DEFAULT NULL,
  `t_01_45` double DEFAULT NULL,
  `t_02_00` double DEFAULT NULL,
  `t_02_15` double DEFAULT NULL,
  `t_02_30` double DEFAULT NULL,
  `t_02_45` double DEFAULT NULL,
  `t_03_00` double DEFAULT NULL,
  `t_03_15` double DEFAULT NULL,
  `t_03_30` double DEFAULT NULL,
  `t_03_45` double DEFAULT NULL,
  `t_04_00` double DEFAULT NULL,
  `t_04_15` double DEFAULT NULL,
  `t_04_30` double DEFAULT NULL,
  `t_04_45` double DEFAULT NULL,
  `t_05_00` double DEFAULT NULL,
  `t_05_15` double DEFAULT NULL,
  `t_05_30` double DEFAULT NULL,
  `t_05_45` double DEFAULT NULL,
  `t_06_00` double DEFAULT NULL,
  `t_06_15` double DEFAULT NULL,
  `t_06_30` double DEFAULT NULL,
  `t_06_45` double DEFAULT NULL,
  `t_07_00` double DEFAULT NULL,
  `t_07_15` double DEFAULT NULL,
  `t_07_30` double DEFAULT NULL,
  `t_07_45` double DEFAULT NULL,
  `t_08_00` double DEFAULT NULL,
  `t_08_15` double DEFAULT NULL,
  `t_08_30` double DEFAULT NULL,
  `t_08_45` double DEFAULT NULL,
  `t_09_00` double DEFAULT NULL,
  `t_09_15` double DEFAULT NULL,
  `t_09_30` double DEFAULT NULL,
  `t_09_45` double DEFAULT NULL,
  `t_10_00` double DEFAULT NULL,
  `t_10_15` double DEFAULT NULL,
  `t_10_30` double DEFAULT NULL,
  `t_10_45` double DEFAULT NULL,
  `t_11_00` double DEFAULT NULL,
  `t_11_15` double DEFAULT NULL,
  `t_11_30` double DEFAULT NULL,
  `t_11_45` double DEFAULT NULL,
  `t_12_00` double DEFAULT NULL,
  `t_12_15` double DEFAULT NULL,
  `t_12_30` double DEFAULT NULL,
  `t_12_45` double DEFAULT NULL,
  `t_13_00` double DEFAULT NULL,
  `t_13_15` double DEFAULT NULL,
  `t_13_30` double DEFAULT NULL,
  `t_13_45` double DEFAULT NULL,
  `t_14_00` double DEFAULT NULL,
  `t_14_15` double DEFAULT NULL,
  `t_14_30` double DEFAULT NULL,
  `t_14_45` double DEFAULT NULL,
  `t_15_00` double DEFAULT NULL,
  `t_15_15` double DEFAULT NULL,
  `t_15_30` double DEFAULT NULL,
  `t_15_45` double DEFAULT NULL,
  `t_16_00` double DEFAULT NULL,
  `t_16_15` double DEFAULT NULL,
  `t_16_30` double DEFAULT NULL,
  `t_16_45` double DEFAULT NULL,
  `t_17_00` double DEFAULT NULL,
  `t_17_15` double DEFAULT NULL,
  `t_17_30` double DEFAULT NULL,
  `t_17_45` double DEFAULT NULL,
  `t_18_00` double DEFAULT NULL,
  `t_18_15` double DEFAULT NULL,
  `t_18_30` double DEFAULT NULL,
  `t_18_45` double DEFAULT NULL,
  `t_19_00` double DEFAULT NULL,
  `t_19_15` double DEFAULT NULL,
  `t_19_30` double DEFAULT NULL,
  `t_19_45` double DEFAULT NULL,
  `t_20_00` double DEFAULT NULL,
  `t_20_15` double DEFAULT NULL,
  `t_20_30` double DEFAULT NULL,
  `t_20_45` double DEFAULT NULL,
  `t_21_00` double DEFAULT NULL,
  `t_21_15` double DEFAULT NULL,
  `t_21_30` double DEFAULT NULL,
  `t_21_45` double DEFAULT NULL,
  `t_22_00` double DEFAULT NULL,
  `t_22_15` double DEFAULT NULL,
  `t_22_30` double DEFAULT NULL,
  `t_22_45` double DEFAULT NULL,
  `t_23_00` double DEFAULT NULL,
  `t_23_15` double DEFAULT NULL,
  `t_23_30` double DEFAULT NULL,
  `t_23_45` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1l0xg1n92fju35sfo79f3dxdj` (`site_id`),
  CONSTRAINT `FK_1l0xg1n92fju35sfo79f3dxdj` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_condition_profile`
--

LOCK TABLES `site_condition_profile` WRITE;
/*!40000 ALTER TABLE `site_condition_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_condition_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_condition_raw`
--

DROP TABLE IF EXISTS `site_condition_raw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_condition_raw` (
  `id` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `site_id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rn1firciwneckkfhicvg2ph79` (`site_id`),
  CONSTRAINT `FK_rn1firciwneckkfhicvg2ph79` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_condition_raw`
--

LOCK TABLES `site_condition_raw` WRITE;
/*!40000 ALTER TABLE `site_condition_raw` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_condition_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_metric`
--

DROP TABLE IF EXISTS `site_metric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_metric` (
  `site_id` int(11) NOT NULL,
  `site_reliability` double DEFAULT NULL,
  `site_balance` double DEFAULT NULL,
  `number_request` int(11) DEFAULT NULL,
  PRIMARY KEY (`site_id`),
  CONSTRAINT `FK_h26bidib7h5j61fjesefnlykw` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_metric`
--

LOCK TABLES `site_metric` WRITE;
/*!40000 ALTER TABLE `site_metric` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_metric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storable_footprint`
--

DROP TABLE IF EXISTS `storable_footprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storable_footprint` (
  `equipment_id` int(11) NOT NULL,
  `maximum_capacity` double DEFAULT NULL,
  `discharge_rate` double DEFAULT NULL,
  PRIMARY KEY (`equipment_id`),
  CONSTRAINT `FK_2ncb6130wnov4balc4v22kvll` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storable_footprint`
--

LOCK TABLES `storable_footprint` WRITE;
/*!40000 ALTER TABLE `storable_footprint` DISABLE KEYS */;
/*!40000 ALTER TABLE `storable_footprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transformer`
--

DROP TABLE IF EXISTS `transformer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transformer` (
  `id` int(11) NOT NULL,
  `DSO_territory_id` int(11) NOT NULL,
  `id_parent` int(11) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lskr29br8vpswu3aysyjutmh7` (`DSO_territory_id`),
  CONSTRAINT `FK_lskr29br8vpswu3aysyjutmh7` FOREIGN KEY (`DSO_territory_id`) REFERENCES `DSO_territory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transformer`
--

LOCK TABLES `transformer` WRITE;
/*!40000 ALTER TABLE `transformer` DISABLE KEYS */;
INSERT INTO `transformer` VALUES (1,1,NULL,NULL);
/*!40000 ALTER TABLE `transformer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weather_condition`
--

DROP TABLE IF EXISTS `weather_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weather_condition` (
  `city_id` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `tyoe` varchar(255) NOT NULL,
  `value` float DEFAULT NULL,
  PRIMARY KEY (`city_id`,`timestamp`,`tyoe`),
  CONSTRAINT `FK_4xtf0tr9dpp9ioiixl99lort9` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weather_condition`
--

LOCK TABLES `weather_condition` WRITE;
/*!40000 ALTER TABLE `weather_condition` DISABLE KEYS */;
/*!40000 ALTER TABLE `weather_condition` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-19 15:35:34
