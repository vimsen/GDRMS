package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.SiteConditionRaw;

public class SiteConditionRawService extends ServicesAbstract{
	
	
	 public SiteConditionRawService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new SiteConditionRaw into the database.
	     * @param siteConditionRaw
	     * @throws DataAccessLayerException 
	     */
	    public void insert(SiteConditionRaw siteConditionRaw) throws DataAccessLayerException  {
	        super.saveOrUpdate(siteConditionRaw);
	    }


	    /**
	     * Delete a detached SiteConditionRaw from the database.
	     * @param siteConditionRaw
	     * @throws DataAccessLayerException 
	     */
	    public void delete(SiteConditionRaw siteConditionRaw) throws DataAccessLayerException  {
	        super.delete(siteConditionRaw);
	    }

	    /**
	     * Find an SiteConditionRaw by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public SiteConditionRaw find(int id) throws DataAccessLayerException  {
	        return (SiteConditionRaw) super.find(SiteConditionRaw.class, id);
	    }

	    /**
	     * Updates the state of a detached SiteConditionRaw.
	     *
	     * @param siteConditionRaw
	     * @throws DataAccessLayerException 
	     */
	    public void update(SiteConditionRaw siteConditionRaw) throws DataAccessLayerException {
	        super.saveOrUpdate(siteConditionRaw);
	    }

	    /**
	     * Finds all SiteConditionRaws in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(SiteConditionRaw.class);
	    }
	    

}


