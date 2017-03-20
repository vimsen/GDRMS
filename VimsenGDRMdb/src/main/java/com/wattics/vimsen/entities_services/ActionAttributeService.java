package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ActionAttribute;

public class ActionAttributeService extends ServicesAbstract{
	
	
	 public ActionAttributeService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ActionAttribute into the database.
	     * @param actionAttribute
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ActionAttribute actionAttribute) throws DataAccessLayerException  {
	        super.saveOrUpdate(actionAttribute);
	    }


	    /**
	     * Delete a detached ActionAttribute from the database.
	     * @param actionAttribute
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ActionAttribute actionAttribute) throws DataAccessLayerException  {
	        super.delete(actionAttribute);
	    }

	    /**
	     * Find an ActionAttribute by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ActionAttribute find(int id) throws DataAccessLayerException  {
	        return (ActionAttribute) super.find(ActionAttribute.class, id);
	    }

	    /**
	     * Updates the state of a detached ActionAttribute.
	     *
	     * @param actionAttribute
	     * @throws DataAccessLayerException 
	     */
	    public void update(ActionAttribute actionAttribute) throws DataAccessLayerException {
	        super.saveOrUpdate(actionAttribute);
	    }

	    /**
	     * Finds all ActionAttributes in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ActionAttribute.class);
	    }
	    

}

