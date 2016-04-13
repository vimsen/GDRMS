package com.wattics.vimsen.engine.manager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetter;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMEngine.PlanManager;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetter;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterNoHib;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorer;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataStorerInterface;
import com.wattics.vimsen.LDRMdatamanager.ControllerRuleSender;
import com.wattics.vimsen.LDRMdatamanager.ControllerRuleSenderInterface;
import com.wattics.vimsen.LDRMdatamanager.LDRMRuleException;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.utils.MapperException;

public class PlanRunnable implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(PlanRunnable.class);
  private ExecutorService streamHandlerPool;
  private final int sizePool = 5;

  private GDRMDataGetterInterface dataGetter;
  private GDRMDataStorerInterface dataStorer;
  private EDMSDataGetterInterface edmsDataGetter;
  private ControllerRuleSenderInterface ruleSender;

  public PlanRunnable(HibernateUtil hibernateUtil) {
    streamHandlerPool = Executors.newFixedThreadPool(sizePool);

    dataGetter = new GDRMDataGetter(hibernateUtil);
    dataStorer = new GDRMDataStorer(hibernateUtil);
    edmsDataGetter = new EDMSDataGetter();
    ruleSender = new ControllerRuleSender();
  }

  public void run() {
    logger.info("Worker start");
    try {

      retrieveAndProcessNewActions();
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

  public void retrieveAndProcessNewActions() throws InterruptedException, DataAccessLayerException {
    List<Plan> plans = null;
    List<Integer> plansId = null;

    try {
      plans = PlanManager.selectPlansInRegisteredStatusToBeProcessed(dataGetter, dataStorer, edmsDataGetter);
      logger.info("Numer of plans available to be processed " + plans.size());
      logger.info("Try to create a plan for plan id = " + plans.get(0).getId());
      PlanManager.generateAndStorePlansMap(plans, dataGetter, dataStorer, edmsDataGetter);
      logger.info("Plan created for plan id = " + plans.get(0).getId());
      plansId = GDRMDataGetterNoHib.getListPlansId(plans);
      List<String> rulesSent = PlanManager.generateAndSendControllerActionsMap(plansId, edmsDataGetter, dataGetter, ruleSender);
      logger.info("Rule created and sent to LDRM");
      for (String rule : rulesSent) {
        logger.info(rule);
        System.out.println("Processed plan id = " + plans.get(0).getId());
        System.out.println(rule);
      }
    } catch (EDMSDataGetterException e) {
      logger.error("Error in plan creation. LDRMRuleException: " + e);
      logger.info("Try to set plan status to invalid for plan id = " + plans.get(0).getId());
      PlanManager.setPlanStatusToInvalid(dataGetter, dataStorer, plans);
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
