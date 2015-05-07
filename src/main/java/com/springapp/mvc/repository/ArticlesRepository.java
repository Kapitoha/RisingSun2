package com.springapp.mvc.repository;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.TagsEntity;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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

    public List<ArticlesEntity> newsSearchByTag(String name){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT articles.* from articles LEFT JOIN tagsarcticle on articles.ID = tagsarcticle.ID_Arcticle LEFT JOIN tags on tags.ID = tagsarcticle.ID_Teg where tags.Name=:name order by DateCreate").addEntity(ArticlesEntity.class).setString("name", name).list();
    }

    public TagsEntity maxCountByTag(String name){
        return (TagsEntity) this.sessionFactory.getCurrentSession().createSQLQuery("Select ID,Name from (Select ID_Teg, max(countTeg) as often from (Select ID_Teg, count(countTeg) as countTeg from (SELECT\n" +
                "ID_Teg,\n" +
                "  1 as countTeg\n" +
                "FROM tagsarcticle LEFT JOIN tags on tags.ID = tagsarcticle.ID_Teg\n" +
                "WHERE ID_Arcticle IN (SELECT ID_Arcticle\n" +
                "                      FROM tagsarcticle\n" +
                "                        LEFT JOIN tags ON tags.ID = tagsarcticle.ID_Teg\n" +
                "                      WHERE Name = :name) and tags.Name<>:name) as TableA group by ID_Teg) as TableB) as TableC  LEFT JOIN tags on TableC.ID_Teg=tags.ID").addEntity(TagsEntity.class).setString("name", name).uniqueResult();
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

