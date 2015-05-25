package com.springapp.mvc.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.Article;
import com.springapp.mvc.domain.FirstPage;

/**
 * @author kapitoha
 *
 */
@Repository
@Transactional
public class ArticleManager {
    @Autowired
    private SessionFactory sessionFactory;
    
    public DAOmngr getManager()
    {
	return new DAOmngr(sessionFactory);
    }

    public List<Article> getArticles()
    {
	return getManager().getInstanceList(Article.class);
    }

    public Article getArticle(int id)
    {
	return (Article) getManager().getInstance(Article.class, id);
    }

    public boolean saveArticle(Article article)
    {
	article.setCreationDate(new Date());
	return getManager().saveInstance(article);
    }

    public boolean updateArticle(Article article)
    {
	return getManager().updateInstance(article);
    }

    public List<FirstPage> getFirstPageArticles()
    {
	return getManager().getInstanceList(FirstPage.class);
    }

    public boolean deleteArticle(Article article)
    {
	return getManager().deleteInstance(article.getId(), Article.class);
    }

    public boolean putArticleOnFirstPage(Article article, boolean isFeatured)
    {
	FirstPage page = new FirstPage();
	page.setArticle(article);
	page.setFeatured(isFeatured);
	page.setPreviousPageId(getLastMainPageId());
	return false;
    }
    
    public FirstPage getLastFirstPage()
    {
	FirstPage fp = null;
	Session session = null;
	try
	{
	    fp = new FirstPage();
	    DetachedCriteria maxId = DetachedCriteria.forClass(FirstPage.class)
		    .setProjection(Projections.max("id"));
	    session = sessionFactory.openSession();
	    @SuppressWarnings("unchecked")
	    List<FirstPage> list = session.createCriteria(FirstPage.class)
		    .add(Property.forName("id").eq(maxId)).list();
	    if (null != list && !list.isEmpty())
		fp = list.get(0);
	}
	catch (HibernateException e)
	{
	    e.printStackTrace();
	    return null;
	}
	finally
	{
	    if (null != session)
		session.close();
	}
	return fp;
    }
    
    public int getLastMainPageId()
    {
	FirstPage last = getLastFirstPage();
	return (last != null)? last.getId() : 0;
    }

    public FirstPage getFirstPage(Article article)
    {
	List<FirstPage> fpList = getManager().getInstanceList(
		FirstPage.class, null, Restrictions.eq("article_id", article.getId()));
	return (fpList != null && !fpList.isEmpty()) ? fpList.get(0) : null;
    }

    public boolean removeArticleFromFirstPage(Article article)
    {
	FirstPage fst = getFirstPage(article);
	return (null != fst) ? getManager()
		.deleteInstance(fst.getId(), FirstPage.class) : false;
    }

}
