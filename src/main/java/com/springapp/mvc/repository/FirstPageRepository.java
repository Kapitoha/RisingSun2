package com.springapp.mvc.repository;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.FirstpageEntity;
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
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM firstpage LEFT JOIN articles on firstpage.Article_ID=articles.ID order by Raiting").addEntity(ArticlesEntity.class).list();
    }

    public void removeFirstPage(Integer id){
        FirstpageEntity first=(FirstpageEntity)this.sessionFactory.getCurrentSession().load(FirstpageEntity.class,id);
        if (null!=first){
            this.sessionFactory.getCurrentSession().delete(first);
        }
    }

}
