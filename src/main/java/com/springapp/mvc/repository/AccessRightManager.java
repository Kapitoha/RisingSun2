package com.springapp.mvc.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.DAOManager;
import com.springapp.mvc.domain.AccessRight;
import com.springapp.mvc.domain.Right;

/**
 * @author kapitoha
 *
 */
@Repository
@Transactional
public class AccessRightManager {
    @Autowired
    private SessionFactory sessionFactory;
    private DAOManager manager;
    public DAOManager getManager()
    {
	return manager != null? manager : new DAOManager(sessionFactory);
    }
    
    public List<AccessRight> getAccessRightsList()
    {
	return getManager().getInstanceList(AccessRight.class);
    }
    
    public AccessRight getAccessRightById(int id)
    {
	return (AccessRight) getManager().getInstance(AccessRight.class, id);
    }
    
    public List<AccessRight> createAndGetDefaultRights()
    {
	List<AccessRight> list = getAccessRightsList();
	if (null == list || list.isEmpty())
	{
	    for (Right right : Right.values())
	    {
		getManager().saveInstance(new AccessRight(right.toString()));
	    }
	    list = new ArrayList<AccessRight>(getAccessRightsList());
	}
	return list;
    }
}
