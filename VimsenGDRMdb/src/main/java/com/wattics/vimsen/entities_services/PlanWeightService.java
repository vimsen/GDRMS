package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.PlanWeight;
import com.wattics.vimsen.entities.PlanWeightId;

public class PlanWeightService extends ServicesAbstract{
	
	
	 public PlanWeightService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new PlanWeight into the database.
	     * @param planWeight
	     * @throws DataAccessLayerException 
	     */
	    public void insert(PlanWeight planWeight) throws DataAccessLayerException  {
	        super.saveOrUpdate(planWeight);
	    }


	    /**
	     * Delete a detached PlanWeight from the database.
	     * @param planWeight
	     * @throws DataAccessLayerException 
	     */
	    public void delete(PlanWeight planWeight) throws DataAccessLayerException  {
	        super.delete(planWeight);
	    }

	    /**
	     * Find an PlanWeight by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
		protected PlanWeight findPlanWeight(PlanWeightId id) throws DataAccessLayerException {
			PlanWeight obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (PlanWeight) session.load(PlanWeight.class, id);
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
		 * Find PlanWeight by its primary key.
		 * 
		 * @param id
		 * @return
		 * @throws DataAccessLayerException 
		 */
		public PlanWeight find(PlanWeightId id) throws DataAccessLayerException {
			return this.findPlanWeight(id);
		}


	    /**
	     * Updates the state of a detached PlanWeight.
	     *
	     * @param planWeight
	     * @throws DataAccessLayerException 
	     */
	    public void update(PlanWeight planWeight) throws DataAccessLayerException {
	        super.saveOrUpdate(planWeight);
	    }

	    /**
	     * Finds all PlanWeights in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(PlanWeight.class);
	    }
	    

}


