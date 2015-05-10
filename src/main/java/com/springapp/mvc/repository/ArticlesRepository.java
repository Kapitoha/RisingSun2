package com.springapp.mvc.repository;

import com.springapp.mvc.dao.DAOManager;
import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.ArticlesEntity;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alex on 01.05.2015.
 */

@Repository
@Transactional
public class ArticlesRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean addArticle(ArticlesEntity arcticlesEntity){
        return new DAOmngr(sessionFactory).saveInstance(arcticlesEntity);
    }

    public List<String> newsArchive(){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT LAST_DAY(DateCreate) + INTERVAL 1 DAY - INTERVAL 1 MONTH  as DateArchive FROM articles GROUP BY DateArchive").list();
    }

    public List<ArticlesEntity> listAll(){
        return new DAOmngr(sessionFactory).getInstanceList(ArticlesEntity.class);
    }

    public List<ArticlesEntity> newsSearch(String name){
        return new DAOmngr(sessionFactory).getInstanceList(ArticlesEntity.class, Order.asc("DateCreate"), Restrictions.like("Article", name));
//        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM articles WHERE articles.Article LIKE :name order by DateCreate").addEntity(ArticlesEntity.class).setString("name", name).list();
    }

    public void removeArticle(Integer id){
        DAOManager.deleteInstance(id, ArticlesEntity.class);
        }
}


