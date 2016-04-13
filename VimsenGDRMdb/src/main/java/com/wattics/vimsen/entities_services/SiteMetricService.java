package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.SiteMetric;

public class SiteMetricService extends ServicesAbstract {

  public SiteMetricService(HibernateUtil hibernateUtil) {
    super(hibernateUtil);
  }

  /**
   * Insert a new SiteMetric into the database.
   * 
   * @param siteMetric
   * @throws DataAccessLayerException 
   */
  public void insert(SiteMetric siteMetric) throws DataAccessLayerException {
    super.saveOrUpdate(siteMetric);
  }

  /**
   * Delete a detached SiteMetric from the database.
   * 
   * @param siteMetric
   * @throws DataAccessLayerException 
   */
  public void delete(SiteMetric siteMetric) throws DataAccessLayerException {
    super.delete(siteMetric);
  }

  /**
   * Find an SiteMetric by its primary key.
   * 
   * @param id
   * @return
   * @throws DataAccessLayerException 
   */
  public SiteMetric find(int id) throws DataAccessLayerException {
    SiteMetric obj = null;
    Session session = null;

    try {
      session = super.hibernateUtil.openSession();
      super.tx = session.beginTransaction();
      obj = (SiteMetric) session.load(SiteMetric.class, id);
      super.tx.commit();
    } catch (ObjectNotFoundException e) {
      hanldeObjectNotFound(e);

    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }
    }

    return obj;
  }

  /**
   * Updates the state of a detached SiteMetric.
   *
   * @param siteMetric
   * @throws DataAccessLayerException 
   */
  public void update(SiteMetric siteMetric) throws DataAccessLayerException {
    super.saveOrUpdate(siteMetric);
  }

  /**
   * Finds all SiteMetrics in the database.
   * 
   * @return
   * @throws DataAccessLayerException 
   */

  public List<?> findAll() throws DataAccessLayerException {
    return super.findAll(SiteMetric.class);
  }

}
