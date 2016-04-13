package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.EquipmentConditionProfile;

public class EquipmentConditionProfileService extends ServicesAbstract{
	
	
	 public EquipmentConditionProfileService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new EquipmentConditionProfile into the database.
	     * @param equipmentConditionProfile
	     * @throws DataAccessLayerException 
	     */
	    public void insert(EquipmentConditionProfile equipmentConditionProfile) throws DataAccessLayerException  {
	        super.saveOrUpdate(equipmentConditionProfile);
	    }


	    /**
	     * Delete a detached EquipmentConditionProfile from the database.
	     * @param equipmentConditionProfile
	     * @throws DataAccessLayerException 
	     */
	    public void delete(EquipmentConditionProfile equipmentConditionProfile) throws DataAccessLayerException  {
	        super.delete(equipmentConditionProfile);
	    }

	    /**
	     * Find an EquipmentConditionProfile by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public EquipmentConditionProfile find(int id) throws DataAccessLayerException  {
	        return (EquipmentConditionProfile) super.find(EquipmentConditionProfile.class, id);
	    }

	    /**
	     * Updates the state of a detached EquipmentConditionProfile.
	     *
	     * @param equipmentConditionProfile
	     * @throws DataAccessLayerException 
	     */
	    public void update(EquipmentConditionProfile equipmentConditionProfile) throws DataAccessLayerException {
	        super.saveOrUpdate(equipmentConditionProfile);
	    }

	    /**
	     * Finds all EquipmentConditionProfiles in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(EquipmentConditionProfile.class);
	    }
	    

}

