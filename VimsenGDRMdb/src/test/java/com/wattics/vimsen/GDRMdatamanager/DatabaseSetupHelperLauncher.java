package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HQLServices;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities.ControlSignal;
import com.wattics.vimsen.entities.DssSelectedProsumer;
import com.wattics.vimsen.entities.Equipment;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.entities.Prosumer;
import com.wattics.vimsen.entities.ProsumerHasSite;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.ActionSlaService;
import com.wattics.vimsen.entities_services.MarketSignalService;
import com.wattics.vimsen.entities_services.PlanService;
import com.wattics.vimsen.general.PlanStatusEnum;
import com.wattics.vimsen.utils.TimeHelpers;
import com.wattics.vimsen.utils.VimsenTestUtil;

public class DatabaseSetupHelperLauncher {

  // static String testConfigurationFile = "schemaTestConfig.cfg.xml";
  static String configurationFile = "hibernate.cfg.xml";
  // static String localhostConftestFile = "localhostTestConfig.cfg.xml";
  private static HibernateUtil hibernateUtil = new HibernateUtil(configurationFile);

  @Test
  public void testHql() throws DataAccessLayerException {
    String query = "FROM Prosumer as p where p.id=1";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);

    for (Prosumer prosumer : prosumers) {
      System.out.println(prosumer.getName());
    }

  }

  @Test
  public void testTime() throws DataAccessLayerException, NoValidDataException {
    ActionService actionService = new ActionService(hibernateUtil);
    Action action = actionService.find(321);
    ActionSla actionSla = action.getActionSla();
    DateTime timeStart = actionSla.getStartResponsePeriod();
    System.out.println(timeStart);
  }

  @Test
  public void testToModifyDataGetter() throws DataAccessLayerException, NoValidDataException {
    // String query = "select a from Action a where a.equipment.id in (" +
    // "select e.id from Equipment e where e.site.id= 50)";
    // String query = "select p from Prosumer p where p.id in (select
    // dsp.id.prosumerId from DssSelectedProsumer dsp where
    // dsp.id.marketSignalId = 287 and dsp.isPrimary = 1)))";
    String query = "Select a from Action a where a.id in (select pha.id.action)";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);

    for (Prosumer prosumer : prosumers) {
      System.out.println(prosumer.getName());
    }
  }

  @Test
  public void test() throws DataAccessLayerException {
    int msId = 286;
    HQLServices hqlServices = new HQLServices(hibernateUtil);

    // String query = "Select id.prosumerId FROM DssSelectedProsumer as p where
    // p.id.marketSignalId= '" + msId
    // + "' and p.isPrimary= '" + 0 + "'";
    // List<Integer> dssProsumers = hqlServices.selectWhereCondition(query);
    //
    // for (Integer dssPr : dssProsumers) {
    // System.out.println(dssPr + " ");
    // }

    String query = "FROM Prosumer pr where pr.id in (Select id.prosumerId FROM DssSelectedProsumer as p where p.id.marketSignalId= '"
        + msId + "' and p.isPrimary= '" + 0 + "')";

    List<Prosumer> prosumers = hqlServices.selectWhereCondition(query);
    for (Prosumer prosumer : prosumers) {
      System.out.println(prosumer.getName() + " ");
    }
  }

  @Test
  public void testOneToMany() throws DataAccessLayerException {
    Integer msId = new Integer(1);
    String query = "FROM Equipment where site.id=1";
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    List<Equipment> plans = hqlServices.selectWhereCondition(query);

    for (Equipment plan : plans) {
      System.out.println(plan.getName());
    }

  }

  @Test
  public void testHqlManyToMany() throws DataAccessLayerException {
    // not working at the moment
    HQLServices hqlServices = new HQLServices(hibernateUtil);
    String query = "from Prosumer where prosumerHasSites.id=1";
    // String query = "FROM Prosumer as p where p.id in (SELECT
    // ProsumerHasSite.prosumerId FROM ProsumerHasSite where siteId=1)";
    List<ProsumerHasSite> prosumers = hqlServices.selectWhereCondition(query);

    for (ProsumerHasSite prosumer : prosumers) {
      System.out.println(prosumer.getProsumer().getName());
    }

  }

  // @Test
  // public void addActionSla() throws DataAccessLayerException {
  // DatabaseSetUpHelper.addActionSla(hibernateUtil);
  // }
  //
  // @Test
  // public void addMarketPlans() throws DataAccessLayerException {
  //
  // String startTimeText = "2016-01-10T16:00:00.000+02:00";
  // DateTime startTime =
  // TimeHelpers.createDateTimePreservingTimeZone(startTimeText);
  //
  // Double[] reduction = new Double[] { 10.0, 2.0 };
  // List<String> primaryProsumers = VimsenTestUtil.asList("Pr_1", "Pr_2",
  // "Pr_3", "Pr_4");
  // List<String> secondaryProsumers = VimsenTestUtil.asList("Pr_10", "Pr_11");
  // MarketSignal marketSignal = DatabaseSetUp.storeMarketSignal(hibernateUtil,
  // startTimeText, startTime, reduction,
  // primaryProsumers, secondaryProsumers);
  //
  // DatabaseSetUp.storePlan(hibernateUtil, marketSignal);
  // }
  //
  // @Test
  // public void populateDatabase() throws DataAccessLayerException {
  // int numberProsumers = 4;
  // DatabaseSetUp.populateDb(hibernateUtil, numberProsumers);
  // }
  //
  // @Test
  // public void cleanDatabase() throws DataAccessLayerException {
  // DatabaseSetUp.cleanDb(hibernateUtil);
  // }

}
