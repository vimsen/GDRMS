package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Plan;

public class PlanService extends ServicesAbstract{
	
	
	 public PlanService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Plan into the database.
	     * @param plan
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Plan plan) throws DataAccessLayerException  {
	        super.saveOrUpdate(plan);
	    }


	    /**
	     * Delete a detached Plan from the database.
	     * @param plan
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Plan plan) throws DataAccessLayerException  {
	        super.delete(plan);
	    }

	    /**
	     * Find an Plan by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Plan find(int id) throws DataAccessLayerException  {
	        return (Plan) super.find(Plan.class, id);
	    }

	    /**
	     * Updates the state of a detached Plan.
	     *
	     * @param plan
	     * @throws DataAccessLayerException 
	     */
	    public void update(Plan plan) throws DataAccessLayerException {
	        super.saveOrUpdate(plan);
	    }

	    /**
	     * Finds all Plans in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Plan.class);
	    }
	    

}


