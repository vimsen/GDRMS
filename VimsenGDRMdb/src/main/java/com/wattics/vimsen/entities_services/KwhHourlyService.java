package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.KwhHourly;
import com.wattics.vimsen.entities.KwhHourlyId;

public class KwhHourlyService extends ServicesAbstract{
	
	
	 public KwhHourlyService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new KwhHourly into the database.
	     * @param kwhHourly
	     * @throws DataAccessLayerException 
	     */
	    public void insert(KwhHourly kwhHourly) throws DataAccessLayerException  {
	        super.saveOrUpdate(kwhHourly);
	    }


	    /**
	     * Delete a detached KwhHourly from the database.
	     * @param kwhHourly
	     * @throws DataAccessLayerException 
	     */
	    public void delete(KwhHourly kwhHourly) throws DataAccessLayerException  {
	        super.delete(kwhHourly);
	    }

	    /**
	     * Find an KwhHourly by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected KwhHourly findKwhHourly(KwhHourlyId id) throws DataAccessLayerException {
			KwhHourly obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (KwhHourly) session.load(KwhHourly.class, id);
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
		 * Find KwhHourly by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public KwhHourly find(KwhHourlyId id) throws DataAccessLayerException {
			return this.findKwhHourly(id);
		}


	    /**
	     * Updates the state of a detached KwhHourly.
	     *
	     * @param kwhHourly
	     * @throws DataAccessLayerException 
	     */
	    public void update(KwhHourly kwhHourly) throws DataAccessLayerException {
	        super.saveOrUpdate(kwhHourly);
	    }

	    /**
	     * Finds all KwhHourlys in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(KwhHourly.class);
	    }
	    

}


