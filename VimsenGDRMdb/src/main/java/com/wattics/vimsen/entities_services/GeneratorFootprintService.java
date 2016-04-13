package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.GeneratorFootprint;

public class GeneratorFootprintService extends ServicesAbstract{
	
	
	 public GeneratorFootprintService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new GeneratorFootprint into the database.
	     * @param generatorFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void insert(GeneratorFootprint generatorFootprint) throws DataAccessLayerException  {
	        super.saveOrUpdate(generatorFootprint);
	    }


	    /**
	     * Delete a detached GeneratorFootprint from the database.
	     * @param generatorFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void delete(GeneratorFootprint generatorFootprint) throws DataAccessLayerException  {
	        super.delete(generatorFootprint);
	    }

	    /**
	     * Find an GeneratorFootprint by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public GeneratorFootprint find(int id) throws DataAccessLayerException  {
	        return (GeneratorFootprint) super.find(GeneratorFootprint.class, id);
	    }

	    /**
	     * Updates the state of a detached GeneratorFootprint.
	     *
	     * @param generatorFootprint
	     * @throws DataAccessLayerException 
	     */
	    public void update(GeneratorFootprint generatorFootprint) throws DataAccessLayerException {
	        super.saveOrUpdate(generatorFootprint);
	    }

	    /**
	     * Finds all GeneratorFootprints in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(GeneratorFootprint.class);
	    }
	    

}

