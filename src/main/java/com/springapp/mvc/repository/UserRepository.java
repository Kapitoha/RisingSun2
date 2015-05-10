package com.springapp.mvc.repository;

import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.UsersEntity;

import java.util.List;

import org.hibernate.SessionFactory;
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
}