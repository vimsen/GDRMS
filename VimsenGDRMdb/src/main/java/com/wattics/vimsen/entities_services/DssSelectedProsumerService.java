package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.DssSelectedProsumer;

public class DssSelectedProsumerService extends ServicesAbstract{


  public DssSelectedProsumerService(HibernateUtil hibernateUtil) {
         super(hibernateUtil);
     }

     /**
      * Insert a new DssSelectedProsumer into the database.
      * @param dssSelectedProsumer
     * @throws DataAccessLayerException 
      */
     public void insert(DssSelectedProsumer dssSelectedProsumer) throws DataAccessLayerException  {
         super.saveOrUpdate(dssSelectedProsumer);
     }


     /**
      * Delete a detached DssSelectedProsumer from the database.
      * @param dssSelectedProsumer
     * @throws DataAccessLayerException 
      */
     public void delete(DssSelectedProsumer dssSelectedProsumer) throws DataAccessLayerException  {
         super.delete(dssSelectedProsumer);
     }

     /**
      * Find an DssSelectedProsumer by its primary key.
      * @param id
      * @return
     * @throws DataAccessLayerException 
      */
     public DssSelectedProsumer find(int id) throws DataAccessLayerException  {
         return (DssSelectedProsumer) super.find(DssSelectedProsumer.class, id);
     }

     /**
      * Updates the state of a detached DssSelectedProsumer.
      *
      * @param dssSelectedProsumer
     * @throws DataAccessLayerException 
      */
     public void update(DssSelectedProsumer dssSelectedProsumer) throws DataAccessLayerException {
         super.saveOrUpdate(dssSelectedProsumer);
     }

     /**
      * Finds all DssSelectedProsumer in the database.
      * @return
     * @throws DataAccessLayerException 
      */
     
     public List<?> findAll() throws DataAccessLayerException {
         return super.findAll(DssSelectedProsumer.class);
     }
     
  
}
