package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.City;

public class CityService extends ServicesAbstract{
	
	
	 public CityService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new City into the database.
	     * @param city
	     * @throws DataAccessLayerException 
	     */
	    public void insert(City city) throws DataAccessLayerException  {
	        super.saveOrUpdate(city);
	    }


	    /**
	     * Delete a detached City from the database.
	     * @param city
	     * @throws DataAccessLayerException 
	     */
	    public void delete(City city) throws DataAccessLayerException  {
	        super.delete(city);
	    }

	    /**
	     * Find an City by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public City find(int id) throws DataAccessLayerException  {
	        return (City) super.find(City.class, id);
	    }

	    /**
	     * Updates the state of a detached City.
	     *
	     * @param city
	     * @throws DataAccessLayerException 
	     */
	    public void update(City city) throws DataAccessLayerException {
	        super.saveOrUpdate(city);
	    }

	    /**
	     * Finds all Citys in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(City.class);
	    }
	    

}
