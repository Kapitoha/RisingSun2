package com.springapp.mvc.repository;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.FirstpageEntity;
import com.springapp.mvc.domain.UsersEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	Session session = sessionFactory.openSession();
	List<FirstpageEntity> list = session.createSQLQuery("SELECT \n" +
                "    firstpage.ID,\n" +
                "    firstpage.Article_ID,\n" +
                "    firstpage.Raiting,\n" +
                "    Replace(Replace(Replace(Replace(articles.Article,\"<p>\",\"\"),\"</p>\",\"\"),\"<strong>\",\"\"),\"</strong>\",\"\") as Article,\n" +
                "    articles.Author,\n" +
                "    articles.ID,\n" +
                "    articles.Title,\n" +
                "    articles.NamePage,\n" +
                "    articles.DateCreate,\n" +
                "    users.ID,\n" +
                "    users.Name,\n" +
                "    users.Login\n" +
                "FROM\n" +
                "    firstpage\n" +
                "        LEFT JOIN\n" +
                "    articles ON firstpage.Article_ID = articles.ID\n" +
                "        LEFT JOIN\n" +
                "    users ON users.ID = articles.Author\n" +
                "ORDER BY Raiting").addEntity(ArticlesEntity.class).addEntity(UsersEntity.class).list();
	session.close();
	return list;
    }

    public List<FirstpageEntity> newsByName(String name){
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM firstpage LEFT JOIN articles on firstpage.Article_ID=articles.ID LEFT JOIN users on users.ID = articles.Author WHERE articles.NamePage=:name order by Raiting").addEntity(ArticlesEntity.class).addEntity(UsersEntity.class).setString("name", name).list();
    }


    public void removeFirstPage(Integer id){
        FirstpageEntity first=(FirstpageEntity)this.sessionFactory.getCurrentSession().load(FirstpageEntity.class,id);
        if (null!=first){
            this.sessionFactory.getCurrentSession().delete(first);
        }
    }

}
