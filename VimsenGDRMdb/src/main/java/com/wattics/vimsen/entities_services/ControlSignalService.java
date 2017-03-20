package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ControlSignal;

public class ControlSignalService extends ServicesAbstract{
	
	
	 public ControlSignalService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ControlSignal into the database.
	     * @param controlService
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ControlSignal controlService) throws DataAccessLayerException  {
	        super.saveOrUpdate(controlService);
	    }


	    /**
	     * Delete a detached ControlSignal from the database.
	     * @param controlService
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ControlSignal controlService) throws DataAccessLayerException  {
	        super.delete(controlService);
	    }

	    /**
	     * Find an ControlSignal by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ControlSignal find(int id) throws DataAccessLayerException  {
	        return (ControlSignal) super.find(ControlSignal.class, id);
	    }

	    /**
	     * Updates the state of a detached ControlSignal.
	     *
	     * @param controlService
	     * @throws DataAccessLayerException 
	     */
	    public void update(ControlSignal controlService) throws DataAccessLayerException {
	        super.saveOrUpdate(controlService);
	    }

	    /**
	     * Finds all ControlSignals in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ControlSignal.class);
	    }
	    

}
