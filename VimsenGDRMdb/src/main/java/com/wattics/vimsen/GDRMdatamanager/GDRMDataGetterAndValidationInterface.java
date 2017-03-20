package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.Site;

public interface GDRMDataGetterAndValidationInterface {

  public List<Action> getActionsFromProsumer(Prosumer prosumer) throws DataAccessLayerException, NoValidDataException;

  DsoTerritory getDsoTerritory(int dsoTerritoryId) throws DataAccessLayerException, NoValidDataException;

  Plan getPlan(int planId) throws DataAccessLayerException, NoValidDataException;

  Prosumer getProsumerFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException;

  DssSelectedProsumer getDssFromProsumerAndMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException, NoValidDataException;

  List<Prosumer> getProsumerWithActionToImplement(MarketSignal marketSignal)
      throws DataAccessLayerException, NoValidDataException;

  List<Integer> getListPlansId(List<Plan> plans) throws NoValidDataException;

  ActionSla getActionSlaFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException;

  List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal)
      throws DataAccessLayerException, NoValidDataException;

  List<MarketSignal> getMarketSignalWithinOneDay() throws DataAccessLayerException, NoValidDataException;

  List<PlanHasAction> getActionsAssignedToAProsumerForAMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException, NoValidDataException;

  Prosumer getProsumerToImplementAction(PlanHasAction planHasAction) throws DataAccessLayerException, NoValidDataException;

  List<Plan> getPlanInCreatedAndOngoingStatus() throws DataAccessLayerException, NoValidDataException;

  List<Plan> getPlanInRegisteredStatus() throws DataAccessLayerException, NoValidDataException;

  Equipment getEquipmentFromPlanHasAction(PlanHasAction planAction) throws DataAccessLayerException, NoValidDataException;

  Equipment getEquipmentFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException;

  ControlSignal getControlSignalFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException;

  List<Action> getActionsFromProsumers(List<Prosumer> prosumers) throws DataAccessLayerException, NoValidDataException;

  Prosumer getProsumerFromName(String prosumerName) throws DataAccessLayerException, NoValidDataException;

  public List<MarketSignal> getLastMarketSignal() throws DataAccessLayerException, NoValidDataException;

  public List<Prosumer> getNotSelectedSecondaryProsumers(int msId) throws DataAccessLayerException, NoValidDataException;

}
