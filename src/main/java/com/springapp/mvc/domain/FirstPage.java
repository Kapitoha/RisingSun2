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
    @Id
    @Column(name="article_id", unique=true, nullable=false)
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
}
