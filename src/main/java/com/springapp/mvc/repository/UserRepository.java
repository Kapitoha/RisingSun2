package com.springapp.mvc.repository;

import com.springapp.mvc.dao.DAOManager;
import com.springapp.mvc.domain.UsersEntity;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kapitoha on 06.05.15.
 */
@Transactional
@Repository
public class UserRepository {
    @Autowired
    private DAOManager manager;

    public List<UsersEntity> getAllUsers()
    {
        return manager.getInstanceList(UsersEntity.class);
    }

    public UsersEntity getUser(int id)
    {
        return (UsersEntity) manager.getInstance(UsersEntity.class, id);
    }
    
    public boolean isExistsUser(UsersEntity user)
    {
	List<UsersEntity> list = manager.getInstanceList(UsersEntity.class, Order.asc("id"),
                        Restrictions.or(Restrictions.eq("id", user.getId()), Restrictions.eq("login", user.getLogin())));
	return !list.isEmpty();
    }
    
    public List<UsersEntity> getUsersByFields(UsersEntity user)
    {
	List<UsersEntity> list = manager.getInstanceList(UsersEntity.class, Order.asc("id"),
                        Restrictions.or(Restrictions.eq("name", user.getName()),
                                        Restrictions.eq("login", user.getLogin())));
	return list;
    }
    
    public UsersEntity getUser(String login)
    {
	List<UsersEntity> users = manager.getInstanceList(UsersEntity.class, Order.asc("login"),
                        Restrictions.eq("login", login));
	return (users != null && !users.isEmpty())? users.get(0) : null;
    }
    
    public boolean saveUser(UsersEntity user)
    {
	return manager.saveInstance(user);
    }
    
    public boolean updateUser(UsersEntity user)
    {
	return manager.updateInstance(user);
    }
}
