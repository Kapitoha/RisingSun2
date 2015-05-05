package com.springapp.mvc.controller;

import com.springapp.mvc.domain.ArticlesEntity;
import com.springapp.mvc.domain.Search;
import com.springapp.mvc.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
        model.addAttribute("allnews", news);

        return "search";
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String getArchive(Model model) {
//        List<String> archive = this.articlesRepository.newsArchive();
//
//        model.addAttribute("archive", archive);
//
//        return "jspf/content-box-right";
//    }


}
