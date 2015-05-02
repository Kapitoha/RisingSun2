package com.springapp.mvc.repository;

import com.springapp.mvc.domain.ArticlesEntity;
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
public class ArticlesRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void addArticle(ArticlesEntity arcticlesEntity){
        this.sessionFactory.getCurrentSession().save(arcticlesEntity);
    }

    public List<ArticlesEntity> listAll(){
        return this.sessionFactory.getCurrentSession().createQuery("from articles").list();
    }

    public void removeArticle(Integer id){
        ArticlesEntity article=(ArticlesEntity)this.sessionFactory.getCurrentSession().load(ArticlesEntity.class,id);
        if(null!=article){
            this.sessionFactory.getCurrentSession().delete(article);
        }
    }
}

