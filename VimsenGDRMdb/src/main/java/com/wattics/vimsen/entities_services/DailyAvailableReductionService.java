package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.DailyAvailableReduction;
import com.wattics.vimsen.entities.DailyAvailableReductionId;

public class DailyAvailableReductionService extends ServicesAbstract{
	
	
	 public DailyAvailableReductionService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new DailyAvailableReduction into the database.
	     * @param dailyAvailableReduction
	     * @throws DataAccessLayerException 
	     */
	    public void insert(DailyAvailableReduction dailyAvailableReduction) throws DataAccessLayerException  {
	        super.saveOrUpdate(dailyAvailableReduction);
	    }


	    /**
	     * Delete a detached DailyAvailableReduction from the database.
	     * @param dailyAvailableReduction
	     * @throws DataAccessLayerException 
	     */
	    public void delete(DailyAvailableReduction dailyAvailableReduction) throws DataAccessLayerException  {
	        super.delete(dailyAvailableReduction);
	    }

	    /**
	     * Find an DailyAvailableReduction by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected DailyAvailableReduction findDailyAvailableReduction(DailyAvailableReductionId id) throws DataAccessLayerException {
			DailyAvailableReduction obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (DailyAvailableReduction)session.load(DailyAvailableReduction.class, id);
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
	     * Find DailyAvailableReduction by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public DailyAvailableReduction find(DailyAvailableReductionId id) throws DataAccessLayerException  {
	        return this.findDailyAvailableReduction(id);
	    }

	    /**
	     * Updates the state of a detached DailyAvailableReduction.
	     *
	     * @param dailyAvailableReduction
	     * @throws DataAccessLayerException 
	     */
	    public void update(DailyAvailableReduction dailyAvailableReduction) throws DataAccessLayerException {
	        super.saveOrUpdate(dailyAvailableReduction);
	    }

	    /**
	     * Finds all DailyAvailableReductions in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(DailyAvailableReduction.class);
	    }
	    

}
