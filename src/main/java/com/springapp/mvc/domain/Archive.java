package com.springapp.mvc.domain;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author kapitoha
 *
 */
@Entity
@Table(name="archive")
public class Archive {
    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value = "article"))
    @Id
    @GeneratedValue(generator="generator")
    private int articleId;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Article article;
}
