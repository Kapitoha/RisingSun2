package com.springapp.mvc.domain;

import com.springapp.mvc.repository.ArticleManager;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name = "articles")
public class Article implements BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true, length = 11)
    private int id;

    @Column(nullable=false, length=255)
    private String title;

    @Lob
    private byte[] content;

    @ManyToOne
    private UsersEntity author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToOne(mappedBy = "article", cascade=CascadeType.ALL, orphanRemoval=true)
    private FirstPage firstPage;

    @OneToOne(mappedBy="article", cascade=CascadeType.ALL)
    private Archive archive;

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "article_tags", 
    joinColumns = { @JoinColumn(name = "article_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<TagsEntity> tagList = Collections.emptySet();

    @Column(name="image_url")
    private String imageUrl;

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

    public Set<TagsEntity> getTagList()
    {
	return tagList;
    }

    public void setTagList(Set<TagsEntity> tagList)
    {
	this.tagList = tagList;
    }
    
    /**
     * Parse tags from a single string
     */
    public void parseAndSetTags(String string, ArticleManager manager)
    {
	tagList.clear();
	if (null != string && !string.isEmpty())
	{
	    if (null == tagList || tagList.isEmpty())
		tagList = new HashSet<>();
	    HashSet<TagsEntity> set = new HashSet<>(tagList);
	    String[] tags = string.toLowerCase().trim().replaceAll("\\s{2,}", " ").split("(\\s+)|(\\s*,s*)|(,+)");
	    for (String tag : tags)
	    {
		if (!tag.isEmpty())
		{
		    TagsEntity tagsEntity = manager.getTagsManager().getTag(tag);
		    if (null == tagsEntity)
			set.add(new TagsEntity(tag));
		    else
			set.add(tagsEntity);
		}
	    }
	    tagList.addAll(set);
	}
    }
    
    /**
     * This method converts whole tag-list into single string
     * @return String
     */
    public String convertTagsToString()
    {
	StringBuilder sb = new StringBuilder();
	if (null != tagList && !tagList.isEmpty())
	{
	    int count = 0;
	    for (TagsEntity tagsEntity : tagList)
	    {
		sb.append(tagsEntity.getName());
		if (++count != tagList.size())
		    sb.append(", ");
	    }
	}
	return sb.toString();
    }

    public Archive getArchive()
    {
	return archive;
    }

    public void setArchive(Archive archive)
    {
	if (null != archive) archive.setArticle(this);
	this.archive = archive;
    }
    
    public String getContentText()
    {
	return (content != null && content.length > 0)? new String(getContent(), Charset.forName("UTF-8")): "";
    }
    
    public String getContentText(boolean hidePictures)
    {
//	System.out.println(getContentText().re	placeAll("<img.+>", ""));
	return hidePictures? getContentText().replaceAll("<img.+>", "") : getContentText();
    }

    public String getImageUrl()
    {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
	this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((author == null) ? 0 : author.hashCode());
	result = prime * result + Arrays.hashCode(content);
	result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
	result = prime * result + id;
	result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
	result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
	result = prime * result + ((title == null) ? 0 : title.hashCode());
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
	Article other = (Article) obj;
	if (author == null)
	{
	    if (other.author != null)
		return false;
	}
	else if (!author.equals(other.author))
	    return false;
	if (!Arrays.equals(content, other.content))
	    return false;
	if (creationDate == null)
	{
	    if (other.creationDate != null)
		return false;
	}
	else if (!creationDate.equals(other.creationDate))
	    return false;
	if (id != other.id)
	    return false;
	if (imageUrl == null)
	{
	    if (other.imageUrl != null)
		return false;
	}
	else if (!imageUrl.equals(other.imageUrl))
	    return false;
	if (tagList == null)
	{
	    if (other.tagList != null)
		return false;
	}
	else if (!tagList.equals(other.tagList))
	    return false;
	if (title == null)
	{
	    if (other.title != null)
		return false;
	}
	else if (!title.equals(other.title))
	    return false;
	return true;
    }

    

}
