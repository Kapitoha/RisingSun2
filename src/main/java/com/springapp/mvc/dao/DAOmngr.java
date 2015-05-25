package com.springapp.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import com.springapp.mvc.domain.BaseEntity;

/**
 * @author kapitoha
 *
 */
public class DAOmngr {
    @Autowired
    private SessionFactory sessionFactory;
    
    public DAOmngr()
    {
    }
    
    public DAOmngr(SessionFactory sessionFactory)
    {
	this.sessionFactory = sessionFactory;
    }
    public  boolean saveInstance(BaseEntity instance)
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
            System.err.println(instance.getClass().getName()
                    + " Save was unsuccessful. Rollback");
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

    public  boolean updateInstance(BaseEntity instance)
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
     *
     * @param instance
     * @return
     */
    public  <T extends BaseEntity> List<T> getInstanceList(
            Class<T> instance)
    {
        String query = "Select u FROM " + instance.getSimpleName() + " u";
        Session session = sessionFactory.openSession();
        Query que = session.createQuery(query);
        @SuppressWarnings("unchecked")
        List<T> list = que.list();
        session.close();
        return (List<T>) list;
    }

    /**
     * Get Instance list with necessary criteria.
     *
     * @param instanceClass
     * @param criterion
     * @return
     */
    @SuppressWarnings("unchecked")
    public  <T extends BaseEntity> List<T> getInstanceList(
            Class<T> instanceClass,Order order, Criterion... criterion)
    {
        List<T> list = new ArrayList<>();
        Session session = null;
        Criteria criteria = null;
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
            if (null != criteria) {
                if (null != order)
                    criteria.addOrder(order);
                list.addAll(criteria.list());
            }
        }
        catch (HibernateException e)
        {
            System.err.println("getInstanceList(class, Criterion) "
                    + e.getMessage());
        }
        finally
        {
            if (null != session)
                session.close();
        }
        return list;
    }

    public  <T extends BaseEntity> BaseEntity getInstance(
            Class<T> instanceClass, int id)
    {
        BaseEntity inst = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        inst = (BaseEntity) session.get(instanceClass, id);
        if (session != null)
        {
            session.close();
        }
        return inst;
    }

    /**
     * Delete instance from DB where
     *
     * @param id
     *            - id of instance
     * @param instanceClass
     *            - Class of instance
     */
    public <T extends BaseEntity> boolean deleteInstance(int id,
                                                              Class<T> instanceClass)
    {
        Session session = null;
        try
        {
            session = sessionFactory.openSession();
            BaseEntity instance = (BaseEntity) session
                    .load(instanceClass, id);
            if (null != instance)
            {
                Transaction transaction = session.beginTransaction();
                session.delete(instance);
                transaction.commit();
            }
        }
        catch(Exception e)
        {
            return false;
        }
        finally
        {
            if (null != session)
                session.close();
        }
        return true;

    }

}
