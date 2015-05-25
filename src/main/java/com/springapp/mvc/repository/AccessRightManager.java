package com.springapp.mvc.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.AccessRight;

/**
 * @author kapitoha
 *
 */
@Repository
@Transactional
public class AccessRightManager {
    private DAOmngr manager;
    public DAOmngr getManager()
    {
	if (manager == null)
	    manager = new DAOmngr();
	return manager;
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
	    String[] rights = new String("EDIT_USER,"
	    	+ "EDIT_ARTICLE_MASTER,"
		+ "DELETE_ARTICLE_MASTER,"
	    	+ "EDIT_ARTICLE_AUTHOR,"
	    	+ "DELETE_ARTICLE_AUTHOR,"
	    	+ "CREATE_ARTICLE")
	    .split(",\\s*");
	    for (String string : rights)
	    {
		getManager().saveInstance(new AccessRight(string));
	    }
	    list = new ArrayList<AccessRight>(getAccessRightsList());
	}
	return list;
    }
}
