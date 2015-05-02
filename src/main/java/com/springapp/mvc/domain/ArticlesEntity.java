package com.springapp.mvc.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "articles")
public class ArticlesEntity {
    private int id;
    private String title;
    private String article;
    private String namePage;
    private Timestamp dateCreate;
    private UsersEntity usersByAuthor;
    private Collection<FirstpageEntity> firstpagesById;
    private Collection<TagsarcticleEntity> tagsarcticlesById;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Title", nullable = false, insertable = true, updatable = true, length = 65535)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Article", nullable = false, insertable = true, updatable = true, length = 65535)
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Basic
    @Column(name = "NamePage", nullable = false, insertable = true, updatable = true, length = 1500)
    public String getNamePage() {
        return namePage;
    }

    public void setNamePage(String namePage) {
        this.namePage = namePage;
    }

    @Basic
    @Column(name = "DateCreate", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlesEntity that = (ArticlesEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (article != null ? !article.equals(that.article) : that.article != null) return false;
        if (namePage != null ? !namePage.equals(that.namePage) : that.namePage != null) return false;
        if (dateCreate != null ? !dateCreate.equals(that.dateCreate) : that.dateCreate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (article != null ? article.hashCode() : 0);
        result = 31 * result + (namePage != null ? namePage.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Author", referencedColumnName = "ID", nullable = false)
    public UsersEntity getUsersByAuthor() {
        return usersByAuthor;
    }

    public void setUsersByAuthor(UsersEntity usersByAuthor) {
        this.usersByAuthor = usersByAuthor;
    }

    @OneToMany(mappedBy = "articlesByArticleId")
    public Collection<FirstpageEntity> getFirstpagesById() {
        return firstpagesById;
    }

    public void setFirstpagesById(Collection<FirstpageEntity> firstpagesById) {
        this.firstpagesById = firstpagesById;
    }

    @OneToMany(mappedBy = "articlesByIdArcticle")
    public Collection<TagsarcticleEntity> getTagsarcticlesById() {
        return tagsarcticlesById;
    }

    public void setTagsarcticlesById(Collection<TagsarcticleEntity> tagsarcticlesById) {
        this.tagsarcticlesById = tagsarcticlesById;
    }
}
