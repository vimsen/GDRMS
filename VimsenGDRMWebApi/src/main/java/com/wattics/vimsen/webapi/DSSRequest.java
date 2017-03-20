package com.wattics.vimsen.webapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;
import com.wattics.vimsen.GDRMmanager.DSSManager;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.utils.MapperException;

/**
 * 603EH7JDEA68NS Servlet implementation class DSSRequest
 */
public class DSSRequest extends HttpServlet {
  private static Logger logger = LoggerFactory.getLogger(DSSRequest.class);
  private static final long serialVersionUID = 1L;
   String localhostConftestFile = "localhostTestConfig.cfg.xml";
  // String testConfigurationFile = "schemaTestConfig.cfg.xml";
//  private String configurationFile = "hibernate.cfg.xml";
  private HibernateUtil hibernateUtil;
  private DSSManager dssRequestManager;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DSSRequest() {
    super();
    hibernateUtil = new HibernateUtil(localhostConftestFile);
    this.dssRequestManager = new DSSManager(hibernateUtil);
  }

  /**
   * @see Servlet#init()
   */
  public void init() {

  }

  /**
   * @see Servlet#destroy()
   */
  public void destroy() {
    hibernateUtil.closeSessionFactory();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String planId = request.getParameter("plan_id");

    if (planId == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      String responseJson = null;
      try {
        logger.info("Get request for plan id " + planId);
        responseJson = dssRequestManager.getPlanCurrentStatusMap(planId);
        logger.info("Response to request for plan id " + planId + ": " + responseJson);
      } catch (MapperException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        logger.error("Could not map information for requested plan id " + planId + ". ", e);
      } catch (ObjectNotFoundException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Could not find objects for requested plan id " + planId + ". ", e);
      } catch (DataAccessLayerException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Error to access data in GDRM database for requested plan id " + planId + ". ", e);
      } catch (RuntimeException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Runtime error for requested plan id " + planId + ". ", e);
      } catch (NoValidDataException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Invalid data for requested plan id " + planId + ". ", e);
      }
      PrintWriter out = response.getWriter();
      out.println(responseJson);

    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String packetReceived;

    BufferedReader reader = request.getReader();

    if ((packetReceived = reader.readLine()) == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      String responseJson = null;
      try {
        logger.info("Received DSS post request: " + packetReceived);
        responseJson = dssRequestManager.storeDRRequestReturnAck(packetReceived);
        logger.info("Response to DSS post request: " + responseJson);
      } catch (MapperException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        logger.error("Could not map information for DSS post request :" + packetReceived, e);
      } catch (ObjectNotFoundException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Could not find objects for DSS post request :" + packetReceived, e);
      } catch (DataAccessLayerException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Error to access data in GDRM database for DSS post request :" + packetReceived, e);
      } catch (RuntimeException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Runtime error for DSS post request :" + packetReceived, e);
      } catch (NoValidDataException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        logger.error("Invalid data for DSS post request :" + packetReceived, e);
      }
      PrintWriter out = response.getWriter();
      out.println(responseJson);
    }
  }

}
