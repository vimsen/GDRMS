package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ReschedulableFootprint;

public class ReschedulableFootprintService extends ServicesAbstract{
	
	
	 public ReschedulableFootprintService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ReschedulableFootprint into the database.
	     * @param reschedulableFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ReschedulableFootprint reschedulableFootprint) throws DataAccessLayerException  {
	        super.saveOrUpdate(reschedulableFootprint);
	    }


	    /**
	     * Delete a detached ReschedulableFootprint from the database.
	     * @param reschedulableFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ReschedulableFootprint reschedulableFootprint) throws DataAccessLayerException  {
	        super.delete(reschedulableFootprint);
	    }

	    /**
	     * Find an ReschedulableFootprint by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ReschedulableFootprint find(int id) throws DataAccessLayerException  {
	        return (ReschedulableFootprint) super.find(ReschedulableFootprint.class, id);
	    }

	    /**
	     * Updates the state of a detached ReschedulableFootprint.
	     *
	     * @param reschedulableFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void update(ReschedulableFootprint reschedulableFootprint) throws DataAccessLayerException {
	        super.saveOrUpdate(reschedulableFootprint);
	    }

	    /**
	     * Finds all ReschedulableFootprints in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ReschedulableFootprint.class);
	    }
	    

}


