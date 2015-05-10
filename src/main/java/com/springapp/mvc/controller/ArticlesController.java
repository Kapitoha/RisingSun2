package com.springapp.mvc.controller;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.Search;
import com.springapp.mvc.domain.TagsEntity;
import com.springapp.mvc.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Alex on 01.05.2015.
 */
@Controller
public class ArticlesController {

    private ArticlesRepository articlesRepository;


    @Autowired
    public ArticlesController(ArticlesRepository articlesRepository){
        this.articlesRepository=articlesRepository;
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(@ModelAttribute("search") Search search, BindingResult bindingResult, Model model) {
        List<ArticlesEntity> news = this.articlesRepository.newsSearch("%" + search.getSearch() + "%");
        List<Date> list=this.articlesRepository.newsArchive();
        model.addAttribute("allnews", news);
        model.addAttribute("archive", list);
        model.addAttribute("searchr", " Search by value:"+search.getSearch());
        return "search";
    }

    @ModelAttribute("search")
    public Search search() {
        return new Search();
    }

    @RequestMapping(value = "archive/{date}", method = RequestMethod.GET)
    public String search(@PathVariable Date date, Model model) {
        List<ArticlesEntity> news = this.articlesRepository.newsArchive(date);
        List<Date> list=this.articlesRepository.newsArchive();
        model.addAttribute("allnews", news);
        model.addAttribute("archive", list);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        model.addAttribute("searchr", " Archive from:"+ dateFormat.format(date));
        return "search";
    }

    @RequestMapping(value = "tags/{tag}", method = RequestMethod.GET)
    public String search(@PathVariable String tag,  Model model) {
        List<ArticlesEntity> news = this.articlesRepository.newsSearchByTag(tag);
        List<Date> list=this.articlesRepository.newsArchive();
        TagsEntity tagE=this.articlesRepository.maxCountByTag(tag);
        model.addAttribute("allnews", news);
        model.addAttribute("archive", list);
        model.addAttribute("searchr", " Search by tag:<a href=\"/tags/"+tag+"\">"+tag+"</a>, this tag often to meet with <a href=\"/tags/"+tagE.getName()+"\">"+tagE.getName()+"</a>");
        return "search";
    }

}
