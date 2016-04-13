package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ActionMetric;

public class ActionMetricService  extends ServicesAbstract{
	
	
	 public ActionMetricService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ActionMetric into the database.
	     * @param actionMetric
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ActionMetric actionMetric) throws DataAccessLayerException  {
	        super.saveOrUpdate(actionMetric);
	    }


	    /**
	     * Delete a detached ActionMetric from the database.
	     * @param actionMetric
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ActionMetric actionMetric) throws DataAccessLayerException  {
	        super.delete(actionMetric);
	    }

	    /**
	     * Find an ActionMetric by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ActionMetric find(int id) throws DataAccessLayerException  {
	        return (ActionMetric) super.find(ActionMetric.class, id);
	    }

	    /**
	     * Updates the state of a detached ActionMetric.
	     *
	     * @param actionMetric
	     * @throws DataAccessLayerException 
	     */
	    public void update(ActionMetric actionMetric) throws DataAccessLayerException {
	        super.saveOrUpdate(actionMetric);
	    }

	    /**
	     * Finds all ActionMetrics in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ActionMetric.class);
	    }
	    

}


