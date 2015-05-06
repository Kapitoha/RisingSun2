package com.springapp.mvc.controller;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.Search;
import com.springapp.mvc.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
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
        List<ArticlesEntity> news = this.articlesRepository.newsSearch("%"+search.getSearch()+"%");
        List<Date> list=this.articlesRepository.newsArchive();
        model.addAttribute("allnews", news);
        model.addAttribute("archive", list);
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
        return "search";
    }

}
