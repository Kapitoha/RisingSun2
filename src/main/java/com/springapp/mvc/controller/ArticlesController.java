package com.springapp.mvc.controller;

import com.springapp.mvc.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getArchive(Model model) {
        List<String> archive = this.articlesRepository.newsArchive();

        model.addAttribute("archive", archive);

        return "jspf/content-box-right";
    }


}
