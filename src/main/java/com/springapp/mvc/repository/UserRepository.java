package com.springapp.mvc.repository;

import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.UsersEntity;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kapitoha on 06.05.15.
 */
@Transactional
@Repository
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<UsersEntity> getAllUsers()
    {
        return new DAOmngr(sessionFactory).getInstanceList(UsersEntity.class);
    }

    public UsersEntity getUser(int id)
    {
        return (UsersEntity) new DAOmngr(sessionFactory).getInstance(UsersEntity.class, id);
    }
    
    public boolean isExistsUser(UsersEntity user)
    {
	List<UsersEntity> list = new DAOmngr(sessionFactory).getInstanceList(UsersEntity.class, Order.asc("id"), Restrictions.or(Restrictions.eq("id", user.getId()), Restrictions.eq("login", user.getLogin())));
	return !list.isEmpty();
    }
    
    public List<UsersEntity> getUsersByFields(UsersEntity user)
    {
	List<UsersEntity> list = new DAOmngr(sessionFactory).getInstanceList(UsersEntity.class, Order.asc("id"), Restrictions.or(Restrictions.eq("name", user.getName()), Restrictions.eq("login", user.getLogin())));
	return list;
    }
    
    public boolean saveUser(UsersEntity user)
    {
	return new DAOmngr(sessionFactory).saveInstance(user);
    }
    
    public boolean updateUser(UsersEntity user)
    {
	return new DAOmngr(sessionFactory).updateInstance(user);
    }
}
