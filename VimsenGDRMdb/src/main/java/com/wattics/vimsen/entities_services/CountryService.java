package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Country;

public class CountryService extends ServicesAbstract{
	
	
	 public CountryService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Country into the database.
	     * @param country
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Country country) throws DataAccessLayerException  {
	        super.saveOrUpdate(country);
	    }


	    /**
	     * Delete a detached Country from the database.
	     * @param country
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Country country) throws DataAccessLayerException  {
	        super.delete(country);
	    }

	    /**
	     * Find an Country by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Country find(int id) throws DataAccessLayerException  {
	        return (Country) super.find(Country.class, id);
	    }

	    /**
	     * Updates the state of a detached Country.
	     *
	     * @param country
	     * @throws DataAccessLayerException 
	     */
	    public void update(Country country) throws DataAccessLayerException {
	        super.saveOrUpdate(country);
	    }

	    /**
	     * Finds all Countrys in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Country.class);
	    }
	    

}

