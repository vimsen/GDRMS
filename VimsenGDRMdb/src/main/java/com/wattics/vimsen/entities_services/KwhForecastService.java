package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.KwhForecast;
import com.wattics.vimsen.entities.KwhForecastId;

public class KwhForecastService extends ServicesAbstract {

  public KwhForecastService(HibernateUtil hibernateUtil) {
    super(hibernateUtil);
  }

  /**
   * Insert a new KwhForecast into the database.
   * 
   * @param kwhForecast
   * @throws DataAccessLayerException 
   */
  public void insert(KwhForecast kwhForecast) throws DataAccessLayerException {
    super.saveOrUpdate(kwhForecast);
  }

  /**
   * Delete a detached KwhForecast from the database.
   * 
   * @param kwhForecast
   * @throws DataAccessLayerException 
   */
  public void delete(KwhForecast kwhForecast) throws DataAccessLayerException {
    super.delete(kwhForecast);
  }

  /**
   * Find an KwhForecast by its primary key.
   * 
   * @param id
   * @return
   * @throws DataAccessLayerException 
   */
  protected KwhForecast findKwhForecast(KwhForecastId id) throws DataAccessLayerException {
    KwhForecast obj = null;
    Session session = null;

    try {
      session = super.hibernateUtil.openSession();
      super.tx = session.beginTransaction();
      obj = (KwhForecast) session.load(KwhForecast.class, id);
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
   * Find KwhForecast by its primary key.
   * 
   * @param id
   * @return
   * @throws DataAccessLayerException 
   */
  public KwhForecast find(KwhForecastId id) throws DataAccessLayerException {
    return this.findKwhForecast(id);
  }

  /**
   * Updates the state of a detached KwhForecast.
   *
   * @param kwhForecast
   * @throws DataAccessLayerException 
   */
  public void update(KwhForecast kwhForecast) throws DataAccessLayerException {
    super.saveOrUpdate(kwhForecast);
  }

  /**
   * Finds all KwhForecasts in the database.
   * 
   * @return
   * @throws DataAccessLayerException 
   */

  public List<?> findAll() throws DataAccessLayerException {
    return super.findAll(KwhForecast.class);
  }

}
