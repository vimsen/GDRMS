package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.WeatherCondition;
import com.wattics.vimsen.entities.WeatherConditionId;

public class WeatherConditionService  extends ServicesAbstract{
	
	
	 public WeatherConditionService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new WeatherCondition into the database.
	     * @param weatherCondition
	     * @throws DataAccessLayerException 
	     */
	    public void insert(WeatherCondition weatherCondition) throws DataAccessLayerException  {
	        super.saveOrUpdate(weatherCondition);
	    }


	    /**
	     * Delete a detached WeatherCondition from the database.
	     * @param weatherCondition
	     * @throws DataAccessLayerException 
	     */
	    public void delete(WeatherCondition weatherCondition) throws DataAccessLayerException  {
	        super.delete(weatherCondition);
	    }

	    /**
	     * Find an WeatherCondition by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected WeatherCondition findWeatherCondition(WeatherConditionId id) throws DataAccessLayerException {
			WeatherCondition obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (WeatherCondition) session.load(WeatherCondition.class, id);
				super.tx.commit();
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
		 * Find WeatherCondition by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public WeatherCondition find(WeatherConditionId id) throws DataAccessLayerException {
			return this.findWeatherCondition(id);
		}


	    /**
	     * Updates the state of a detached WeatherCondition.
	     *
	     * @param weatherCondition
	     * @throws DataAccessLayerException 
	     */
	    public void update(WeatherCondition weatherCondition) throws DataAccessLayerException {
	        super.saveOrUpdate(weatherCondition);
	    }

	    /**
	     * Finds all WeatherConditions in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(WeatherCondition.class);
	    }
	    

}
