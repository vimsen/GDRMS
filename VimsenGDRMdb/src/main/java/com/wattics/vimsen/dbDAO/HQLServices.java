package com.wattics.vimsen.dbDAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HQLServices {

  protected final HibernateUtil hibernateUtil;
  protected Transaction tx;

  public HQLServices(HibernateUtil hibernateUtil) {
    this.hibernateUtil = hibernateUtil;
  }

  public int hqlTruncate(String myTable) throws DataAccessLayerException {
    Session session = null;
    int queryResult = 0;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      String hql = String.format("DELETE FROM %s", myTable);
      Query query = session.createQuery(hql);
      queryResult = query.executeUpdate();
      tx.commit();
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return queryResult;
  }

  public List selectWhereCondition(String selectQuery) throws DataAccessLayerException {
    Session session = null;
    List result = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      String hql = selectQuery;
      Query query = session.createQuery(hql);
      result = query.list();
      tx.commit();
    } catch (HibernateException e) {
      handleException(e);
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return result;
  }

  protected void handleException(HibernateException e) throws DataAccessLayerException {
    rollback(tx);
    throw new DataAccessLayerException(e);
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
