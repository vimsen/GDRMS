package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.PlanHasAction;
import com.wattics.vimsen.entities.PlanHasActionId;

public class PlanHasActionService extends ServicesAbstract{
	
	
	 public PlanHasActionService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new PlanHasAction into the database.
	     * @param planHasAction
	     * @throws DataAccessLayerException 
	     */
	    public void insert(PlanHasAction planHasAction) throws DataAccessLayerException  {
	        super.saveOrUpdate(planHasAction);
	    }


	    /**
	     * Delete a detached PlanHasAction from the database.
	     * @param planHasAction
	     * @throws DataAccessLayerException 
	     */
	    public void delete(PlanHasAction planHasAction) throws DataAccessLayerException  {
	        super.delete(planHasAction);
	    }

	    /**
	     * Find an PlanHasAction by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected PlanHasAction findPlanHasAction(PlanHasActionId id) throws DataAccessLayerException {
			PlanHasAction obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (PlanHasAction) session.load(PlanHasAction.class, id);
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
		 * Find PlanHasAction by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public PlanHasAction find(PlanHasActionId id) throws DataAccessLayerException {
			return this.findPlanHasAction(id);
		}


	    /**
	     * Updates the state of a detached PlanHasAction.
	     *
	     * @param planHasAction
	     * @throws DataAccessLayerException 
	     */
	    public void update(PlanHasAction planHasAction) throws DataAccessLayerException {
	        super.saveOrUpdate(planHasAction);
	    }

	    /**
	     * Finds all PlanHasActions in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
  public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(PlanHasAction.class);
	    }
	    

}

