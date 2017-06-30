package com.wattics.vimsen.EDMSdatamanager;

import java.util.HashMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.utils.TimeHelpers;

public class EDMSDataGetterTest {

  @Test
  public void getProsumersProductionMeasurmentsVGW() throws EDMSDataGetterException {
    String url = "https://beta.intelen.com/vimsenapi/EDMS_DSS/index.php/intelen/getdataVGW?";
    String prosumerUrl = "prosumers=HP_0001";
    String startdateUrl = "startdate=2015-05-16T11:16:38+02:00";
    String enddateUrl = "enddate=2015-05-16T11:31:38+02:00";
    String slotSize = "interval=900";
    String pointer = "pointer=2";

    String urlRequest = url + prosumerUrl + "&" + startdateUrl + "&" + enddateUrl + "&" + slotSize + "&" + pointer;
    EDMSDataGetter dataGetter = new EDMSDataGetter();
    EDMSMeasurement measurement = dataGetter.getEDMSMeasurementFromVGWUrl(urlRequest);

    System.out.println(measurement.Production.toString());
    Assert.assertEquals(measurement.Production.size(), 1);

    HashMap<DateTime, Double> consumption = (HashMap<DateTime, Double>) measurement.Production.get(0);

    DateTime expectedDate = TimeHelpers.convertStringToDateTime("2015-05-16T11:30:00+02:00");
    Double expectedProduction = 3.5789999999999997;

    for (Entry<DateTime, Double> consumptionEntry : consumption.entrySet()) {
      Assert.assertEquals(consumptionEntry.getKey(), expectedDate);
      Assert.assertEquals(consumptionEntry.getValue(), expectedProduction, 0.0001d);
    }

  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void getProsumersForecastConsumptionMeasurmentsVGWNoDataInResponse() throws EDMSDataGetterException {
    String url = "https://beta.intelen.com/vimsenapi/EDMS_DSS/index.php/intelen/getdataVGW?";
    String prosumerUrl = "prosumers=HP_0001";
    String startdateUrl = "startdate=2015-03-10T11:16:38+02:00";
    String enddateUrl = "enddate=2015-03-10T11:31:38+02:00";
    String slotSize = "interval=900";
    String pointer = "pointer=2";

    String urlRequest = url + prosumerUrl + "&" + startdateUrl + "&" + enddateUrl + "&" + slotSize + "&" + pointer;
    EDMSDataGetter dataGetter = new EDMSDataGetter();
    EDMSMeasurement measurement = dataGetter.getEDMSMeasurementFromVGWUrl(urlRequest);

    HashMap<DateTime, Double> forecastConsumption = (HashMap<DateTime, Double>) measurement.ForecastConsumption.get(0);

    DateTime timeKey = TimeHelpers.convertStringToDateTime("2015-03-11T00:00:00+02:00");
    Double actualForecastValue = forecastConsumption.get(timeKey);
    Assert.assertNull(actualForecastValue);
  }

  @Test
  public void getProsumersForecastConsumptionMeasurmentsVGW() throws EDMSDataGetterException {
    String url = "https://beta.intelen.com/vimsenapi/EDMS_DSS/index.php/intelen/getdataVGW?";
    String prosumerUrl = "prosumers=b827eb4c14af";
    String startdateUrl = "startdate=2016-04-10T11:16:38+02:00";
    String enddateUrl = "enddate=2016-04-10T11:28:38+02:00";
    String slotSize = "interval=900";
    String pointer = "pointer=2";

    String urlRequest = url + prosumerUrl + "&" + startdateUrl + "&" + enddateUrl + "&" + slotSize + "&" + pointer;
    EDMSDataGetter dataGetter = new EDMSDataGetter();
    EDMSMeasurement measurement = dataGetter.getEDMSMeasurementFromVGWUrl(urlRequest);

    HashMap<DateTime, Double> forecastConsumption = (HashMap<DateTime, Double>) measurement.ForecastConsumption.get(0);

    Double expectedForecastValue = 22.85;

    DateTime timeKey = TimeHelpers.convertStringToDateTime("2016-04-11T00:00:00+02:00");
    Double actualForecastValue = forecastConsumption.get(timeKey);
    Assert.assertEquals(actualForecastValue, expectedForecastValue);
  }

}
