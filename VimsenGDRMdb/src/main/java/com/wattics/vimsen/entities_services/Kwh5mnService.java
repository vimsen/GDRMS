package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Kwh5mn;
import com.wattics.vimsen.entities.Kwh5mnId;

public class Kwh5mnService extends ServicesAbstract{
	
	
	 public Kwh5mnService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Kwh5mn into the database.
	     * @param kwh5mn
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Kwh5mn kwh5mn) throws DataAccessLayerException  {
	        super.saveOrUpdate(kwh5mn);
	    }


	    /**
	     * Delete a detached Kwh5mn from the database.
	     * @param kwh5mn
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Kwh5mn kwh5mn) throws DataAccessLayerException  {
	        super.delete(kwh5mn);
	    }

	    /**
	     * Find an Kwh5mn by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected Kwh5mn findKwh5mn(Kwh5mnId id) throws DataAccessLayerException {
			Kwh5mn obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (Kwh5mn) session.load(Kwh5mn.class, id);
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
		 * Find Kwh5mn by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public Kwh5mn find(Kwh5mnId id) throws DataAccessLayerException {
			return this.findKwh5mn(id);
		}


	    /**
	     * Updates the state of a detached Kwh5mn.
	     *
	     * @param kwh5mn
	     * @throws DataAccessLayerException 
	     */
	    public void update(Kwh5mn kwh5mn) throws DataAccessLayerException {
	        super.saveOrUpdate(kwh5mn);
	    }

	    /**
	     * Finds all Kwh5mns in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Kwh5mn.class);
	    }
	    

}


