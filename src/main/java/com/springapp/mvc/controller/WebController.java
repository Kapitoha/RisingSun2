package com.springapp.mvc.controller;

import com.springapp.mvc.domain.Article;
import com.springapp.mvc.domain.FirstPage;
import com.springapp.mvc.domain.TagsEntity;
import com.springapp.mvc.domain.UsersEntity;
import com.springapp.mvc.repository.ArticleManager;
import com.springapp.mvc.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by kapitoha on 01.06.15.
 */
@Controller
public class WebController {
    @Autowired
    private UserRepository userManager;
    @Autowired
    private ArticleManager articleManager;

    @RequestMapping("/")
    public ModelAndView main()
    {
	ModelAndView view = new ModelAndView("/index");
	Set<FirstPage> firstPages = articleManager.getFirstPages();
	view.addObject("page_tag", "first_page");
	view.addObject("firstPages", firstPages);
	return view;
    }

    @RequestMapping("index")
    public ModelAndView index()
    {
	return main();
    }

    @RequestMapping(value = "article-view")
    public ModelAndView viewArticle(Integer id, HttpServletRequest request)
    {
	ModelAndView view = new ModelAndView("/index");
	view.addObject("page_tag", "article_view");
	view.addObject("article_obj", articleManager.getArticle(id));
	return view;
    }

    @RequestMapping(value = "search")
    public ModelAndView search(HttpServletRequest request)
    {
	Set<Article> result = new LinkedHashSet<>();
	boolean isCompositSearch = request.getParameter("composite") != null;
	UsersEntity user = userManager.parseUser(request.getParameter("author"));
	Set<TagsEntity> tagList = articleManager.getTagsManager().parseTagsFromString(
		request.getParameter("tag"));
	TagsEntity[] tagArray = tagList.toArray(new TagsEntity[tagList.size()]);

	result.addAll(articleManager.searchArticleByCriterion(isCompositSearch,
		request.getParameter("keyword"), user, tagArray));
	ModelAndView view = new ModelAndView("index");
	view.addObject("result_list", new ArrayList<Article>(result));
	view.addObject("page_tag", "search");
	view.addObject("key_word", request.getParameter("keyword"));
	return view;
    }

    @RequestMapping(value = "archive")
    public ModelAndView viewArchive(HttpServletRequest request)
    {
	ModelAndView view = new ModelAndView("index");
	view.addObject("result_list", new ArrayList<Article>(articleManager.getArchivedArticles()));
	view.addObject("page_tag", "archive");
	return view;
    }
}
