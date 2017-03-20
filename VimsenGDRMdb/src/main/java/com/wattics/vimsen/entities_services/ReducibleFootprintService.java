package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ReducibleFootprint;

public class ReducibleFootprintService extends ServicesAbstract{
	
	
	 public ReducibleFootprintService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ReducibleFootprint into the database.
	     * @param reducibleFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ReducibleFootprint reducibleFootprint) throws DataAccessLayerException  {
	        super.saveOrUpdate(reducibleFootprint);
	    }


	    /**
	     * Delete a detached ReducibleFootprint from the database.
	     * @param reducibleFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ReducibleFootprint reducibleFootprint) throws DataAccessLayerException  {
	        super.delete(reducibleFootprint);
	    }

	    /**
	     * Find an ReducibleFootprint by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ReducibleFootprint find(int id) throws DataAccessLayerException  {
	        return (ReducibleFootprint) super.find(ReducibleFootprint.class, id);
	    }

	    /**
	     * Updates the state of a detached ReducibleFootprint.
	     *
	     * @param reducibleFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void update(ReducibleFootprint reducibleFootprint) throws DataAccessLayerException {
	        super.saveOrUpdate(reducibleFootprint);
	    }

	    /**
	     * Finds all ReducibleFootprints in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ReducibleFootprint.class);
	    }
	    

}


