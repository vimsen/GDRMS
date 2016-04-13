package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.EquipmentConditionRaw;

public class EquipmentConditionRawService extends ServicesAbstract{
	
	
	 public EquipmentConditionRawService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new EquipmentConditionRaw into the database.
	     * @param equipmentConditionRaw
	     * @throws DataAccessLayerException 
	     */
	    public void insert(EquipmentConditionRaw equipmentConditionRaw) throws DataAccessLayerException  {
	        super.saveOrUpdate(equipmentConditionRaw);
	    }


	    /**
	     * Delete a detached EquipmentConditionRaw from the database.
	     * @param equipmentConditionRaw
	     * @throws DataAccessLayerException 
	     */
	    public void delete(EquipmentConditionRaw equipmentConditionRaw) throws DataAccessLayerException  {
	        super.delete(equipmentConditionRaw);
	    }

	    /**
	     * Find an EquipmentConditionRaw by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public EquipmentConditionRaw find(int id) throws DataAccessLayerException  {
	        return (EquipmentConditionRaw) super.find(EquipmentConditionRaw.class, id);
	    }

	    /**
	     * Updates the state of a detached EquipmentConditionRaw.
	     *
	     * @param equipmentConditionRaw
	     * @throws DataAccessLayerException 
	     */
	    public void update(EquipmentConditionRaw equipmentConditionRaw) throws DataAccessLayerException {
	        super.saveOrUpdate(equipmentConditionRaw);
	    }

	    /**
	     * Finds all EquipmentConditionRaws in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(EquipmentConditionRaw.class);
	    }
	    

}


