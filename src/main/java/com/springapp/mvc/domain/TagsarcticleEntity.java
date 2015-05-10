package com.springapp.mvc.domain;

import javax.persistence.*;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "tagsarcticle")
public class TagsarcticleEntity extends BaseEntity {
    private int id;
    private ArticlesEntity articlesByIdArcticle;
    private TagsEntity tagsByIdTeg;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagsarcticleEntity that = (TagsarcticleEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Arcticle", referencedColumnName = "ID", nullable = false)
    public ArticlesEntity getArticlesByIdArcticle() {
        return articlesByIdArcticle;
    }

    public void setArticlesByIdArcticle(ArticlesEntity articlesByIdArcticle) {
        this.articlesByIdArcticle = articlesByIdArcticle;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Teg", referencedColumnName = "ID", nullable = false)
    public TagsEntity getTagsByIdTeg() {
        return tagsByIdTeg;
    }

    public void setTagsByIdTeg(TagsEntity tagsByIdTeg) {
        this.tagsByIdTeg = tagsByIdTeg;
    }
}
