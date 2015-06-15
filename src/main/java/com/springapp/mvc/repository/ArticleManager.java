package com.springapp.mvc.repository;

import com.springapp.mvc.dao.DAOManager;
import com.springapp.mvc.domain.*;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author kapitoha
 */
@Repository
@Transactional
public class ArticleManager {

    @Autowired
    private DAOManager daomanager;
    private TagManager tagManager;
    private FirstPageManager firstPageManager;

    public TagManager getTagsManager()
    {
	return null == tagManager ? new ArticleManager.TagManager() : tagManager;
    }
    public FirstPageManager getFirstPageManager()
    {
	return (null == firstPageManager)? new ArticleManager.FirstPageManager() : firstPageManager;
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

    public Set<FirstPage> getFirstPages()
    {
	return new LinkedHashSet<>(daomanager.getInstanceList(FirstPage.class, Order.asc("show_order")));
    }

    public boolean deleteArticle(Article article)
    {
	return daomanager.deleteInstance(article.getId(), Article.class);
    }

    public FirstPage getFirstPage(Article article)
    {
	List<FirstPage> fpList = daomanager.getInstanceList(FirstPage.class, null,
		Restrictions.eq("article", article));
	return (fpList != null && !fpList.isEmpty()) ? fpList.get(0) : null;
    }





    public Collection<Article> getArchivedArticles()
    {
	List<Archive> list = daomanager.getInstanceList(Archive.class, Order.desc("date"));
	Set<Article> articles = new LinkedHashSet<Article>();
	for (Archive archive : list)
	{
	    articles.add(archive.getArticle());
	}
	return articles;
    }

    /**
     * Returns Set<Article> with articles with satisfied parameters.
     * 
     * @param compositeSearch
     *            if true, than all required parameters will be used
     *            independently from each other.
     * @param phrase
     *            some phrase or keyword in Article's title or content
     * @param author
     *            Cptn. Obvious
     * @param tags
     * @return Set
     */
    public List<Article> searchArticleByCriterion(boolean compositeSearch,
	    String phrase, UsersEntity author, TagsEntity... tags)
    {
	Set<Article> set = new HashSet<>();
	List<Criterion> criterions = new ArrayList<>();
	Criterion[] criterionArray;
	if (null != phrase && !phrase.isEmpty())
	{
	    Criterion title = Restrictions.like("title",
		    ("%" + phrase.toLowerCase() + "%"));
	    Criterion content = Restrictions.like("content",
		    ("%" + phrase.toLowerCase() + "%").getBytes());
	    criterions.add(Restrictions.or(title, content));
	    if (!compositeSearch)
	    {
		criterionArray = new Criterion[criterions.size()];
		for (int i = 0; i < criterions.size(); i++)
		{
		    criterionArray[i] = criterions.get(i);
		}
		set.addAll(daomanager.getInstanceList(Article.class, Order.asc("id"),
			criterionArray));
		criterions.clear();
	    }
	}
	if (null != author)
	{
	    criterions.add(Restrictions.eq("author", author));
	    if (!compositeSearch)
	    {
		criterionArray = new Criterion[criterions.size()];
		for (int i = 0; i < criterions.size(); i++)
		{
		    criterionArray[i] = criterions.get(i);
		}
		set.addAll(daomanager.getInstanceList(Article.class, Order.asc("author"),
			criterionArray));
		criterions.clear();
	    }
	}
	if (compositeSearch)
	{
	    criterionArray = new Criterion[criterions.size()];
	    for (int i = 0; i < criterions.size(); i++)
	    {
		criterionArray[i] = criterions.get(i);
	    }
	    set.addAll(daomanager.getInstanceList(Article.class, Order.asc("author"),
		    criterionArray));
	}
	if (null != tags && tags.length > 0)
	{
	    Set<Article> articles = new LinkedHashSet<>();
	    for (TagsEntity tag : tags)
		articles.addAll(tag.getArticles());
	    if (!compositeSearch)
		set.addAll(articles);
	    else
	    {
		articles.retainAll(set);
		set.clear();
		set = articles;
	    }
	}
	Comparator<Article> comparator = new Comparator<Article>()
	{
	    @Override
	    public int compare(Article o1, Article o2)
	    {
		if (o1.getCreationDate().getTime() == o2.getCreationDate().getTime())
		    return 0;
		return o1.getCreationDate().getTime() < o2.getCreationDate().getTime() ? 1
			: -1;
	    }
	};
	List<Article> articleList = new ArrayList<>(set);
	Collections.sort(articleList, comparator);
	return articleList;
    }

    /**
     * Deletes article using SQL query from archive even if CascadeType.ALL is
     * presents
     *
     * @param article
     */
    public void deleteFromArchiveDirectly(Article article)
    {
	daomanager.executeHQLQuery(
			String.format("DELETE FROM %s arc WHERE arc.articleId = ?",
					Archive.class.getSimpleName()), article.getId());
    }
    public class TagManager {

	public TagsEntity getTag(String tagName)
	{
	    List<TagsEntity> list = daomanager.getInstanceList(TagsEntity.class,
		    Order.asc("name"), Restrictions.eq("name", tagName));
	    return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	public TagsEntity getTag(int id)
	{
	    List<TagsEntity> list = daomanager.getInstanceList(TagsEntity.class,
		    Order.asc("id"), Restrictions.eq("id", id));
	    return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	// additional methods
	public List<TagsEntity> getTags(Article article)
	{
	    @SuppressWarnings("unchecked")
	    List<Integer> list = daomanager
		    .executeHQLQueryAndGetList(
			    "SELECT tag_id FROM article_tags at WHERE at.article_id = :article_id",
			    article.getId());
	    List<TagsEntity> tags = new ArrayList<TagsEntity>();
	    for (Object res : list)
	    {
		tags.add(getTagsManager().getTag((int) res));
	    }
	    return tags;
	}

	public void removeAllArticleTagsDirectly(Article article)
	{
	    daomanager.executeHQLQuery(
		    "DELETE FROM article_tags WHERE article_id = :article_id",
		    article.getId());
	}

	public void removeTagFromArticleDirectly(Article article, TagsEntity tag)
	{
	    daomanager
		    .executeHQLQuery(
			    "DELETE FROM article_tags a WHERE a.article_id = :article_id AND a.tag_id = :tag_id",
			    article.getId(), tag.getId());
	}
	public Set<TagsEntity> parseTagsFromString(String string)
	{
	    HashSet<TagsEntity> set = new HashSet<>();
	    if (null != string && !string.isEmpty())
	    {
		String[] tags = string.toLowerCase().trim().replaceAll("\\s{2,}", " ")
			.split("(\\s+)|(\\s*,s*)|(,+)");
		for (String tag : tags)
		{
		    if (!tag.isEmpty())
		    {
			TagsEntity tagsEntity = ArticleManager.this.getTagsManager().getTag(tag);
			if (null == tagsEntity)
			    set.add(new TagsEntity(tag));
			else
			    set.add(tagsEntity);
		    }
		}
	    }
	    return set;
	}

    }
    public class FirstPageManager {

	/**
	 * Deletes article using SQL query from First page even if CascadeType.ALL
	 * is presents
	 *
	 * @param article
	 */
	public void removeArticleFromFirstPageDirectly(Article article)
	{
	    if (null != article)
	    {
		daomanager.executeHQLQuery(String.format(
				"DELETE FROM %s WHERE article_id = :id",
				FirstPage.class.getSimpleName()), article.getId());
	    }
	    else
	    {
		System.err.println("article == null");
	    }
	}

	public boolean updateFirstPage(FirstPage firstPage)
	{
	    return daomanager.updateInstance(firstPage);
	}
    }

}
