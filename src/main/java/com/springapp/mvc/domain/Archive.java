package com.springapp.mvc.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

import java.util.Date;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name="archive")
public class Archive implements BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value = "article"))
    @Id
    @GeneratedValue(generator="generator")
    @Column(nullable = false, unique = true, length = 11)
    private int articleId;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Article article;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    public Archive()
    {}

    public Archive(Date date)
    {
	this.date = date;
    }

    public int getArticleId()
    {
        return articleId;
    }

    public void setArticleId(int articleId)
    {
        this.articleId = articleId;
    }

    public Article getArticle()
    {
        return article;
    }

    public void setArticle(Article article)
    {
        this.article = article;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + articleId;
	result = prime * result + ((date == null) ? 0 : date.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Archive other = (Archive) obj;
	if (articleId != other.articleId)
	    return false;
	if (date == null)
	{
	    if (other.date != null)
		return false;
	}
	else if (!date.equals(other.date))
	    return false;
	return true;
    }

    
    
    
}
