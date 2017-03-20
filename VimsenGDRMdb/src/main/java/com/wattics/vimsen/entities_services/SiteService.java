package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Site;

public class SiteService extends ServicesAbstract{
	
	
	 public SiteService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Site into the database.
	     * @param site
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Site site) throws DataAccessLayerException  {
	        super.saveOrUpdate(site);
	    }


	    /**
	     * Delete a detached Site from the database.
	     * @param site
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Site site) throws DataAccessLayerException  {
	        super.delete(site);
	    }

	    /**
	     * Find an Site by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Site find(int id) throws DataAccessLayerException  {
	        return (Site) super.find(Site.class, id);
	    }

	    /**
	     * Updates the state of a detached Site.
	     *
	     * @param site
	     * @throws DataAccessLayerException 
	     */
	    public void update(Site site) throws DataAccessLayerException {
	        super.saveOrUpdate(site);
	    }

	    /**
	     * Finds all Sites in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
  public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Site.class);
	    }
	    

}

