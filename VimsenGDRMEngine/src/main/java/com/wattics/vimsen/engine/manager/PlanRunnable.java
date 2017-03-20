package com.wattics.vimsen.engine.manager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMEngine.PlanManager;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterAndValidationInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterNoHib;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMdatamanager.Validation;
import com.wattics.vimsen.LDRMdatamanager.ControllerRuleSenderInterface;
import com.wattics.vimsen.LDRMdatamanager.LDRMRuleException;
import com.wattics.vimsen.LDRMdatamanager.MqttRuleSender;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.recommendation.RecommendationException;
import com.wattics.vimsen.recommendation.RecommendationFactory;
import com.wattics.vimsen.utils.MapperException;

public class PlanRunnable implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(PlanRunnable.class);
  private ExecutorService streamHandlerPool;
  private final int sizePool = 5;

  private GDRMDataGetterAndValidationInterface dataGetter;
  private GDRMDataStorerInterface dataStorer;
  private EDMSDataGetterInterface edmsDataGetter;
  private ControllerRuleSenderInterface ruleSender;

  public PlanRunnable(HibernateUtil hibernateUtil) {
    streamHandlerPool = Executors.newFixedThreadPool(sizePool);

    dataGetter = new Validation(hibernateUtil);
    dataStorer = new GDRMDataStorer(hibernateUtil);
    edmsDataGetter = new EDMSDataGetter();
    // ruleSender = new ControllerRuleSender();
    ruleSender = new MqttRuleSender();
  }

  public void run() {
    logger.info("Worker start");
    try {
      logger.info("Update plans status");
      retrieveAndUpdatePlanStatus();
      logger.info("Create new plans");
      retrieveAndProcessNewActions();
      logger.info("Recommendation sender");
      sendRecommendations();
    } catch (InterruptedException e) {
      logger.error("Error in setting plan to invalid status. Interrupted exception: " + e);
      e.printStackTrace();
    } catch (DataAccessLayerException e) {
      logger.error("Error in setting plan to invalid status. DataAccessLayerException: " + e);
      e.printStackTrace();
    } catch (RuntimeException e) {
      logger.error("Error in setting plan to invalid status. RuntimeException: " + e);
      e.printStackTrace();
    }
    logger.info("Worker end");
  }

  private void sendRecommendations() {

    List<MarketSignal> marketSignalAll;
    try {
      // marketSignalAll = dataGetter.getMarketSignalWithinOneDay();
      marketSignalAll = dataGetter.getLastMarketSignal();

      if (marketSignalAll.size() > 0) {
        for (MarketSignal ms : marketSignalAll) {
          RecommendationFactory.sendRecommendationIfNecessary(ms, dataGetter, dataStorer);
        }
      }
    } catch (DataAccessLayerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RecommendationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoValidDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public void retrieveAndUpdatePlanStatus() {
    List<Plan> plans = null;

    try {
      plans = PlanManager.selectCreatedAndOngoingPlans(dataGetter);
      if (plans.size() > 0) {
        for (Plan plan : plans) {
          String planId = "" + plan.getId();
          PlanManager.updatePlanIfNotUpToDateMap(planId, dataGetter, dataStorer, edmsDataGetter);
        }
      }
    } catch (DataAccessLayerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (EDMSDataGetterException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoValidDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MapperException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void retrieveAndProcessNewActions() throws InterruptedException, DataAccessLayerException {
    List<Plan> plans = null;
    List<Integer> plansId = null;

    try {
      plans = PlanManager.selectPlansInRegisteredStatusToBeProcessed(dataGetter, dataStorer);
      logger.info("Numer of plans available to be processed " + plans.size());
      if (plans.size() > 0) {
        logger.info("Try to create a plan for plan id = " + plans.get(0).getId());
        PlanManager.generateAndStorePlansMap(plans, dataGetter, dataStorer);
        logger.info("Plan created for plan id = " + plans.get(0).getId());
        plansId = GDRMDataGetterNoHib.getListPlansId(plans);
        List<Pair<String, String>> ruleCreated = PlanManager.generateControllerActionsSla(plansId, dataGetter);
        List<String> ruleSent = PlanManager.sendControllerActionsMap(ruleSender, ruleCreated);
        logger.info("Rule created and sent to LDRM");
        for (Pair<String, String> rule : ruleCreated) {
          logger.info(rule.getRight());
          System.out.println("Processed plan id = " + plans.get(0).getId());
          System.out.println(rule);
        }
      }
    } catch (LDRMRuleException e) {
      logger.error("Error in plan creation. LDRMRuleException: " + e);
    } catch (MapperException e) {
      logger.error("Error in plan creation. MapperException: " + e);
    } catch (DataAccessLayerException e) {
      logger.error("Error in plan creation. DataAccessLayerException: " + e);
    } catch (RuntimeException e) {
      logger.error("Error in plan creation. RuntimeException: " + e);
    } catch (Exception e) {
      logger.error("Error in plan creation." + e);
    }

  }

}
