package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Transformer;

public class TransformerService  extends ServicesAbstract{
	
	
	 public TransformerService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Transformer into the database.
	     * @param transformer
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Transformer transformer) throws DataAccessLayerException  {
	        super.saveOrUpdate(transformer);
	    }


	    /**
	     * Delete a detached Transformer from the database.
	     * @param transformer
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Transformer transformer) throws DataAccessLayerException  {
	        super.delete(transformer);
	    }

	    /**
	     * Find an Transformer by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Transformer find(int id) throws DataAccessLayerException  {
	        return (Transformer) super.find(Transformer.class, id);
	    }

	    /**
	     * Updates the state of a detached Transformer.
	     *
	     * @param transformer
	     * @throws DataAccessLayerException 
	     */
	    public void update(Transformer transformer) throws DataAccessLayerException {
	        super.saveOrUpdate(transformer);
	    }

	    /**
	     * Finds all Transformers in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Transformer.class);
	    }
	    

}

