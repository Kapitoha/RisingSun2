package com.springapp.mvc.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.RulesEntity;

/**
 * @author kapitoha
 *
 */
@Transactional
@Repository
public class RulesRepo {
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<RulesEntity> getRulesList()
    {
	return new DAOmngr(sessionFactory).getInstanceList(RulesEntity.class);
    }
    
    public RulesEntity getRuleById(int id)
    {
	return (RulesEntity) new DAOmngr(sessionFactory).getInstance(RulesEntity.class, id);
    }

}
