package com.springapp.mvc.repository;

import com.springapp.mvc.dao.DAOManager;
import com.springapp.mvc.domain.Article;
import com.springapp.mvc.domain.FirstPage;
import com.springapp.mvc.domain.TagsEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kapitoha
 *
 */
@Repository
@Transactional
public class ArticleManager {

    @Autowired
    private DAOManager daomanager;
    private TagManager tagManager;

    public TagManager getTagsManager()
    {
	return null == tagManager? new ArticleManager.TagManager() : tagManager;
    }

    public List<Article> getArticles()
    {
	return daomanager.getInstanceList(Article.class);
    }

    public Article getArticle(int id)
    {
	return (Article) daomanager.getInstance(Article.class, id);
    }

    public boolean saveArticle(Article article)
    {
	article.setCreationDate(new Date());
	return daomanager.saveInstance(article);
    }

    public boolean updateArticle(Article article)
    {
	return daomanager.updateInstance(article);
    }

    public List<FirstPage> getFirstPageArticles()
    {
	return daomanager.getInstanceList(FirstPage.class);
    }

    public boolean deleteArticle(Article article)
    {
	return daomanager.deleteInstance(article.getId(), Article.class);
    }

//    public boolean putArticleOnFirstPage(Article article, boolean isFeatured)
//    {
//	FirstPage page = new FirstPage();
//	page.setArticle(article);
//	page.setFeatured(isFeatured);
//	return false;
//    }
    
    public FirstPage getLastFirstPage()
    {
	FirstPage fp = null;
	Session session = null;
	try
	{
	    fp = new FirstPage();
	    DetachedCriteria maxId = DetachedCriteria.forClass(FirstPage.class)
		    .setProjection(Projections.max("id"));
	    session = daomanager.getSessionFactory().openSession();
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
    
//    public int getLastMainPageId()
//    {
//	FirstPage last = getLastFirstPage();
//	return (last != null)? last.getId() : 0;
//    }

    public FirstPage getFirstPage(Article article)
    {
	List<FirstPage> fpList = daomanager.getInstanceList(FirstPage.class, null, Restrictions.eq("article", article));
	System.out.println("FPList size: " + fpList.size());
	return (fpList != null && !fpList.isEmpty()) ? fpList.get(0) : null;
    }

    public void removeArticleFromFirstPageDirectly(Article article)
    {
	if (null != article)
	{
	    Session session = null;
	    Transaction transaction = null;
	    try
	    {
		session = daomanager.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		Query query = session.createQuery(String.format(
		    "DELETE FROM %s WHERE article_id = :id",
		    FirstPage.class.getSimpleName()));
		query.setInteger("id", article.getId());
		System.out.println(query.executeUpdate());
		transaction.commit();
	    }
	    catch (HibernateException e)
	    {
		if (null != transaction)
		{
		    transaction.rollback();
		}
		e.printStackTrace();
	    }
	    finally {
		if (null != session) session.close();
	    }
	}
	else {
	    System.err.println("article == null");
	}
    }
    
    
    public class TagManager {
	public TagsEntity getTag(String tagName)
	{
	    List<TagsEntity> list = daomanager.getInstanceList(TagsEntity.class, Order.asc("name"),
			    Restrictions.eq("name", tagName));
	    return list != null && !list.isEmpty()? list.get(0) : null;
	}
	public TagsEntity getTag(int id)
	{
	    List<TagsEntity> list = daomanager.getInstanceList(TagsEntity.class, Order.asc("id"),
			    Restrictions.eq("id", id));
	    return list != null && !list.isEmpty()? list.get(0) : null;
	}
	//additional methods
	public List<TagsEntity> getTags(Article article)
	{
	    Session session = null;
	    Transaction transaction = null;
	    List<TagsEntity> tags = new ArrayList<TagsEntity>();
	    try
	    {
		session = daomanager.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		Query query = session.createSQLQuery("SELECT tag_id FROM article_tags at WHERE at.article_id = :article_id");
		query.setInteger("article_id", article.getId());
		transaction.commit();
		for (Object res : query.list())
		{
		    tags.add(getTagsManager().getTag((int)res));
		}
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
		if (null != transaction)
		{
		    transaction.rollback();
		}
	    }
	    finally
	    {
		if (null != session) session.close();
	    }
	    return tags;
	}
	public void removeTagFromArticleDirectly(Article article, TagsEntity tag)
	{
	    Session session = null;
	    Transaction transaction = null;
	    try
	    {
		session = daomanager.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		Query query = session.createSQLQuery("DELETE FROM article_tags a WHERE a.article_id = :article_id AND a.tag_id = :tag_id");
		query.setInteger("article_id", article.getId());
		query.setInteger("tag_id", tag.getId());		
		transaction.commit();
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
		if (null != transaction)
		{
		    transaction.rollback();
		}
	    }
	    finally
	    {
		if (null != session) session.close();
	    }
	}
    }
    

}
