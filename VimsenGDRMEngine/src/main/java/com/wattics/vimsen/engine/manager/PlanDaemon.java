package com.wattics.vimsen.engine.manager;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wattics.vimsen.dbDAO.HibernateUtil;

public class PlanDaemon implements Daemon {

  private static ScheduledThreadPoolExecutor scheduledThreadPool;
  private static final int poolSize = 5;
  private static final int rateExectutionInMInutes = 2;
  private static Logger logger = LoggerFactory.getLogger(PlanDaemon.class);
  private static String configurationFile = "hibernate.cfg.xml";
  // private static String testConfigurationFile = "schemaTestConfig.cfg.xml";
  // private static String localhostConftestFile ="localhostTestConfig.cfg.xml";
  private static HibernateUtil hibernateUtil;

  public static void main(String[] args) throws InterruptedException {
    logger.info("Set-up and start thread.");
    try {
      setupAndStartDaemon();
    } catch (Exception e) {
      logger.error("Could not set up and run Plan Daemon.", e);
    }
  }

  public static void setupAndStartDaemon() {
    scheduledThreadPool = new ScheduledThreadPoolExecutor(poolSize);

    logger.info("Start thread every " + rateExectutionInMInutes + " minutes.");

    hibernateUtil = new HibernateUtil(configurationFile);
    PlanRunnable worker = new PlanRunnable(hibernateUtil);
    scheduledThreadPool.scheduleAtFixedRate(worker, 0, rateExectutionInMInutes, TimeUnit.MINUTES); // to

    logger.info("End set up.");
  }

  /**
   * Methods used by jsvc on Linux to monitor the daemon
   * 
   * @param dc
   * @throws org.apache.commons.daemon.DaemonInitException
   */
  @Override
  public void init(DaemonContext dc) throws DaemonInitException {
    // do nothing
  }

  @Override
  public void start() throws Exception {
    setupAndStartDaemon();
  }

  @Override
  public void stop() throws Exception {
    scheduledThreadPool.shutdown();
  }

  @Override
  public void destroy() {
    hibernateUtil.closeSessionFactory();
  }

  /**
   * Methods used by prunsrv on Windows to monitor the service
   * 
   * @param args
   */
  public static void windowsService(String args[]) {
    String cmd = "start";
    if (args.length > 0) {
      cmd = args[0];
    }

    if ("start".equals(cmd)) {
      try {
        setupAndStartDaemon();
      } catch (Exception e) {
        logger.error("Unable to set up and start Plan creator, stopping the daemon.", e);
        scheduledThreadPool.shutdown();
      }
    } else {
      scheduledThreadPool.shutdown();
    }
  }
}
