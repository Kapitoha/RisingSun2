package com.springapp.mvc.domain;

import com.springapp.mvc.utils.StringUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "users")
public class UsersEntity implements BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    private int id;
    @Basic
    @Column(name = "Name", nullable = false, insertable = true, updatable = true, length = 100)
    private String name;
    @Basic
    @Column(name = "Login", nullable = false, insertable = true, updatable = true, length = 50)
    private String login;
    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 50)
    private String password;
    @Basic
    @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 50)
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Article> articles = Collections.emptyList();
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="users_rights")
    @Column(name="access_right_id")
    private Set<AccessRight> accessList = Collections.emptySet();


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
        this.name = StringUtils.decodeString(name);
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = StringUtils.decodeString(login);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringUtils.decodeString(password);
    }



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

    public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articlesById) {
        this.articles = articlesById;
    }

    public Set<AccessRight> getAccessList()
    {
	    return accessList;
    }

    public void setAccessList(Set<AccessRight> accessList)
    {
	this.accessList = accessList;
    }
}
