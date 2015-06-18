package com.springapp.mvc.domain;

import com.springapp.mvc.repository.ArticleManager;
import com.springapp.mvc.utils.StringUtils;

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
    @Column(length=5, columnDefinition="INTEGER default 40")
    private Integer titleFontSize;
    @Column(columnDefinition="varchar(8) default '#000000'")
    private String titleColor;

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
	return StringUtils.clearFromJavaScriptInjection(title);
    }

    public void setTitle(String title)
    {
	this.title = StringUtils.clearHTMLTags(title);
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
	    this.content = StringUtils.clearFromJavaScriptInjection(content).getBytes();
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
	    String[] tags = StringUtils.clearFromJavaScriptInjection(string).toLowerCase().trim().replaceAll("\\s{2,}",
			" ").split("(\\s+)|(\\s*,s*)|(,+)");
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
	return (content != null && content.length > 0)? StringUtils.clearFromJavaScriptInjection(new String
			(getContent(), Charset.forName("UTF-8"))): "";
    }
    
    public String getContentText(boolean hidePictures)
    {
	return hidePictures? getContentText().replaceAll("<img.+>", "") : getContentText();
    }

    public String getImageUrl()
    {
	return StringUtils.clearHTMLTags(imageUrl);
    }

    public void setImageUrl(String imageUrl)
    {
	this.imageUrl = StringUtils.clearHTMLTags(imageUrl);;
    }

    public int getTitleFontSize()
    {
        return (titleFontSize == null || titleFontSize <= 0)? 40 : titleFontSize;
    }

    public void setTitleFontSize(int titleFontSize)
    {
        this.titleFontSize = titleFontSize;
    }

    public String getTitleColor()
    {
        return null == titleColor? "#000000" : titleColor;
    }

    public void setTitleColor(String titleColor)
    {
        this.titleColor = titleColor == null || titleColor.isEmpty()? "#000000" : titleColor;
    }

    @Override
    public boolean equals(Object o)
    {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;

	Article article = (Article) o;

	if (id != article.id)
	    return false;
	if (author != null ? !author.equals(article.author) : article.author != null)
	    return false;
	return !(creationDate != null ? !creationDate.equals(article.creationDate) : article.creationDate != null);

    }

    @Override
    public int hashCode()
    {
	int result = id;
	result = 31 * result + (author != null ? author.hashCode() : 0);
	result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
	return result;
    }
}
