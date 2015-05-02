package com.springapp.mvc.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "tablename")
public class TablenameEntity {
    private int id;
    private String tableName;
    private Collection<AccesstableEntity> accesstablesById;

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
    @Column(name = "TableName", nullable = false, insertable = true, updatable = true, length = 100)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TablenameEntity that = (TablenameEntity) o;

        if (id != that.id) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tablenameByIdTable")
    public Collection<AccesstableEntity> getAccesstablesById() {
        return accesstablesById;
    }

    public void setAccesstablesById(Collection<AccesstableEntity> accesstablesById) {
        this.accesstablesById = accesstablesById;
    }
}
