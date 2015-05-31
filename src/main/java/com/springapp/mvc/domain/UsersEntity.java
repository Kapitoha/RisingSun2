package com.springapp.mvc.domain;

import javax.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "users")
public class UsersEntity implements BaseEntity {
    private int id;
    private String name;
    private String login;
    private String password;
    private Status status;
    private Collection<ArticlesEntity> articlesById = Collections.emptyList();

    private Set<AccessRight> accessList = Collections.emptySet();

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
    @Column(name = "Name", nullable = false, insertable = true, updatable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Login", nullable = false, insertable = true, updatable = true, length = 50)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Basic
    @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 50)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByAuthor")
    public Collection<ArticlesEntity> getArticlesById() {
        return articlesById;
    }

    public void setArticlesById(Collection<ArticlesEntity> articlesById) {
        this.articlesById = articlesById;
    }

//    @OneToMany(mappedBy = "usersByIdUsers", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//    @EmbeddedId
//    public Collection<UsersrulesEntity> getUsersrulesById() {
//        return usersrulesById;
//    }
//
//    public void setUsersrulesById(Collection<UsersrulesEntity> usersrulesById) {
//        this.usersrulesById = usersrulesById;
//    }
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="users_rights")
    @Column(name="access_right_id")
    public Set<AccessRight> getAccessList()
    {
	    return accessList;
    }

    public void setAccessList(Set<AccessRight> accessList)
    {
	this.accessList = accessList;
    }
}
