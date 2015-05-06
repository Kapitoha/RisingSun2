package com.springapp.mvc.repository;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.FirstpageEntity;
import com.springapp.mvc.domain.UsersEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Alex on 01.05.2015.
 */
@Repository
@Transactional
public class FirstPageRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void addFirstPage(FirstpageEntity firstpageEntity){
        this.sessionFactory.getCurrentSession().save(firstpageEntity);
    }

    public List<FirstpageEntity> listAll(){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM firstpage LEFT JOIN articles ON firstpage.Article_ID = articles.ID LEFT JOIN users ON users.ID = articles.Author ORDER BY Raiting").addEntity(ArticlesEntity.class).addEntity(UsersEntity.class).list();
    }

    public List<FirstpageEntity> newsByName(String name){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM firstpage LEFT JOIN articles on firstpage.Article_ID=articles.ID LEFT JOIN users on users.ID = articles.Author WHERE articles.NamePage=:name order by Raiting").addEntity(ArticlesEntity.class).addEntity(UsersEntity.class).setString("name", name).list();
    }

    public List<Date> newsArchive(){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT LAST_DAY(DateCreate) + INTERVAL 1 DAY - INTERVAL 1 MONTH  as DateArchive FROM articles where articles.archive=1 GROUP BY DateArchive").list();
    }

    public void removeFirstPage(Integer id){
        FirstpageEntity first=(FirstpageEntity)this.sessionFactory.getCurrentSession().load(FirstpageEntity.class,id);
        if (null!=first){
            this.sessionFactory.getCurrentSession().delete(first);
        }
    }

}
