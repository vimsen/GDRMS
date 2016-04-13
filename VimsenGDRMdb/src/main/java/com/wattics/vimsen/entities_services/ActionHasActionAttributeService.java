package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.ActionHasActionAttribute;
import com.wattics.vimsen.entities.ActionHasActionAttributeId;

public class ActionHasActionAttributeService extends ServicesAbstract{
	
	
	 public ActionHasActionAttributeService(HibernateUtil hibernateUtil) {
	        super(hibernateUtil);
	    }

	    /**
	     * Insert a new ActionHasActionAttribute into the database.
	     * @param actionHasActionAttribute
	     * @throws DataAccessLayerException 
	     */
	    public void insert(ActionHasActionAttribute actionHasActionAttribute) throws DataAccessLayerException  {
	        super.saveOrUpdate(actionHasActionAttribute);
	    }


	    /**
	     * Delete a detached ActionHasActionAttribute from the database.
	     * @param actionHasActionAttribute
	     * @throws DataAccessLayerException 
	     */
	    public void delete(ActionHasActionAttribute actionHasActionAttribute) throws DataAccessLayerException  {
	        super.delete(actionHasActionAttribute);
	    }


		protected ActionHasActionAttribute findActionHasActionAttribute(ActionHasActionAttributeId id) throws DataAccessLayerException {
			ActionHasActionAttribute obj = null;
			Session session = null;

			try {
				session = super.hibernateUtil.openSession();
				super.tx = session.beginTransaction();
				obj = (ActionHasActionAttribute)session.load(ActionHasActionAttribute.class, id);
				super.tx.commit();
			} catch (HibernateException e) {
				handleException(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}

			return obj;
		}

	    /**
	     * Find an ActionHasActionAttribute by its primary key.
	     * @param id
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    public ActionHasActionAttribute find(ActionHasActionAttributeId id) throws DataAccessLayerException  {
	        return this.findActionHasActionAttribute(id);
	    }

	    /**
	     * Updates the state of a detached ActionHasActionAttribute.
	     *
	     * @param actionHasActionAttribute
	     * @throws DataAccessLayerException 
	     */
	    public void update(ActionHasActionAttribute actionHasActionAttribute) throws DataAccessLayerException {
	        super.saveOrUpdate(actionHasActionAttribute);
	    }

	    /**
	     * Finds all ActionHasActionAttributes in the database.
	     * @return
	     * @throws DataAccessLayerException 
	     */
	    
	    public List<?> findAll() throws DataAccessLayerException {
	        return super.findAll(ActionHasActionAttribute.class);
	    }
	    

}
