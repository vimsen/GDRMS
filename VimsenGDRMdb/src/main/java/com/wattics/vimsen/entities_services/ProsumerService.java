package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.*;
import com.wattics.vimsen.entities.Prosumer;

public class ProsumerService extends ServicesAbstract{
	
	
	 public ProsumerService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Prosumer into the database.
	     * @param prosumer
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Prosumer prosumer) throws DataAccessLayerException  {
	        super.saveOrUpdate(prosumer);
	    }


	    /**
	     * Delete a detached Prosumer from the database.
	     * @param prosumer
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Prosumer prosumer) throws DataAccessLayerException  {
	        super.delete(prosumer);
	    }

	    /**
	     * Find an Prosumer by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Prosumer find(int id) throws DataAccessLayerException  {
	        return (Prosumer) super.find(Prosumer.class, id);
	    }

	    /**
	     * Updates the state of a detached Prosumer.
	     *
	     * @param prosumer
	     * @throws DataAccessLayerException 
	     */
	    public void update(Prosumer prosumer) throws DataAccessLayerException {
	        super.saveOrUpdate(prosumer);
	    }

	    /**
	     * Finds all Prosumers in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Prosumer.class);
	    }
	    

}
