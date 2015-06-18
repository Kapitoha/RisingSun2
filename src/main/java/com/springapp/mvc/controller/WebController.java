package com.springapp.mvc.controller;

import com.springapp.mvc.domain.Article;
import com.springapp.mvc.domain.FirstPage;
import com.springapp.mvc.domain.TagsEntity;
import com.springapp.mvc.domain.UsersEntity;
import com.springapp.mvc.repository.ArticleManager;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.utils.CollectionTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
	ModelAndView view = new ModelAndView("index");
	Set<Article> result = new LinkedHashSet<>();
	boolean isCompositSearch = request.getParameter("composite") != null;
	UsersEntity user = userManager.parseUser(request.getParameter("author"));
	Set<TagsEntity> tagList = articleManager.getTagsManager().parseTagsFromString(
		request.getParameter("tag"));
	TagsEntity[] tagArray = tagList.toArray(new TagsEntity[tagList.size()]);

	result.addAll(articleManager.searchArticleByCriterion(isCompositSearch,
		request.getParameter("keyword"), user, tagArray));
	if (tagList.size() == 1)
	{
	    TagsEntity tag = tagArray[0];
	    Map<TagsEntity, Integer> map = new HashMap<TagsEntity, Integer>();
	    for (Article article : tag.getArticles())
	    {
		for (TagsEntity tagsEntity : article.getTagList())
		{
		    Integer count = (map.get(tagsEntity) == null)? 0 : map.get(tagsEntity) + 1;
		    map.put(tagsEntity, count);
		}
	    }
	    Map<TagsEntity, Integer>sortedTagsMap = new LinkedHashMap<>(3);
	    Map<TagsEntity, Integer>tmp = CollectionTag.sortMapByValues(map, true);
	    tmp.remove(tag);
	    int i = 0;
	    for (Map.Entry<TagsEntity, Integer> pair: tmp.entrySet())
	    {
		if (++i > 3) break;
		else
		    sortedTagsMap.put(pair.getKey(), pair.getValue());
	    }
	    view.addObject("goal_tag", tag);
	    view.addObject("relative_tags", sortedTagsMap.keySet());
	}
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
