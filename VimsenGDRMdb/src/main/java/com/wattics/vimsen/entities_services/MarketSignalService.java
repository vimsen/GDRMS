package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.MarketSignal;

public class MarketSignalService extends ServicesAbstract{
	
	
	 public MarketSignalService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new MarketSignal into the database.
	     * @param marketSignal
	     * @throws DataAccessLayerException 
	     */
	    public void insert(MarketSignal marketSignal) throws DataAccessLayerException  {
	        super.saveOrUpdate(marketSignal);
	    }


	    /**
	     * Delete a detached MarketSignal from the database.
	     * @param marketSignal
	     * @throws DataAccessLayerException 
	     */
	    public void delete(MarketSignal marketSignal) throws DataAccessLayerException  {
	        super.delete(marketSignal);
	    }

	    /**
	     * Find an MarketSignal by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public MarketSignal find(int id) throws DataAccessLayerException  {
	        return (MarketSignal) super.find(MarketSignal.class, id);
	    }

	    /**
	     * Updates the state of a detached MarketSignal.
	     *
	     * @param marketSignal
	     * @throws DataAccessLayerException 
	     */
	    public void update(MarketSignal marketSignal) throws DataAccessLayerException {
	        super.saveOrUpdate(marketSignal);
	    }

	    /**
	     * Finds all MarketSignals in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(MarketSignal.class);
	    }
	    

}


