package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
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
import com.wattics.vimsen.entities.validator.ValidationCheck;
import com.wattics.vimsen.general.PlanStatusEnum;

public class Validation implements GDRMDataGetterAndValidationInterface {

  GDRMDataGetterInterface dataGetter;
  BasicDataGetter basicDataGetter;

  public Validation(HibernateUtil hibernateUtil) {
    this.dataGetter = new GDRMDataGetter(hibernateUtil);
    this.basicDataGetter = new BasicDataGetter(hibernateUtil);
  }

  @Override
  public List<Action> getActionsFromProsumer(Prosumer prosumer) throws DataAccessLayerException, NoValidDataException {
    List<Action> actions = basicDataGetter.getActionsFromProsumer(prosumer);
    return (List<Action>) (Object) ValidationCheck.returnValidListObject(actions);
  }

  @Override
  public DsoTerritory getDsoTerritory(int dsoTerritoryId) throws DataAccessLayerException, NoValidDataException {
    DsoTerritory dsoTerritory = basicDataGetter.getDsoTerritory(dsoTerritoryId);
    if (ValidationCheck.isObjectValid(dsoTerritory)) {
      return dsoTerritory;
    }
    throw new NoValidDataException("No valid DsoTerritory");
  }

  @Override
  public Plan getPlan(int planId) throws DataAccessLayerException, NoValidDataException {
    Plan plan = basicDataGetter.getPlan(planId);
    if (ValidationCheck.isObjectValid(plan)) {
      return plan;
    }
    throw new NoValidDataException("No valid DsoTerritory");
  }

  @Override
  public Prosumer getProsumerFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    Prosumer prosumer = basicDataGetter.getProsumerFromActionId(actionId);
    if (ValidationCheck.isObjectValid(prosumer)) {
      return prosumer;
    }
    throw new NoValidDataException("No valid prosumer");
  }

  @Override
  public Equipment getEquipmentFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    Equipment equipment = basicDataGetter.getEquipmentFromActionId(actionId);
    if (ValidationCheck.isObjectValid(equipment)) {
      return equipment;
    }
    throw new NoValidDataException("No valid equipment for the selected action");
  }

  @Override
  public ControlSignal getControlSignalFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    ControlSignal controlSignal = basicDataGetter.getControlSignalFromActionId(actionId);
    if (ValidationCheck.isObjectValid(controlSignal)) {
      return controlSignal;
    }
    throw new NoValidDataException("No valid control signal for the selected action");
  }

  @Override
  public List<Action> getActionsFromProsumers(List<Prosumer> prosumers) throws DataAccessLayerException, NoValidDataException {
    List<Action> actions = dataGetter.getActionsFromProsumers(prosumers);
    return (List<Action>) (Object) ValidationCheck.returnValidListObject(actions);
  }

  @Override
  public DssSelectedProsumer getDssFromProsumerAndMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException, NoValidDataException {
    DssSelectedProsumer dssSp = basicDataGetter.getDssFromProsumerAndMarketSignal(marketSignal, prosumer);
    if (ValidationCheck.isObjectValid(dssSp)) {
      return dssSp;
    }
    throw new NoValidDataException("No valid dssProsumer for the selected marketSignal and prosumer");
  }

  @Override
  public List<Prosumer> getProsumerWithActionToImplement(MarketSignal marketSignal)
      throws DataAccessLayerException, NoValidDataException {
    List<Prosumer> prosumers = dataGetter.getProsumerWithActionToImplement(marketSignal);
    return (List<Prosumer>) (Object) ValidationCheck.returnValidListObject(prosumers);
  }

  @Override
  public List<PlanHasAction> getActionsAssignedToAProsumerForAMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException, NoValidDataException {
    List<PlanHasAction> phas = dataGetter.getActionsAssignedToAProsumerForAMarketSignal(marketSignal, prosumer);
    return (List<PlanHasAction>) (Object) ValidationCheck.returnValidListObject(phas);
  }

  @Override
  public Prosumer getProsumerToImplementAction(PlanHasAction planHasAction)
      throws DataAccessLayerException, NoValidDataException {
    Prosumer prosumer = dataGetter.getProsumerToImplementAction(planHasAction);
    if (ValidationCheck.isObjectValid(prosumer)) {
      return prosumer;
    }
    throw new NoValidDataException("No valid prosumer for the action planned");
  }

  @Override
  public List<MarketSignal> getMarketSignalWithinOneDay() throws DataAccessLayerException, NoValidDataException {
    List<MarketSignal> marketSignals = dataGetter.getMarketSignalWithinOneDay();
    return (List<MarketSignal>) (Object) ValidationCheck.returnValidListObject(marketSignals);
  }

  @Override
  public List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal)
      throws DataAccessLayerException, NoValidDataException {
    List<Prosumer> prosumers = basicDataGetter.getPrimaryProsumersFromMarketSignal(marketSignal);
    return (List<Prosumer>) (Object) ValidationCheck.returnValidListObject(prosumers);
  }

  @Override
  public Prosumer getProsumerFromName(String prosumerName) throws DataAccessLayerException, NoValidDataException {
    Prosumer prosumer = basicDataGetter.getProsumerFromName(prosumerName);
    if (ValidationCheck.isObjectValid(prosumer)) {
      return prosumer;
    }
    throw new NoValidDataException("No valid prosumer for the selected name");
  }

  @Override
  public ActionSla getActionSlaFromActionId(int actionId) throws DataAccessLayerException, NoValidDataException {
    ActionSla actionSla = basicDataGetter.getActionSlaFromActionId(actionId);
    if (ValidationCheck.isObjectValid(actionSla)) {
      return actionSla;
    }
    throw new NoValidDataException("No valid prosumer for the selected name");
  }

  @Override
  public Equipment getEquipmentFromPlanHasAction(PlanHasAction planAction) throws DataAccessLayerException, NoValidDataException {
    Equipment equipment = dataGetter.getEquipmentFromPlanHasAction(planAction);
    if (ValidationCheck.isObjectValid(equipment)) {
      return equipment;
    }
    throw new NoValidDataException("No valid equipment for the action planned");
  }

  @Override
  public List<Integer> getListPlansId(List<Plan> plans) throws NoValidDataException {
    List<Integer> plansId = new ArrayList<Integer>();
    for (Plan plan : plans) {
      Integer planId = plan.getId();
      plansId.add(planId);
    }
    if (plansId.size() > 0) {
      return plansId;
    }
    throw new NoValidDataException("No valid plans ids");
  }

  @Override
  public List<Plan> getPlanInCreatedAndOngoingStatus() throws NoValidDataException, DataAccessLayerException {
    List<Plan> plans = new ArrayList<>();
    List<Plan> plansCreated = basicDataGetter.getPlansWithStatus(PlanStatusEnum.CREATED.toString());
    plans.addAll(plansCreated);
    List<Plan> plansOngoing = basicDataGetter.getPlansWithStatus(PlanStatusEnum.ONGOING.toString());
    plans.addAll(plansOngoing);
    return (List<Plan>) (Object) ValidationCheck.returnValidListObject(plans);
  }

  @Override
  public List<Plan> getPlanInRegisteredStatus() throws DataAccessLayerException, NoValidDataException {
    List<Plan> plans = basicDataGetter.getPlansWithStatus(PlanStatusEnum.REGISTERED.toString());
    return (List<Plan>) (Object) ValidationCheck.returnValidListObject(plans);
  }

  @Override
  public List<MarketSignal> getLastMarketSignal() throws DataAccessLayerException, NoValidDataException {
    List<MarketSignal> ms = basicDataGetter.getLastMarketSignal();
    return (List<MarketSignal>) (Object) ValidationCheck.returnValidListObject(ms);
  }

  @Override
  public List<Prosumer> getNotSelectedSecondaryProsumers(int msId) throws DataAccessLayerException, NoValidDataException {
    List<Prosumer> secondaryProsumers = basicDataGetter.getNotSelectedSecondaryProsumers(msId);
    return (List<Prosumer>) (Object) ValidationCheck.returnValidListObject(secondaryProsumers);
  }

}
