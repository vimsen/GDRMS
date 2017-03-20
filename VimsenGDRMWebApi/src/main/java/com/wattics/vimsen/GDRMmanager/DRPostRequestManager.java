package com.wattics.vimsen.GDRMmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wattics.vimen.DSSdata.DRRequest;
import com.wattics.vimen.DSSdata.DRRequestAck;
import com.wattics.vimen.DSSdata.DSSRequestsJsonMapper;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.DssSelectedProsumerId;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.webapi.DSSRequest;

public class DRPostRequestManager {

  private static Logger logger = LoggerFactory.getLogger(DSSRequest.class);

  public static String storeJsonDRRequestReturnJsonAck(GDRMDataStorerInterface gdrmStorer,
      GDRMDataGetterAndValidationInterface dataGetter, String jsonDSSRequest, DsoTerritory dsoTerritory)
      throws MapperException, DataAccessLayerException {
    MarketSignal marketSignal = storeJsonDRRequest(gdrmStorer, dataGetter, jsonDSSRequest, dsoTerritory);
    Plan plan = createAndStoreRegisteredPlan(gdrmStorer, marketSignal);
    DRRequestAck drRequestAck = getDRRequestAck(plan);
    String drAckJson = DSSRequestsJsonMapper.serializeDRRequestAck(drRequestAck);
    return drAckJson;
  }

  private static DRRequestAck getDRRequestAck(Plan plan) {
    DRRequestAck drRequestAck = new DRRequestAck();
    drRequestAck.status = plan.getStatus();
    drRequestAck.plan_id = plan.getId();
    return drRequestAck;
  }

  private static Plan createAndStoreRegisteredPlan(GDRMDataStorerInterface gdrmStorer, MarketSignal marketSignal)
      throws DataAccessLayerException {
    Plan plan = new Plan(marketSignal);
    plan.setStatus(PlanStatusEnum.REGISTERED.toString());
    gdrmStorer.storePlan(plan);
    return plan;
  }

  private static MarketSignal storeJsonDRRequest(GDRMDataStorerInterface gdrmStorer,
      GDRMDataGetterAndValidationInterface dataGetter, String jsonDRRequest, DsoTerritory dsoTerritory)
      throws MapperException, DataAccessLayerException {
    DRRequest drRequest = DSSRequestsJsonMapper.deserialiseDRRequest(jsonDRRequest);
    MarketSignal marketSignal = getMarketSignalInfoFromDRRequest(drRequest, dsoTerritory);
    gdrmStorer.storeMarketSignal(marketSignal);
    int marketSignalId = marketSignal.getId();
    logger.info("MarketSignal id: " + marketSignalId);
    List<DssSelectedProsumer> prosumersAvailable = getDSSSelectedProsumersFromDRRequest(drRequest, marketSignalId, dataGetter);
    for (DssSelectedProsumer prosumerAvailable : prosumersAvailable) {
      gdrmStorer.storeProsumersAvailableParticipate(prosumerAvailable);
    }
    return marketSignal;
  }

  private static MarketSignal getMarketSignalInfoFromDRRequest(DRRequest dssRequest, DsoTerritory dsoTerritory) {
    MarketSignal marketSignal = new MarketSignal();
    marketSignal.setStartTime(dssRequest.start_time);
    DateTime endTime = new DateTime(getEndDRTime(dssRequest));
    marketSignal.setEndTime(endTime);
    marketSignal.setStartTimeText(dssRequest.start_time.toString());
    marketSignal.setTimeInterval(dssRequest.interval);
    marketSignal.setUnit(dssRequest.unit);
    marketSignal.setType(dssRequest.type);
    String targetReduction = Arrays.toString(dssRequest.target_values);
    marketSignal.setAmountReduction(targetReduction);
    marketSignal.setDsoTerritory(dsoTerritory);

    return marketSignal;
  }

  private static long getEndDRTime(DRRequest dssRequest) {
    return dssRequest.start_time.getMillis() + 1000 * dssRequest.interval * dssRequest.target_values.length;
  }

  private static List<DssSelectedProsumer> getDSSSelectedProsumersFromDRRequest(DRRequest dssRequest, int marketSignalId,
      GDRMDataGetterAndValidationInterface dataGetter) throws DataAccessLayerException {
    List<DssSelectedProsumer> prosumersAvailable = new ArrayList<DssSelectedProsumer>();
    for (String prosumerName : dssRequest.prosumers_primary) {
      logger.info("ProsumerName: " + prosumerName);
      try {
        Prosumer prosumer = dataGetter.getProsumerFromName(prosumerName);

        int prosumerId = prosumer.getId();
        DssSelectedProsumerId prosumerAvailableId = new DssSelectedProsumerId(prosumerId, marketSignalId);
        DssSelectedProsumer prosumerAvailable = new DssSelectedProsumer();
        prosumerAvailable.setId(prosumerAvailableId);
        prosumerAvailable.setIsPrimary(true);
        prosumerAvailable.setRecomRegisteredSent(false);
        prosumerAvailable.setRecomReminderSent(false);
        prosumerAvailable.setRecomSummarySent(false);
        prosumerAvailable.setRecomLongReminderSent(false);
        prosumerAvailable.setIsSelected(false);
        prosumersAvailable.add(prosumerAvailable);
      } catch (NoValidDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    for (String prosumerName : dssRequest.prosumers_secondary) {
      try {
        Prosumer prosumer = dataGetter.getProsumerFromName(prosumerName);
        int prosumerId = prosumer.getId();
        DssSelectedProsumerId prosumerAvailableId = new DssSelectedProsumerId(prosumerId, marketSignalId);
        DssSelectedProsumer prosumerAvailable = new DssSelectedProsumer();
        prosumerAvailable.setId(prosumerAvailableId);
        prosumerAvailable.setIsPrimary(false);
        prosumerAvailable.setRecomRegisteredSent(false);
        prosumerAvailable.setRecomReminderSent(false);
        prosumerAvailable.setRecomSummarySent(false);
        prosumerAvailable.setRecomLongReminderSent(false);
        prosumerAvailable.setIsSelected(false);
        prosumersAvailable.add(prosumerAvailable);
      } catch (NoValidDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return prosumersAvailable;
  }
}
