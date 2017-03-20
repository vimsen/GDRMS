package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Equipment;

public class EquipmentService extends ServicesAbstract{
	
	
	 public EquipmentService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new Equipment into the database.
	     * @param equipment
	     * @throws DataAccessLayerException 
	     */
	    public void insert(Equipment equipment) throws DataAccessLayerException  {
	        super.saveOrUpdate(equipment);
	    }


	    /**
	     * Delete a detached Equipment from the database.
	     * @param equipment
	     * @throws DataAccessLayerException 
	     */
	    public void delete(Equipment equipment) throws DataAccessLayerException  {
	        super.delete(equipment);
	    }

	    /**
	     * Find an Equipment by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public Equipment find(int id) throws DataAccessLayerException  {
	        return (Equipment) super.find(Equipment.class, id);
	    }

	    /**
	     * Updates the state of a detached Equipment.
	     *
	     * @param equipment
	     * @throws DataAccessLayerException 
	     */
	    public void update(Equipment equipment) throws DataAccessLayerException {
	        super.saveOrUpdate(equipment);
	    }

	    /**
	     * Finds all Equipments in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(Equipment.class);
	    }
	    
	       /**
         * Find an Equipment by its primary key. Returns null if the element is not found
         * @param id
         * @return
	       * @throws DataAccessLayerException 
         */
	    public Equipment get(int id) throws DataAccessLayerException{
          return (Equipment) super.get(Equipment.class, id);
      }
	    

}

