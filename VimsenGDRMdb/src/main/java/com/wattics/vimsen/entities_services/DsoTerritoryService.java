package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.DsoTerritory;

public class DsoTerritoryService extends ServicesAbstract{
	
	
	 public DsoTerritoryService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new DsoTerritory into the database.
	     * @param dsoTerritory
	     * @throws DataAccessLayerException 
	     */
	    public void insert(DsoTerritory dsoTerritory) throws DataAccessLayerException  {
	        super.saveOrUpdate(dsoTerritory);
	    }


	    /**
	     * Delete a detached DsoTerritory from the database.
	     * @param dsoTerritory
	     * @throws DataAccessLayerException 
	     */
	    public void delete(DsoTerritory dsoTerritory) throws DataAccessLayerException  {
	        super.delete(dsoTerritory);
	    }

	    /**
	     * Find an DsoTerritory by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public DsoTerritory find(int id) throws DataAccessLayerException  {
	        return (DsoTerritory) super.find(DsoTerritory.class, id);
	    }

	    /**
	     * Updates the state of a detached DsoTerritory.
	     *
	     * @param dsoTerritory
	     * @throws DataAccessLayerException 
	     */
	    public void update(DsoTerritory dsoTerritory) throws DataAccessLayerException {
	        super.saveOrUpdate(dsoTerritory);
	    }

	    /**
	     * Finds all DsoTerritorys in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(DsoTerritory.class);
	    }
	    

}
