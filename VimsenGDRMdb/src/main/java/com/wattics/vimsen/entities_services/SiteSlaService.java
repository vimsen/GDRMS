package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.SiteSla;

public class SiteSlaService  extends ServicesAbstract{
	
	
	 public SiteSlaService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new SiteSla into the database.
	     * @param siteSla
	     * @throws DataAccessLayerException 
	     */
	    public void insert(SiteSla siteSla) throws DataAccessLayerException  {
	        super.saveOrUpdate(siteSla);
	    }


	    /**
	     * Delete a detached SiteSla from the database.
	     * @param siteSla
	     * @throws DataAccessLayerException 
	     */
	    public void delete(SiteSla siteSla) throws DataAccessLayerException  {
	        super.delete(siteSla);
	    }

	    /**
	     * Find an SiteSla by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public SiteSla find(int id) throws DataAccessLayerException  {
	        return (SiteSla) super.find(SiteSla.class, id);
	    }

	    /**
	     * Updates the state of a detached SiteSla.
	     *
	     * @param siteSla
	     * @throws DataAccessLayerException 
	     */
	    public void update(SiteSla siteSla) throws DataAccessLayerException {
	        super.saveOrUpdate(siteSla);
	    }

	    /**
	     * Finds all SiteSlas in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(SiteSla.class);
	    }
	    

}
