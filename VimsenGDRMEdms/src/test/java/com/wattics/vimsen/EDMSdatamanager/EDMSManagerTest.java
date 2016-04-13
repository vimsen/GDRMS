package com.wattics.vimsen.EDMSdatamanager;

import java.util.Map;

import org.joda.time.Instant;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.DatabaseSetUp;
import com.wattics.vimsen.entities.Prosumer;

public class EDMSManagerTest {

  @Test
  public void getForecastVGWMap() throws EDMSDataGetterException {
    Prosumer prosumer = new Prosumer(1);
    prosumer.setName("b827eb4c14af");
    String equipmentName = DatabaseSetUp.DEFAULT_EQUIPMENT_NAME;
    Instant startTime = new Instant("2016-04-06T08:29:00+02:00");
    Instant endTime = new Instant("2016-04-06T10:27:00+02:00");
    int intervalDurationSec = 900;
    EDMSDataGetterInterface dataGetter = new EDMSDataGetter();

    Map<Long, Double> consumption = EDMSManager.getKwhForecastConsumptionVGWMap(prosumer, equipmentName, startTime, endTime,
        intervalDurationSec, dataGetter);

    Assert.assertEquals(consumption.size(), 8);

    Instant initialInstant = new Instant("2016-04-06T08:30:00+02:00");

    Long time = initialInstant.getMillis();

    Double value = consumption.get(time);
    Assert.assertEquals(value, 9.52);
  }

  @Test
  public void getConsumptionVGWMap() throws EDMSDataGetterException {
    Prosumer prosumer = new Prosumer(1);
    prosumer.setName("b827eb4c14af");
    String equipmentName = DatabaseSetUp.DEFAULT_EQUIPMENT_NAME;
    Instant startTime = new Instant("2016-04-05T08:27:00+02:00");
    Instant endTime = new Instant("2016-04-05T10:27:00+02:00");
    int intervalDurationSec = 900;
    EDMSDataGetterInterface dataGetter = new EDMSDataGetter();

    Map<Long, Double> consumption = EDMSManager.getKwh15mnConsumptionVGWMap(prosumer, equipmentName, startTime, endTime,
        intervalDurationSec, dataGetter);

    Assert.assertEquals(consumption.size(), 8);

    Instant initialInstant = new Instant("2016-04-05T09:30:00+02:00");

    Long time = initialInstant.getMillis();

    Double value = consumption.get(time);
    Assert.assertEquals(value, -0.26);
  }

}
