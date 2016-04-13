package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;

import org.joda.time.DateTime;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.DsoTerritory;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.KwhForecastId;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.Site;
import com.wattics.vimsen.entities.SiteMetric;

public interface GDRMDataGetterInterface {

  public Site getSiteFromProsumerId(int prosumerId) throws DataAccessLayerException;

  public Equipment getEquipment(int id, String name, Site site) throws DataAccessLayerException;

  public SiteMetric getSiteMetric(Site prosumerSite) throws DataAccessLayerException;

  public DsoTerritory getDsoTerritory(int dsoTerritoryId) throws DataAccessLayerException;

  public List<Action> getActionsFromProsumerId(int prosumerId) throws DataAccessLayerException;

  public List<Action> getActionsFromProsumer(Prosumer prosumer) throws DataAccessLayerException;

  public Plan getPlan(int planId) throws DataAccessLayerException;

  public MarketSignal getMarketSignal(int marketSignalId) throws DataAccessLayerException;

  public Prosumer getProsumerFromActionId(int actionId) throws DataAccessLayerException;

  public List<Plan> getPlansWithStatus(String string) throws DataAccessLayerException;

  public KwhForecast getKwhForecastFromProsumer(Prosumer prosumer, String prameterType, DateTime date)
      throws DataAccessLayerException;

  public KwhForecast getKwhForecast(KwhForecastId forecastId, Equipment equipment) throws DataAccessLayerException;

  public Kwh15mn getKwh15mn(Kwh15mnId kwhId, Equipment equipment) throws DataAccessLayerException;

}
