package com.springapp.mvc.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "tags")
public class TagsEntity {
    private int id;
    private String name;
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
    @Column(name = "Name", nullable = false, insertable = true, updatable = true, length = 1500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagsEntity that = (TagsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tagsByIdTeg")
    public Collection<TagsarcticleEntity> getTagsarcticlesById() {
        return tagsarcticlesById;
    }

    public void setTagsarcticlesById(Collection<TagsarcticleEntity> tagsarcticlesById) {
        this.tagsarcticlesById = tagsarcticlesById;
    }
}
