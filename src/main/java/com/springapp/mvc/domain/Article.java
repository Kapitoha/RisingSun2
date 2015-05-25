package com.springapp.mvc.domain;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name = "articles_all")
public class Article extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true, length = 11)
    private int id;
    @Column(nullable=false)
    private String title;
    @Lob
    private byte[] content;
    @ManyToOne
    private UsersEntity author;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
//    @JoinTable(name = "main_page", joinColumns={@JoinColumn(name="article_id")})
    @OneToOne(mappedBy="article", cascade=CascadeType.ALL)
    private FirstPage firstPage;
//    @JoinTable(name = "archive", joinColumns={@JoinColumn(name="article_id")})
//    @OneToOne
//    private Archive archive;
    @ManyToMany
    @JoinTable(name = "article_tags", 
    joinColumns = { @JoinColumn(name = "article_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<TagsEntity> tagList;

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getTitle()
    {
	return title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public byte[] getContent()
    {
	return content;
    }

    public void setContent(byte[] content)
    {
	this.content = content;
    }
    
    public void setContent(String content)
    {
	if (content != null)
	    this.content = content.getBytes();
    }

    public UsersEntity getAuthor()
    {
	return author;
    }

    public void setAuthor(UsersEntity author)
    {
	this.author = author;
    }

    public Date getCreationDate()
    {
	return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
	this.creationDate = creationDate;
    }

    public FirstPage getFirstPage()
    {
	return firstPage;
    }

    public void setFirstPage(FirstPage firstPage)
    {
	if (null != firstPage)firstPage.setArticle(this);
	this.firstPage = firstPage;
    }

    public List<TagsEntity> getTagList()
    {
	return tagList;
    }

    public void setTagList(List<TagsEntity> tagList)
    {
	this.tagList = tagList;
    }

//    public Archive getArchive()
//    {
//	return archive;
//    }
//
//    public void setArchive(Archive archive)
//    {
//	this.archive = archive;
//    }
    
    public String getContentText()
    {
	return (content != null && content.length > 0)? new String(getContent(), Charset.forName("UTF-8")): "";
    }
}
