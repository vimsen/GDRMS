package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.SiteConditionProfile;

public class SiteConditionProfileService extends ServicesAbstract{
	
	
	 public SiteConditionProfileService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new SiteConditionProfile into the database.
	     * @param siteConditionProfile
	     * @throws DataAccessLayerException 
	     */
	    public void insert(SiteConditionProfile siteConditionProfile) throws DataAccessLayerException  {
	        super.saveOrUpdate(siteConditionProfile);
	    }


	    /**
	     * Delete a detached SiteConditionProfile from the database.
	     * @param siteConditionProfile
	     * @throws DataAccessLayerException 
	     */
	    public void delete(SiteConditionProfile siteConditionProfile) throws DataAccessLayerException  {
	        super.delete(siteConditionProfile);
	    }

	    /**
	     * Find an SiteConditionProfile by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public SiteConditionProfile find(int id) throws DataAccessLayerException  {
	        return (SiteConditionProfile) super.find(SiteConditionProfile.class, id);
	    }

	    /**
	     * Updates the state of a detached SiteConditionProfile.
	     *
	     * @param siteConditionProfile
	     * @throws DataAccessLayerException 
	     */
	    public void update(SiteConditionProfile siteConditionProfile) throws DataAccessLayerException {
	        super.saveOrUpdate(siteConditionProfile);
	    }

	    /**
	     * Finds all SiteConditionProfiles in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(SiteConditionProfile.class);
	    }
	    

}

