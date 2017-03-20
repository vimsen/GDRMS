package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Action;



public class ActionService extends ServicesAbstract{
	
	
	 public ActionService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Action into the database.
	     * @param action
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Action action) throws DataAccessLayerException  {
	        super.saveOrUpdate(action);
	    }


	    /**
	     * Delete a detached Action from the database.
	     * @param action
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Action action) throws DataAccessLayerException  {
	        super.delete(action);
	    }

	    /**
	     * Find an Action by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Action find(int id) throws DataAccessLayerException  {
	        return (Action) super.find(Action.class, id);
	    }

	    /**
	     * Updates the state of a detached Action.
	     *
	     * @param action
	     * @throws DataAccessLayerException 
	     */
	    public void update(Action action) throws DataAccessLayerException {
	        super.saveOrUpdate(action);
	    }

	    /**
	     * Finds all Actions in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Action.class);
	    }
	    

}

