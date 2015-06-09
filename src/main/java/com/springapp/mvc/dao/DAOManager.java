package com.springapp.mvc.dao;

import com.springapp.mvc.domain.BaseEntity;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kapitoha
 */
@Service("dao")
public class DAOManager {
    @Autowired private SessionFactory sessionFactory;

    public DAOManager()
    {
    }

    public DAOManager(SessionFactory sessionFactory)
    {
	this.sessionFactory = sessionFactory;
    }

    public boolean saveInstance(BaseEntity instance)
    {
	Session session = null;
	Transaction trans = null;
	try
	{
	    session = sessionFactory.openSession();
	    trans = session.beginTransaction();
	    session.save(instance);
	    trans.commit();
	    return true;
	}
	catch (HibernateException e)
	{
	    if (trans != null)
	    {
		trans.rollback();
	    }
	    System.err.println(instance.getClass().getName() + " Save was unsuccessful. Rollback");
	    e.printStackTrace();
	    return false;
	}
	finally
	{
	    if (null != session)
	    {
		session.close();
	    }
	}
    }

    public boolean updateInstance(BaseEntity instance)
    {
	Session session = null;
	Transaction trans = null;
	try
	{
	    session = sessionFactory.openSession();
	    trans = session.beginTransaction();
	    session.update(instance);
	    trans.commit();
	    return true;
	}
	catch (Exception e)
	{
	    if (trans != null)
	    {
		trans.rollback();
	    }
	    System.err.println("Update was unsuccessful. Rollback");
	    e.printStackTrace();
	    return false;
	}
	finally
	{
	    if (null != session)
	    {
		session.close();
	    }
	}
    }

    /**
     * Returns list of Instances Where instance param is Instance.class
     */
    public <T extends BaseEntity> List<T> getInstanceList(Class<T> instance)
    {
	String query = "Select u FROM " + instance.getSimpleName() + " u";
	Session session = sessionFactory.openSession();
	Query que = session.createQuery(query);
	@SuppressWarnings("unchecked")
	List<T> list = que.list();
	session.close();
	return list;
    }

    /**
     * Get Instance list with necessary criteria.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> List<T> getInstanceList(Class<T> instanceClass, Order order, Criterion... criterion)
    {
	List<T> list = new ArrayList<>();
	Session session = null;
	Criteria criteria;
	try
	{
	    session = sessionFactory.openSession();
	    criteria = session.createCriteria(instanceClass);
	    if (null != criterion && criterion.length > 0)
	    {
		for (Criterion crit : criterion)
		{
		    criteria.add(crit);
		}
	    }
	    if (null != criteria)
	    {
		if (null != order)
		    criteria.addOrder(order);
		list.addAll(criteria.list());
	    }
	}
	catch (HibernateException e)
	{
	    System.err.println("getInstanceList(class, Criterion) " + e.getMessage());
	}
	finally
	{
	    if (null != session)
		session.close();
	}
	return list;
    }

    public <T extends BaseEntity> BaseEntity getInstance(Class<T> instanceClass, int id)
    {
	BaseEntity inst = null;
	Session session = null;
	try
	{
	    session = sessionFactory.openSession();
	    session.beginTransaction();
	    inst = (BaseEntity) session.get(instanceClass, id);
	}
	catch (HibernateException e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    if (null != session)
	    {
		session.close();
	    }
	}
	return inst;
    }

    /**
     * Delete instance from DB where
     *
     * @param id            - id of instance
     * @param instanceClass - Class of instance
     */
    public <T extends BaseEntity> boolean deleteInstance(int id, Class<T> instanceClass)
    {
	Session session = null;
	try
	{
	    session = sessionFactory.openSession();
	    BaseEntity instance = (BaseEntity) session.load(instanceClass, id);
	    if (null != instance)
	    {
		Transaction transaction = session.beginTransaction();
		session.delete(instance);
		transaction.commit();
	    }
	    else
		System.err.println("Required for deletion instance not found (null)");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    return false;
	}
	finally
	{
	    if (null != session)
		session.close();
	}
	return true;

    }

    public void executeHQLQuery(String hqlQuery, Object... args)
    {
	Session session = null;
	Transaction transaction = null;
	try
	{
	    session = sessionFactory.openSession();
	    transaction = session.beginTransaction();
	    Query query = generateHQLQuery(session, hqlQuery, args);
	    query.executeUpdate();
	    transaction.commit();
	}
	catch (HibernateException e)
	{
	    if (null != transaction)
	    {
		transaction.rollback();
	    }
	    e.printStackTrace();
	}
	finally
	{
	    if (null != session)
		session.close();
	}
    }

    @SuppressWarnings("rawtypes")
    public List executeHQLQueryAndGetList(String hqlQuery, Object... args)
    {
	List list = Collections.emptyList();
	Session session = null;
	Transaction transaction = null;
	try
	{
	    session = sessionFactory.openSession();
	    transaction = session.beginTransaction();
	    Query query = generateHQLQuery(session, hqlQuery, args);
	    list = query.list();
	    transaction.commit();
	}
	catch (HibernateException e)
	{
	    if (null != transaction)
	    {
		transaction.rollback();
	    }
	    e.printStackTrace();
	}
	finally
	{
	    if (null != session)
		session.close();
	}
	return list;
    }

    /**
     * This method parses the query string to find arguments names and assign values from args array
     */
    private Query generateHQLQuery(Session session, String hqlQuery, Object[] args)
    {
	Query query = session.createQuery(hqlQuery);
	List<String> names = new ArrayList<>();
	final Pattern pattern = Pattern.compile(":\\S+");
	final Matcher matcher = pattern.matcher(hqlQuery);
	while (matcher.find())
	{
	    names.add(matcher.group().replaceFirst(":", ""));
	}
	boolean isUseNames = null != names && !names.isEmpty();
	for (int i = 0; i < args.length; i++)
	{
	    if (isUseNames)
	    {
		query.setParameter(names.get(i).replaceAll("\\s+", ""), args[i]);
	    }
	    else
		query.setParameter(i, args[i]);
	}
	return query;
    }

    public SessionFactory getSessionFactory()
    {
	return sessionFactory;
    }

}
