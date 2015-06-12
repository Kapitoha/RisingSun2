package com.springapp.mvc.domain;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name="first_page")
public class FirstPage implements BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="article_id", unique=true, nullable=false, length = 11)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="article"))
    private int article_id;
    @Column(nullable = false)
    private boolean featured;
    @Column(length = 11)
    private int show_order;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Article article;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getShow_order() {
        return show_order;
    }

    public void setShow_order(int show_order) {
        this.show_order = show_order;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FirstPage firstPage = (FirstPage) o;

        return article_id == firstPage.article_id;

    }

    @Override
    public int hashCode()
    {
        return article_id;
    }

    @Override
    public String toString()
    {
        return "FirstPage{" +
                        "article_id=" + article_id +
                        ", featured=" + featured +
                        ", show_order=" + show_order +
                        '}';
    }
}
