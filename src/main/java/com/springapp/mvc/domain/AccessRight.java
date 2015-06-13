package com.springapp.mvc.domain;

import com.springapp.mvc.utils.StringUtils;

import javax.persistence.*;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name="access_rights")
public class AccessRight implements BaseEntity {
    @Id
    @GeneratedValue
    @Column(nullable=false, unique= true, length=11)
    private int id;
    @Column(nullable=false, unique=true, length=100)
    private String description;
    //default constructor. Don't remove!!!
    public AccessRight(){}
    
    
    public AccessRight(String description)
    {
	setDescription(description);
    }


    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getDescription()
    {
        return StringUtils.clearHTMLTags(description);
    }
    public void setDescription(String description)
    {
        this.description = StringUtils.clearHTMLTags(description);
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((description == null) ? 0 : description.hashCode());
	result = prime * result + id;
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
	AccessRight other = (AccessRight) obj;
	if (description == null)
	{
	    if (other.description != null)
		return false;
	}
	else if (!description.equals(other.description))
	    return false;
	if (id != other.id)
	    return false;
	return true;
    }
    

}
