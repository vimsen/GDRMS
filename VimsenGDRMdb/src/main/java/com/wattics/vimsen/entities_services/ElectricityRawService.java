package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ElectricityRaw;

public class ElectricityRawService extends ServicesAbstract{
	
	
	 public ElectricityRawService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ElectricityRaw into the database.
	     * @param electricityRaw
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ElectricityRaw electricityRaw) throws DataAccessLayerException  {
	        super.saveOrUpdate(electricityRaw);
	    }


	    /**
	     * Delete a detached ElectricityRaw from the database.
	     * @param electricityRaw
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ElectricityRaw electricityRaw) throws DataAccessLayerException  {
	        super.delete(electricityRaw);
	    }

	    /**
	     * Find an ElectricityRaw by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ElectricityRaw find(int id) throws DataAccessLayerException  {
	        return (ElectricityRaw) super.find(ElectricityRaw.class, id);
	    }

	    /**
	     * Updates the state of a detached ElectricityRaw.
	     *
	     * @param electricityRaw
	     * @throws DataAccessLayerException 
	     */
	    public void update(ElectricityRaw electricityRaw) throws DataAccessLayerException {
	        super.saveOrUpdate(electricityRaw);
	    }

	    /**
	     * Finds all ElectricityRaws in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ElectricityRaw.class);
	    }
	    

}


