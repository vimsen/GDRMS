package com.wattics.vimsen.GDRMdatamanager;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.css.CSSImportRule;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HQLServices;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities_services.DsoTerritoryService;
import com.wattics.vimsen.entities_services.PlanService;

public class BasicDataGetter {

  private HibernateUtil hibernateUtil;

  public BasicDataGetter(HibernateUtil hibernateUtil) {
    this.hibernateUtil = hibernateUtil;
  }

  public ActionSla getActionSlaFromActionId(int actionId) throws DataAccessLayerException {
    ActionSla actionSla = null;
    String query = "FROM ActionSla as a where a.actionId= '" + actionId + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<ActionSla> acrionSlas = hqlServices.selectWhereCondition(query);
    actionSla = acrionSlas.get(0);

    return actionSla;
  }

  public Prosumer getProsumerFromName(String prosumerName) throws DataAccessLayerException {
    Prosumer prosumer = null;
    String query = "FROM Prosumer as p where p.name= '" + prosumerName + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);
    prosumer = prosumers.get(0);
    return prosumer;
  }

  public List<Plan> getPlansFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    Integer msId = marketSignal.getId();
    String query = "FROM Plan where marketSignal.id=" + msId.toString();
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Plan> plans = hqlServices.selectWhereCondition(query);

    return plans;
  }

  @SuppressWarnings("unchecked")
  public DssSelectedProsumer getDssFromProsumerAndMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException {
    DssSelectedProsumer dssProsumer = null;
    int msId = marketSignal.getId();
    int pId = prosumer.getId();
    String query = "FROM DssSelectedProsumer as p where p.id.prosumerId= '" + pId + "' and p.id.marketSignalId= '" + msId + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<DssSelectedProsumer> dssProsumers = hqlServices.selectWhereCondition(query);
    if (dssProsumers.size() > 0) {
      dssProsumer = dssProsumers.get(0);
    }

    return dssProsumer;
  }

  public List<MarketSignal> getLastMarketSignal() throws DataAccessLayerException {
    String query = "FROM MarketSignal as m ORDER BY m.id desc";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<MarketSignal> ms = hqlServices.selectWhereCondition(query);
    if (ms.size() > 0) {
      MarketSignal lastMarketSignal = ms.get(0);
      List<MarketSignal> msLast = new ArrayList<>();
      msLast.add(lastMarketSignal);
      return msLast;
    } else {
      return ms;
    }
  }

  public DsoTerritory getDsoTerritory(int dsoTerritoryId) throws DataAccessLayerException {
    DsoTerritoryService dsoTerritoryService = new DsoTerritoryService(hibernateUtil);
    DsoTerritory dsoTerritory = dsoTerritoryService.find(dsoTerritoryId);
    return dsoTerritory;
  }

  public Plan getPlan(int planId) throws DataAccessLayerException {
    PlanService planService = new PlanService(hibernateUtil);
    Plan plan = planService.find(planId);
    return plan;
  }

  public List<Prosumer> getNotSelectedSecondaryProsumers(int msId) throws DataAccessLayerException {
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    String query = "FROM Prosumer pr where pr.id in (Select id.prosumerId FROM DssSelectedProsumer as p where p.id.marketSignalId= '"
        + msId + "' and p.isPrimary= '" + 0 + "')";

    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);
    return prosumers;
  }

  public Equipment getEquipmentFromActionId(int actionId) throws DataAccessLayerException {
    String query = "select e FROM Equipment  e  left join e.actions a where a.id = " + actionId;
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Equipment> equipment = hqlServices.selectWhereCondition(query);
    if (equipment.size() > 0) {
      return equipment.get(0);
    }
    return null;

  }

  public ControlSignal getControlSignalFromActionId(int actionId) throws DataAccessLayerException {
    ControlSignal cs = null;
    String query = "select cs FROM ControlSignal  cs  left join cs.actions a where a.id = = " + actionId;
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<ControlSignal> csignals = hqlServices.selectWhereCondition(query);
    if (csignals.size() > 0) {
      cs = csignals.get(0);
    }
    return cs;
  }

  public List<Plan> getPlansWithStatus(String planStatus) throws DataAccessLayerException {
    String query = "FROM Plan as p WHERE p.status= '" + planStatus + "'";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Plan> plans = hqlServices.selectWhereCondition(query);

    return plans;
  }

  public Prosumer getProsumerFromActionId(int actionId) throws DataAccessLayerException {
    Prosumer prosumer = null;
    String query = "select p FROM Prosumer  p  left join p.prosumerHasSites phs where phs.id.siteId in"
        + " (Select e.site.id from Equipment e where e.id in (" + "select a.equipment.id from Action a where a.id =" + actionId
        + " ))";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);
    if (prosumers.size() > 0) {
      prosumer = prosumers.get(0);
    }
    return prosumer;
  }

  public List<Action> getActionsFromProsumer(Prosumer prosumer) throws DataAccessLayerException {
    int prosumerId = prosumer.getId();
    String query = "select a from Action a where a.equipment.id in (" + "select e.id from Equipment e where e.site.id in ("
        + "select phs.id.siteId from ProsumerHasSite phs where phs.id.prosumerId = " + prosumerId + "))";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Action> actions = hqlServices.selectWhereCondition(query);
    return actions;
  }

  public List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    int marketSignalId = marketSignal.getId();
    String query = "select p from Prosumer p where p.id in (select dsp.id.prosumerId from DssSelectedProsumer dsp where dsp.id.marketSignalId = "
        + marketSignalId + " and dsp.isPrimary = 1)";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);
    return prosumers;
  }

}
