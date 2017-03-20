package com.wattics.vimsen.recommendation;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.MockGDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.ValidationMock;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Prosumer;

public class RegisteredRecommendationTest {

  @Test
  public void alwaysSendsRegisteredMessageIfNotSentYet() {
    MarketSignal ms = new MarketSignal();

    RegisteredRecommendation registeredRecom = new RegisteredRecommendation();
    boolean isTimeToSend = registeredRecom.isTimeToSendRecommendation(ms);

    Assert.assertTrue(isTimeToSend);
  }

  @Test
  public void recommendationNotSentYet() {
    DssSelectedProsumer dssSP = new DssSelectedProsumer();
    dssSP.setRecomRegisteredSent(false);

    RegisteredRecommendation registeredRecom = new RegisteredRecommendation();
    boolean notSentYet = registeredRecom.recommendationHasBeenSent(dssSP);

    Assert.assertFalse(notSentYet);
  }

  @Test
  public void updateWithRecommendationSent() {
    DssSelectedProsumer dssSP = new DssSelectedProsumer();
    dssSP.setRecomRegisteredSent(false);

    RegisteredRecommendation registeredRecom = new RegisteredRecommendation();
    DssSelectedProsumer updatedDss = registeredRecom.updateDssWithSentSentRecommendation(dssSP);

    Assert.assertTrue(updatedDss.getRecomRegisteredSent());
  }

  @Test
  public void generateRecommendationMessage() throws RecommendationException, DataAccessLayerException, NoValidDataException {
    MarketSignal ms = new MarketSignal();
    ms.setTimeInterval(900);
    ms.setStartTime(new DateTime("2016-09-10T12:00:00.000+01:00"));
    ms.setStartTimeText("2016-09-10T12:00:00.000+01:00");
    Prosumer prosumer = new Prosumer();
    ValidationMock dataGetter = new ValidationMock();

    RegisteredRecommendation registeredRecom = new RegisteredRecommendation();
    String recommendationMessage = registeredRecom.getRecommendationMessage(ms, prosumer, dataGetter);

    String expectedMessage = "You have been requested to deliver power surplus between 12:00 and 12:30 using equipment[ Equipment ]. Your equipment will be powered off during that period as follows:\n12:00 to 12:15: Equipment Equipment  OFF\n12:15 to 12:30: Equipment Equipment  OFF\n";
    Assert.assertEquals(recommendationMessage, expectedMessage);
  }

}
