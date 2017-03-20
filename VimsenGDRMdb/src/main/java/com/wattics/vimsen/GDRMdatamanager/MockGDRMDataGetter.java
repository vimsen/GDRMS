package com.wattics.vimsen.GDRMdatamanager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.City;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.Country;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.KwhForecastId;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;
import com.wattics.vimsen.entities.Transformer;

public class MockGDRMDataGetter implements GDRMDataGetterInterface {

  public Site getSiteFromProsumer(Prosumer prosumer) {
    // Set<Site> sites = new HashSet<Site>();
    Site site = new Site();
    site.setId(1);
    return site;
    // Country country = new Country(1);
    // City city = new City(1, country);
    // site.setCity(city);
    // Transformer transformer = new Transformer();
    // transformer.setId(1);
    // site.setTransformer(transformer);
    // sites.add(site);
    // prosumer.setSites(sites);
    // List<Site> sitesList = new ArrayList<Site>(prosumer.getSites());
    // return sitesList.get(0);

  }

  public Site getSiteFromProsumerId(int prosumerId) {
    // Set<Site> prosumerSites = new HashSet<Site>();
    Site site = new Site();
    site.setId(1);
    return site;
    // Country country = new Country(1);
    // City city = new City(1, country);
    // site.setCity(city);
    // Transformer transformer = new Transformer();
    // transformer.setId(1);
    // site.setTransformer(transformer);
    // prosumerSites.add(site);
    // Prosumer prosumer = new Prosumer(prosumerId);
    // prosumer.setSites(prosumerSites);
    // List<Site> sitesList = new ArrayList<Site>(prosumer.getSites());
    // return sitesList.get(0);

  }

  public Equipment getEquipment(int id, String name, Site site) {
    Equipment equipment = new Equipment();
    equipment.setName(name);
    equipment.setId(id);
    equipment.setSite(site);
    return equipment;
  }

  public Kwh15mn getKwh15mn(Kwh15mnId id, Equipment eq) {
    // TODO Auto-generated method stub
    return null;
  }

  public SiteMetric getSiteMetric(Site prosumerSite) {
    SiteMetric siteMetric = new SiteMetric(prosumerSite);
    return siteMetric;
  }

  public KwhForecast getKwhForecast(KwhForecastId forecastId, Equipment equipment) {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Equipment> getEquipmentFromSite(Site site) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DsoTerritory getDsoTerritory(int dsoTerritoryId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Action> getActionsFromProsumerId(int prosumerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Plan getPlan(int planId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketSignal getMarketSignal(int marketSignalId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Action> getActionsFromProsumer(Prosumer prosumer) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Prosumer getProsumerFromActionId(int actionId) {
    Prosumer prosumer = new Prosumer(actionId);
    return prosumer;
  }

  @Override
  public List<Plan> getPlansWithStatus(String string) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public KwhForecast getKwhForecastFromProsumer(Prosumer prosumer, String prameterType, DateTime date)
      throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Equipment getEquipmentFromActionId(int actionId) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ControlSignal getControlSignalFromActionId(int actionId) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Action> getActionsFromProsumers(List<Prosumer> prosumers) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DssSelectedProsumer getDssFromProsumerAndMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Prosumer> getProsumerWithActionToImplement(MarketSignal marketSignal) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<PlanHasAction> getActionsAssignedToAProsumerForAMarketSignal(MarketSignal marketSignal, Prosumer prosumer)
      throws DataAccessLayerException {
    Equipment equipment = new Equipment();
    equipment.setName("Equipment");
    Action action = new Action();
    action.setEquipment(equipment);
    List<PlanHasAction> planActions = new ArrayList<>();

    PlanHasAction planAction = new PlanHasAction();
    planAction.setTStart(marketSignal.getStartTime());
    planAction.setPlannedAmount("[3.2,2.1]");
    planAction.setActualAmount("[2.5,1.3]");
    planAction.setAction(action);

    planActions.add(planAction);
    planActions.add(planAction);
    return planActions;
  }

  @Override
  public Prosumer getProsumerToImplementAction(PlanHasAction planHasAction) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<MarketSignal> getMarketSignalWithinOneDay() throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Prosumer> getPrimaryProsumersFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Plan> getPlansFromMarketSignal(MarketSignal marketSignal) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Prosumer getProsumerFromName(String prosumerName) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Equipment getEquipmentFromPlanHasAction(PlanHasAction planAction) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ActionSla getActionSlaFromActionId(int actionId) throws DataAccessLayerException {
    // TODO Auto-generated method stub
    return null;
  }

}
