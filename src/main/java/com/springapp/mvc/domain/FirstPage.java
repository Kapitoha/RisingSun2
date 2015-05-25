package com.springapp.mvc.domain;

import javax.persistence.*;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name="main_page")
public class FirstPage extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(nullable=false, unique=true, length=11)
    private int id;
    @OneToOne
    @JoinColumn(name="article_id", nullable=false)
    private Article article;
    private int previousPageId;
    private boolean featured;
    
    
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public Article getArticle()
    {
        return article;
    }
    public void setArticle(Article article)
    {
        this.article = article;
    }

    public boolean isFeatured()
    {
        return featured;
    }
    public void setFeatured(boolean featured)
    {
        this.featured = featured;
    }
    public int getPreviousPageId()
    {
	return previousPageId;
    }
    public void setPreviousPageId(int previousPageId)
    {
	this.previousPageId = previousPageId;
    }
}
