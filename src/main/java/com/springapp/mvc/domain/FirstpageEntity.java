package com.springapp.mvc.domain;

import javax.persistence.*;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "firstpage")
public class FirstpageEntity extends BaseEntity {
    private int id;
    private int raiting;
    private ArticlesEntity articlesByArticleId;

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
    @Column(name = "Raiting", nullable = false, insertable = true, updatable = true)
    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FirstpageEntity that = (FirstpageEntity) o;

        if (id != that.id) return false;
        if (raiting != that.raiting) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + raiting;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Article_ID", referencedColumnName = "ID", nullable = false)
    public ArticlesEntity getArticlesByArticleId() {
        return articlesByArticleId;
    }

    public void setArticlesByArticleId(ArticlesEntity articlesByArticleId) {
        this.articlesByArticleId = articlesByArticleId;
    }
}
