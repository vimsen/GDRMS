package com.wattics.vimsen.entities_services;

import java.util.List;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ProsumerHasSite;

public class ProsumerHasSiteService extends ServicesAbstract{
  
  
  public ProsumerHasSiteService(HibernateUtil hibernateUtil) {
         super(hibernateUtil);
     }

     /**
      * Insert a new ProsumerHasSite into the database.
      * @param prosumer
     * @throws DataAccessLayerException 
      */
     public void insert(ProsumerHasSite prosumerHasSite) throws DataAccessLayerException  {
         super.saveOrUpdate(prosumerHasSite);
     }


     /**
      * Delete a detached ProsumerHasSite from the database.
      * @param prosumer
     * @throws DataAccessLayerException 
      */
     public void delete(ProsumerHasSite prosumerHasSite) throws DataAccessLayerException  {
         super.delete(prosumerHasSite);
     }

     /**
      * Find an ProsumerHasSite by its primary key.
      * @param id
      * @return
     * @throws DataAccessLayerException 
      */
     public ProsumerHasSite find(int id) throws DataAccessLayerException  {
         return (ProsumerHasSite) super.find(ProsumerHasSite.class, id);
     }

     /**
      * Updates the state of a detached ProsumerHasSite.
      *
      * @param prosumerHasSite
     * @throws DataAccessLayerException 
      */
     public void update(ProsumerHasSite prosumerHasSite) throws DataAccessLayerException {
         super.saveOrUpdate(prosumerHasSite);
     }

     /**
      * Finds all ProsumerHasSites in the database.
      * @return
     * @throws DataAccessLayerException 
      */
     
     public List<?> findAll() throws DataAccessLayerException {
         return super.findAll(ProsumerHasSite.class);
     }
     

}

