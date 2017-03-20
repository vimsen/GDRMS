package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
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
import com.wattics.vimsen.entities.PlanHasActionId;
import com.wattics.vimsen.entities.Prosumer;

public class ValidationMock implements GDRMDataGetterAndValidationInterface {

  @Override
  public List<Action> getActionsFromProsumer(Prosumer prosumer) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DsoTerritory getDsoTerritory(int dsoTerritoryId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Plan getPlan(int planId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Prosumer getProsumerFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DssSelectedProsumer getDssFromProsumerAndMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Prosumer> getProsumerWithActionToImplement(MarketSignal marketSignal)
      throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Integer> getListPlansId(List<Plan> plans) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ActionSla getActionSlaFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal)
      throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<MarketSignal> getMarketSignalWithinOneDay() throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<PlanHasAction> getActionsAssignedToAProsumerForAMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException, NoValidDataException {
    List<PlanHasAction> pha = new ArrayList<>();
    Plan plan = new Plan(marketSignal);
    plan.setId(1);
    Action action = new Action();
    action.setId(1);
    for (int i = 0; i < 2; i++) {
      PlanHasActionId planHasActionId = new PlanHasActionId(plan.getId(), action.getId());
      PlanHasAction planAction = new PlanHasAction();
      planAction.setId(planHasActionId);
      planAction.setPlan(plan);
      planAction.setAction(action);
      planAction.setPlannedAmount("[2.0,5.0]");
      planAction.setTStart(marketSignal.getStartTime());
      planAction.setAmountRecProgressToSend(1);
      planAction.setAmountRecProgressToSend(0);
      pha.add(planAction);
    }
    return pha;
  }

  @Override
  public Prosumer getProsumerToImplementAction(PlanHasAction planHasAction)
      throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Plan> getPlanInCreatedAndOngoingStatus() throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Plan> getPlanInRegisteredStatus() throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Equipment getEquipmentFromPlanHasAction(PlanHasAction planAction) throws DataAccessLayerException, NoValidDataException {
    return planAction.getAction().getEquipment();
  }

  private Equipment getDefaultEquipment() {
    Equipment e = new Equipment();
    e.setName("consumption");
    e.setCategory("consumption");
    return e;
  }

  @Override
  public Equipment getEquipmentFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ControlSignal getControlSignalFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Action> getActionsFromProsumers(List<Prosumer> prosumers) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Prosumer getProsumerFromName(String prosumerName) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<MarketSignal> getLastMarketSignal() throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Prosumer> getNotSelectedSecondaryProsumers(int msId) throws DataAccessLayerException, NoValidDataException {
    // TODO Auto-generated method stub
    return null;
  }

}
