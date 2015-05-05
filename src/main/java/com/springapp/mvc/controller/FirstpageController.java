package com.springapp.mvc.controller;

import com.springapp.mvc.domain.FirstpageEntity;
import com.springapp.mvc.domain.Search;
import com.springapp.mvc.repository.FirstPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Alex on 01.05.2015.
 */
@Controller
public class FirstpageController {

    private FirstPageRepository firstPageRepository;


    @Autowired
    public FirstpageController(FirstPageRepository firstPageRepository){
        this.firstPageRepository=firstPageRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getNews(Model model) {
        List<FirstpageEntity> news = this.firstPageRepository.listAll();

        model.addAttribute("allnews", news);

        return "index";
    }

    @RequestMapping(value = "news/{name}", method = RequestMethod.GET)
    public String getNewsByName(@PathVariable String name, Model model) {
        List<FirstpageEntity> news = this.firstPageRepository.newsByName(name);

        model.addAttribute("allnews", news);

        return "news";
    }

    @ModelAttribute("search")
    public Search search() {
        return new Search();
    }


}
