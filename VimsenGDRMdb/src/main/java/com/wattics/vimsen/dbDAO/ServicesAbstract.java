package com.wattics.vimsen.dbDAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class ServicesAbstract {

  protected final HibernateUtil hibernateUtil;

  protected Transaction tx;

  public ServicesAbstract(HibernateUtil hibernateUtil) {
    this.hibernateUtil = hibernateUtil;
  }

  protected void saveOrUpdate(Object obj) throws DataAccessLayerException {
    Session session = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      session.saveOrUpdate(obj);
      tx.commit();
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  protected void delete(Object obj) throws DataAccessLayerException {
    Session session = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      session.delete(obj);
      tx.commit();
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  protected Object find(Class clazz, int id) throws DataAccessLayerException {
    Object obj = null;
    Session session = null;

    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      obj = session.load(clazz, id);
      tx.commit();
    } catch (ObjectNotFoundException e) {
      hanldeObjectNotFound(e);
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }

    }

    return obj;
  }

  protected Object get(Class clazz, int id) throws DataAccessLayerException {
    Object obj = null;
    Session session = null;

    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      obj = session.get(clazz, id);
      tx.commit();
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }

    }

    return obj;
  }

  protected List<Object> findAll(Class clazz) throws DataAccessLayerException {
    List<Object> objects = null;
    Session session = null;

    try {
      session = hibernateUtil.openSession();
      Transaction tx = session.beginTransaction();
      Query query = session.createQuery("from " + clazz.getName());
      objects = query.list();
      tx.commit();
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }
    }

    return objects;
  }

  protected void handleException(HibernateException e) throws DataAccessLayerException {
    rollback(tx);
    throw new DataAccessLayerException(e);
  }

  protected void hanldeObjectNotFound(ObjectNotFoundException e) throws ObjectNotFoundException {
    rollback(tx);
    throw e;

  }

  private void rollback(Transaction tx) {
    try {
      if (tx != null) {
        tx.rollback();
      }
    } catch (HibernateException ignored) {
      // log.error("Couldn't rollback Transaction", ignored);
    }
  }

}
