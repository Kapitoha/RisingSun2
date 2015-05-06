package com.springapp.mvc.repository;

import com.springapp.mvc.domain.ArticlesEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Alex on 01.05.2015.
 */

@Repository
@Transactional
public class ArticlesRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void addArticle(ArticlesEntity arcticlesEntity){
        this.sessionFactory.getCurrentSession().save(arcticlesEntity);
    }



    public List<ArticlesEntity> listAll(){
        return this.sessionFactory.getCurrentSession().createQuery("from articles").list();
    }

    public List<ArticlesEntity> newsSearch(String name){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM articles WHERE articles.Article LIKE :name order by DateCreate").addEntity(ArticlesEntity.class).setString("name", name).list();
    }

    public List<ArticlesEntity> newsArchive(Date dateArchive){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM articles WHERE articles.archive=1 and articles.DateCreate BETWEEN :date1 and LAST_DAY(:date1) order by DateCreate").addEntity(ArticlesEntity.class).setDate("date1", dateArchive).list();
    }

    public List<Date> newsArchive(){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT LAST_DAY(DateCreate) + INTERVAL 1 DAY - INTERVAL 1 MONTH  as DateArchive FROM articles where articles.archive=1 GROUP BY DateArchive").list();
    }

    public void removeArticle(Integer id){
        ArticlesEntity article=(ArticlesEntity)this.sessionFactory.getCurrentSession().load(ArticlesEntity.class,id);
        if(null!=article){
            this.sessionFactory.getCurrentSession().delete(article);
        }
    }
}

