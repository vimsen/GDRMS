package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.OperatingState;
import com.wattics.vimsen.entities.OperatingStateId;

public class OperatingStateService extends ServicesAbstract{
	
	
	 public OperatingStateService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new OperatingState into the database.
	     * @param operatingState
	     * @throws DataAccessLayerException 
	     */
	    public void insert(OperatingState operatingState) throws DataAccessLayerException  {
	        super.saveOrUpdate(operatingState);
	    }


	    /**
	     * Delete a detached OperatingState from the database.
	     * @param operatingState
	     * @throws DataAccessLayerException 
	     */
	    public void delete(OperatingState operatingState) throws DataAccessLayerException  {
	        super.delete(operatingState);
	    }

	    /**
	     * Find an OperatingState by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected OperatingState findOperatingState(OperatingStateId id) throws DataAccessLayerException {
			OperatingState obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (OperatingState) session.load(OperatingState.class, id);
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
		 * Find OperatingState by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public OperatingState find(OperatingStateId id) throws DataAccessLayerException {
			return this.findOperatingState(id);
		}


	    /**
	     * Updates the state of a detached OperatingState.
	     *
	     * @param operatingState
	     * @throws DataAccessLayerException 
	     */
	    public void update(OperatingState operatingState) throws DataAccessLayerException {
	        super.saveOrUpdate(operatingState);
	    }

	    /**
	     * Finds all OperatingStates in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(OperatingState.class);
	    }
	    

}

