package com.wattics.vimsen.dbDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static SessionFactory sessionFactory;

  public HibernateUtil(String configurationFile) {

    buildIfNeeded(configurationFile);

  }

  @SuppressWarnings("deprecation")
  private SessionFactory buildSessionFactory(String configurationFile) {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      // return new Configuration().configure().buildSessionFactory( new
      // StandardServiceRegistryBuilder().build());
      return new Configuration().configure(configurationFile).buildSessionFactory();
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Builds a SessionFactory, if it hasn't been already.
   */
  public SessionFactory buildIfNeeded(String configurationFile) {
    if (sessionFactory != null) {
      return sessionFactory;
    }
    sessionFactory = buildSessionFactory(configurationFile);
    return sessionFactory;

  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public Session openSession() {
    // buildIfNeeded();
    return sessionFactory.openSession();
  }

  public void close(Session session) {
    if (session != null) {
      // try {
      session.close();
      // } //catch (HibernateException ignored) {
      // log.error("Couldn't close Session", ignored);
      // }
    }
  }

  public Transaction beginTransaction() {

    Session session = sessionFactory.openSession();
    return session.beginTransaction();
  }

  public void closeSessionFactory() {
    if (sessionFactory != null) {
      sessionFactory.close();
    }

  }

  public void setSessionFactoryToNull() {
    sessionFactory = null;
  }

}
