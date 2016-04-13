package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ProsumerPreference;

public class ProsumerPreferenceService extends ServicesAbstract{
	
	
	 public ProsumerPreferenceService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ProsumerPreference into the database.
	     * @param prosumerPreference
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ProsumerPreference prosumerPreference) throws DataAccessLayerException  {
	        super.saveOrUpdate(prosumerPreference);
	    }


	    /**
	     * Delete a detached ProsumerPreference from the database.
	     * @param prosumerPreference
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ProsumerPreference prosumerPreference) throws DataAccessLayerException  {
	        super.delete(prosumerPreference);
	    }

	    /**
	     * Find an ProsumerPreference by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ProsumerPreference find(int id) throws DataAccessLayerException  {
	        return (ProsumerPreference) super.find(ProsumerPreference.class, id);
	    }

	    /**
	     * Updates the state of a detached ProsumerPreference.
	     *
	     * @param prosumerPreference
	     * @throws DataAccessLayerException 
	     */
	    public void update(ProsumerPreference prosumerPreference) throws DataAccessLayerException {
	        super.saveOrUpdate(prosumerPreference);
	    }

	    /**
	     * Finds all ProsumerPreferences in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ProsumerPreference.class);
	    }
	    

}


