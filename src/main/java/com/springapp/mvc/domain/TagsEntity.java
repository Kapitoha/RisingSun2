package com.springapp.mvc.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "tags")
public class TagsEntity implements BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique=true, nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    private int id;
    @Basic
    @Column(unique=true, nullable = false, insertable = true, updatable = true, length = 255)
    private String name;
    @ManyToMany(mappedBy="tagList", fetch=FetchType.EAGER)
    private Set<Article> articles = Collections.emptySet();
    
    public TagsEntity()
    {}

    public TagsEntity(String name)
    {
	this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles()
    {
	return articles;
    }

    public void setArticles(Set<Article> articles)
    {
	this.articles = articles;
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	TagsEntity other = (TagsEntity) obj;
	if (name == null)
	{
	    if (other.name != null)
		return false;
	}
	else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString()
    {
	return "TagsEntity [id=" + id + ", name=" + name + ", articles count: " + articles.size() + "]";
    }
    
    
    
}
