package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.KwhBoundaries;
import com.wattics.vimsen.entities.KwhBoundariesId;

public class KwhBoundariesService extends ServicesAbstract{
	
	
	 public KwhBoundariesService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new KwhBoundaries into the database.
	     * @param kwhBoundaries
	     * @throws DataAccessLayerException 
	     */
	    public void insert(KwhBoundaries kwhBoundaries) throws DataAccessLayerException  {
	        super.saveOrUpdate(kwhBoundaries);
	    }


	    /**
	     * Delete a detached KwhBoundaries from the database.
	     * @param kwhBoundaries
	     * @throws DataAccessLayerException 
	     */
	    public void delete(KwhBoundaries kwhBoundaries) throws DataAccessLayerException  {
	        super.delete(kwhBoundaries);
	    }

	    /**
	     * Find an KwhBoundaries by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected KwhBoundaries findKwhBoundaries(KwhBoundariesId id) throws DataAccessLayerException {
			KwhBoundaries obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (KwhBoundaries) session.load(KwhBoundaries.class, id);
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
		 * Find KwhBoundaries by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public KwhBoundaries find(KwhBoundariesId id) throws DataAccessLayerException {
			return this.findKwhBoundaries(id);
		}


	    /**
	     * Updates the state of a detached KwhBoundaries.
	     *
	     * @param kwhBoundaries
	     * @throws DataAccessLayerException 
	     */
	    public void update(KwhBoundaries kwhBoundaries) throws DataAccessLayerException {
	        super.saveOrUpdate(kwhBoundaries);
	    }

	    /**
	     * Finds all KwhBoundariess in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(KwhBoundaries.class);
	    }
	    

}


