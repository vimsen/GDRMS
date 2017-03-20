package com.wattics.vimsen.recommendation;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Prosumer;

public class RecommendationFactory {

  static List<RecommendationType> activeRecommendations = Arrays.asList(RecommendationType.registered,
      RecommendationType.long_reminder, RecommendationType.short_reminder);

  public static void sendRecommendationIfNecessary(MarketSignal marketSignal, GDRMDataGetterAndValidationInterface dataGetter,
      GDRMDataStorerInterface dataStorer) throws DataAccessLayerException, RecommendationException, NoValidDataException {
    List<Prosumer> prosumers = dataGetter.getProsumerWithActionToImplement(marketSignal);
    for (RecommendationType activeRecomType : activeRecommendations) {
      RecommendationManager manager = getRecommendationManagerType(activeRecomType);
      sendRecommendationTypeIfNecessary(marketSignal, prosumers, manager, dataGetter, dataStorer);

    }

  }

  private static RecommendationManager getRecommendationManagerType(RecommendationType type) throws RecommendationException {
    switch (type) {
      case registered:
        return new RegisteredRecommendation();
      case long_reminder:
        return new LongReminderRecommendation();
      case short_reminder:
        return new ShortReminderRecommendation();
      default:
        throw new RecommendationException("Recommendation type not recognized: " + type.toString());
    }
  }

  private static void sendRecommendationTypeIfNecessary(MarketSignal marketSignal, List<Prosumer> prosumers,
      RecommendationManager manager, GDRMDataGetterAndValidationInterface dataGetter, GDRMDataStorerInterface dataStorer)
      throws DataAccessLayerException, RecommendationException, NoValidDataException {

    for (Prosumer prosumer : prosumers) {
      DssSelectedProsumer dssProsumer = dataGetter.getDssFromProsumerAndMarketSignal(marketSignal, prosumer);
      if (manager.hasToSendRecommendation(marketSignal, prosumer, dssProsumer)) {
        Pair<String, String> message = manager.generateRecommendation(marketSignal, prosumer, dataGetter);
        manager.sendRecommendation(message);
        manager.storeRecommendationSuccess(dssProsumer, dataStorer);

      }
    }
  }

}
