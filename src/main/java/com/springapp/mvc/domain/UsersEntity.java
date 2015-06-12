package com.springapp.mvc.domain;

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
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 11)
    @GeneratedValue
    private int id;
    @Basic
    @Column(name = "Name", nullable = false, insertable = true, updatable = true, length = 50)
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

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
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
        this.name = name;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
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
	UsersEntity other = (UsersEntity) obj;
	if (id != other.id)
	    return false;
	if (login == null)
	{
	    if (other.login != null)
		return false;
	}
	else if (!login.equals(other.login))
	    return false;
	if (name == null)
	{
	    if (other.name != null)
		return false;
	}
	else if (!name.equals(other.name))
	    return false;
	if (password == null)
	{
	    if (other.password != null)
		return false;
	}
	else if (!password.equals(other.password))
	    return false;
	if (status != other.status)
	    return false;
	return true;
    }
    
    
}
