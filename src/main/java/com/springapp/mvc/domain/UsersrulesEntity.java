package com.springapp.mvc.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by Alex on 30.04.2015.
 */
//@Entity
@Table(name = "usersrules")
@Embeddable
public class UsersrulesEntity extends BaseEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Transient
    private int id;
    private RulesEntity rulesByIdRules;
    private UsersEntity usersByIdUsers;

//    @Id
//    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
//    @GeneratedValue
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersrulesEntity that = (UsersrulesEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Rules", referencedColumnName = "ID", nullable = false)
    @Id
    public RulesEntity getRulesByIdRules() {
        return rulesByIdRules;
    }

    public void setRulesByIdRules(RulesEntity rulesByIdRules) {
        this.rulesByIdRules = rulesByIdRules;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Users", referencedColumnName = "ID", nullable = false)
    @Id
    public UsersEntity getUsersByIdUsers() {
        return usersByIdUsers;
    }

    public void setUsersByIdUsers(UsersEntity usersByIdUsers) {
        this.usersByIdUsers = usersByIdUsers;
    }
}
