package com.wattics.vimsen.entities_services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.dbDAO.ServicesAbstract;
import com.wattics.vimsen.entities.Kwh15mn;
import com.wattics.vimsen.entities.Kwh15mnId;

public class Kwh15mnService extends ServicesAbstract {

	public Kwh15mnService(HibernateUtil hibernateUtil) {
		super(hibernateUtil);
	}

	/**
	 * Insert a new Kwh15mn into the database.
	 * 
	 * @param kwh15mn
	 * @throws DataAccessLayerException 
	 */
	public void insert(Kwh15mn kwh15mn) throws DataAccessLayerException {
		super.saveOrUpdate(kwh15mn);
	}

	/**
	 * Delete a detached Kwh15mn from the database.
	 * 
	 * @param kwh15mn
	 * @throws DataAccessLayerException 
	 */
	public void delete(Kwh15mn kwh15mn) throws DataAccessLayerException {
		super.delete(kwh15mn);
	}

	/**
	 * Find an Kwh15mn by its primary key.
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessLayerException 
	 */

	protected Kwh15mn findKwh15mn(Kwh15mnId id) throws DataAccessLayerException {
		Kwh15mn obj = null;
		Session session = null;

		try {
			session = super.hibernateUtil.openSession();
			super.tx = session.beginTransaction();
			obj = (Kwh15mn) session.load(Kwh15mn.class, id);
			super.tx.commit();
		}catch(ObjectNotFoundException e){
		  hanldeObjectNotFound(e);
	
		}
		catch (HibernateException e) {
			handleException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return obj;
	}

	/**
	 * Find Kwh15mn by its primary key.
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessLayerException 
	 */
	public Kwh15mn find(Kwh15mnId id) throws DataAccessLayerException {
		return this.findKwh15mn(id);
	}

	/**
	 * Updates the state of a detached Kwh15mn.
	 *
	 * @param kwh15mn
	 * @throws DataAccessLayerException 
	 */
	public void update(Kwh15mn kwh15mn) throws DataAccessLayerException {
		super.saveOrUpdate(kwh15mn);
	}

	/**
	 * Finds all Kwh15mns in the database.
	 * 
	 * @return
	 * @throws DataAccessLayerException 
	 */

	public List<?> findAll() throws DataAccessLayerException {
		return super.findAll(Kwh15mn.class);
	}

}
