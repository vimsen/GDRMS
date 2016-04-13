package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ActionSla;

public class ActionSlaService extends ServicesAbstract{
	
	
	 public ActionSlaService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ActionSla into the database.
	     * @param actionSla
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ActionSla actionSla) throws DataAccessLayerException  {
	        super.saveOrUpdate(actionSla);
	    }


	    /**
	     * Delete a detached ActionSla from the database.
	     * @param actionSla
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ActionSla actionSla) throws DataAccessLayerException  {
	        super.delete(actionSla);
	    }

	    /**
	     * Find an ActionSla by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ActionSla find(int id) throws DataAccessLayerException  {
	        return (ActionSla) super.find(ActionSla.class, id);
	    }

	    /**
	     * Updates the state of a detached ActionSla.
	     *
	     * @param actionSla
	     * @throws DataAccessLayerException 
	     */
	    public void update(ActionSla actionSla) throws DataAccessLayerException {
	        super.saveOrUpdate(actionSla);
	    }

	    /**
	     * Finds all ActionSlas in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ActionSla.class);
	    }
	    

}