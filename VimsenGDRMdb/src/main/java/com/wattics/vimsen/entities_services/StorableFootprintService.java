package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.StorableFootprint;

public class StorableFootprintService  extends ServicesAbstract{
	
	
	 public StorableFootprintService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new StorableFootprint into the database.
	     * @param storableFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void insert(StorableFootprint storableFootprint) throws DataAccessLayerException  {
	        super.saveOrUpdate(storableFootprint);
	    }


	    /**
	     * Delete a detached StorableFootprint from the database.
	     * @param storableFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void delete(StorableFootprint storableFootprint) throws DataAccessLayerException  {
	        super.delete(storableFootprint);
	    }

	    /**
	     * Find an StorableFootprint by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public StorableFootprint find(int id) throws DataAccessLayerException  {
	        return (StorableFootprint) super.find(StorableFootprint.class, id);
	    }

	    /**
	     * Updates the state of a detached StorableFootprint.
	     *
	     * @param storableFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void update(StorableFootprint storableFootprint) throws DataAccessLayerException {
	        super.saveOrUpdate(storableFootprint);
	    }

	    /**
	     * Finds all StorableFootprints in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(StorableFootprint.class);
	    }
	    

}
