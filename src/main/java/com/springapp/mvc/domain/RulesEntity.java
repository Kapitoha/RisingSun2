package com.springapp.mvc.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "rules")
public class RulesEntity {
    private int id;
    private String nameRule;
    private Collection<UsersrulesEntity> usersrulesById;

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
    @Column(name = "NameRule", nullable = false, insertable = true, updatable = true, length = 1500)
    public String getNameRule() {
        return nameRule;
    }

    public void setNameRule(String nameRule) {
        this.nameRule = nameRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RulesEntity that = (RulesEntity) o;

        if (id != that.id) return false;
        if (nameRule != null ? !nameRule.equals(that.nameRule) : that.nameRule != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameRule != null ? nameRule.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "rulesByIdRules")
    public Collection<UsersrulesEntity> getUsersrulesById() {
        return usersrulesById;
    }

    public void setUsersrulesById(Collection<UsersrulesEntity> usersrulesById) {
        this.usersrulesById = usersrulesById;
    }
}
