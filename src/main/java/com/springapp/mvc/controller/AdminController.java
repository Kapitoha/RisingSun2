package com.springapp.mvc.controller;

import com.springapp.mvc.domain.AccessRight;
import com.springapp.mvc.domain.Article;
import com.springapp.mvc.domain.FirstPage;
import com.springapp.mvc.domain.Status;
import com.springapp.mvc.domain.UsersEntity;
import com.springapp.mvc.repository.AccessRightManager;
import com.springapp.mvc.repository.ArticleManager;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by kapitoha on 06.05.15.
 */
@Controller
public class AdminController {
    // ******************************* actions
    private static final int SAVE = 1;
    private static final int UPDATE = 2;
    // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessRightManager accessManger;
    @Autowired
    private ArticleManager articleManager;

    @RequestMapping(value = "/admin")
    public ModelAndView showAdmin()
    {
	ModelAndView view = new ModelAndView();
	return view;
    }

    @RequestMapping(value = "/admin-users")
    public ModelAndView showUsers()
    {
	ModelAndView view = new ModelAndView();
	List<UsersEntity> list = getUserRepository().getAllUsers();
	view.setViewName("/admin");
	view.addObject("page_tag", "users_show");
	view.addObject("user_list", list);
	return view;
    }
    
    @PreAuthorize("hasRole('EDIT_USER')")
    @RequestMapping(value = "/admin-user-edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute(value = "user") UsersEntity user,
	    HttpServletRequest request)
    {
	ModelAndView view = new ModelAndView();
	view.setViewName("/admin");
	if (null != user && user.getId() > 0)
	{
	    user = getUserRepository().getUser(user.getId());
	}
	else
	{
	    user = new UsersEntity();
	    user.setAccessList(new ArrayList<AccessRight>());
	}
	List<AccessRight> rulesList = accessManger.getAccessRightsList();
	//service solution delete if necessary
	if (rulesList == null || rulesList.isEmpty())
	{
	    rulesList = accessManger.createAndGetDefaultRights();
	}
	view.addObject("page_tag", "user-edit");
	view.addObject("user", user);
	view.addObject("rules_list", rulesList);
	view.addObject("status_list", Status.values());
	return view;
    }
    @PreAuthorize("hasRole('EDIT_USER')")
    @RequestMapping(value = "/admin-user-create")
    public ModelAndView createUser(HttpServletRequest request)
    {
	return editUser(new UsersEntity(), request);
    }

    @PreAuthorize("hasRole('EDIT_USER')")
    @RequestMapping(value = "/admin-user-save", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute("user") UsersEntity user,
	    HttpServletRequest request, RedirectAttributes attr)
    {
	UsersEntity userInstance = user;
	Status status = Status.valueOf(request.getParameter("status"));
	if (null == userInstance)
	{
	    System.out.println("create new user");
	    userInstance = new UsersEntity();
	}

	/*
	 * Check fields
	 */
	if (!StringUtils.checkIfAllowed(userInstance.getName()))
	{
	    request.setAttribute(
		    "error_msg",
		    String.format(
			    "Allowed only <strong><em>%s</em></strong> characters in 'Name' field",
			    StringUtils.FIELD_NAME_PATTERN));
	    return editUser(userInstance, request);
	}
	if (!StringUtils.checkIfAllowed(userInstance.getLogin()))
	{
	    request.setAttribute(
		    "error_msg",
		    String.format(
			    "Allowed only <strong><em>%s</em></strong> characters in 'login' field",
			    StringUtils.FIELD_NAME_PATTERN));
	    return editUser(userInstance, request);
	}
	// read access rights
	List<AccessRight> newAccessRightsList = new ArrayList<AccessRight>();
	String[] accessRightsArray = request.getParameterValues("access_right");
	if (null != accessRightsArray && accessRightsArray.length > 0)
	{
	    for (String rights : accessRightsArray)
	    {
		int right_id = 0;
		try
		{
		    right_id = Integer.valueOf(rights);
		    newAccessRightsList.add(accessManger.getAccessRightById(right_id));
		}
		catch (NumberFormatException ne)
		{
		    System.err.println("wrong id of rule: " + ne.getLocalizedMessage());
		    continue;
		}
	    }
	}
	// Rewrite access rights with new values
	userInstance.getAccessList().clear();
	userInstance.setAccessList(newAccessRightsList);

	/*
	 * Save / update actions
	 */
	String result_msg = null;
	int action = 0;
	List<UsersEntity> existedUsersList = userRepository
		.getUsersByFields(userInstance);
	if (null == existedUsersList || existedUsersList.isEmpty())
	{
	    // save
	    action = SAVE;
	}
	else
	{
	    // check for duplicates
	    for (UsersEntity existedUser : existedUsersList)
	    {
		if (existedUser.getName().equals(userInstance.getName())
			&& existedUser.getId() != userInstance.getId())
		{
		    // same name
		    request.setAttribute("error_msg",
			    "This Name is already presented. Choose other please.");
		    return editUser(userInstance, request);
		}
		if (existedUser.getLogin().equals(userInstance.getLogin())
			&& existedUser.getId() != userInstance.getId())
		{
		    // same login
		    request.setAttribute("error_msg",
			    "This Login is already presented. Choose other please.");
		    return editUser(userInstance, request);
		}
	    }
	    if (userInstance.getId() == 0)
	    {
		action = SAVE;
	    }
	    else
	    {
		// update
		action = UPDATE;
	    }
	}
	userInstance.setStatus(status);
	switch (action)
	{
	    case SAVE: // save action
		System.out.println("save");
		if (userRepository.saveUser(userInstance))
		    result_msg = "Saving was successful";
		else
		{
		    request.setAttribute("error_msg", "Saving was failed.");
		    return editUser(userInstance, request);
		}
		break;
	    case UPDATE: // update action
		System.out.println("update");
		if (!userRepository.updateUser(userInstance))
		{
		    request.setAttribute("error_msg", "Update was failed.");
		    return editUser(userInstance, request);
		}
		else
		    result_msg = "Update was successful";
		break;

	    default:
		break;
	}
	ModelAndView finalView = new ModelAndView("redirect:/admin");
	attr.addFlashAttribute("info_msg", result_msg);
	return finalView;
    }

    // ============================================================== Articles
    @RequestMapping("/admin-articles-print")
    public ModelAndView printArticles(HttpServletRequest request)
    {
	ModelAndView view = new ModelAndView();
	view.setViewName("/admin");
	List<Article> articlesList = articleManager.getArticles();
	view.addObject("articles_list", articlesList);
	view.addObject("page_tag", "articles_print");
	return view;
    }
    @PreAuthorize("hasAnyRole('EDIT_ARTICLE_MASTER', 'EDIT_ARTICLE_AUTHOR')")
    @RequestMapping("/admin-article-edit")
    public ModelAndView editArticle(@ModelAttribute Article article,
	    HttpServletRequest request)
    {
	ModelAndView view = new ModelAndView("/admin");
	// FirstPage firstPage = articleManager.getFirstPage(article);
	view.addObject("page_tag", "articles_edit");
	view.addObject("article_obj", article);
	view.addObject("first_page", article.getFirstPage());
	System.out.println("Articles first page" + article.getFirstPage());
	return view;
    }
    @PreAuthorize("hasRole('CREATE_ARTICLE')")
    @RequestMapping("/admin-article-create")
    public ModelAndView createArticle(HttpServletRequest request)
    {
	return editArticle(new Article(), request);
    }
    @PreAuthorize("hasAnyRole('DELETE_ARTICLE_MASTER', 'DELETE_ARTICLE_AUTHOR')")
    @RequestMapping("/admin-article-delete")
    public ModelAndView deleteArticle(@ModelAttribute Article article,
	    RedirectAttributes attributes)
    {
	if (articleManager.deleteArticle(article))
	{
	    attributes.addFlashAttribute("info_msg", String.format(
		    "Article '%s' deleted successfully", article.getTitle()));
	}
	else
	    attributes.addFlashAttribute("error_msg", "Deletion was failed");
	return new ModelAndView(new RedirectView("admin"));
    }
    
    @PreAuthorize("hasRole('CREATE_ARTICLE')")
    @RequestMapping("/admin-article-save")
    public ModelAndView saveArticle(@ModelAttribute Article articleInstance,
	    HttpServletRequest request, RedirectAttributes attributes)
    {
	int action = 0;
	String msg = null;
	boolean isOnFirstPage = request.getParameter("show_main") != null;
	boolean isFeatured = request.getParameter("featured") != null;
	Article article = articleInstance;
	String content = request.getParameter("editor");
	if (null == articleInstance)
	    article = new Article();
	article.setContent(content);
	if (isOnFirstPage)
	{
	    if (article.getFirstPage() == null)
		article.setFirstPage(new FirstPage());
	    article.getFirstPage().setFeatured(isFeatured);
	}
	if (article.getId() > 0)
	{
	    action = UPDATE;
	}
	else
	    action = SAVE;
	switch (action)
	{
	    case SAVE:
		if (articleManager.saveArticle(article))
		    msg = "Saving was successful";
		else
		{
		    return editArticle(article, request);
		}
		break;
	    case UPDATE:
		if (isOnFirstPage)
		{
		    FirstPage firstPage = new FirstPage();
		    firstPage.setPreviousPageId(articleManager.getLastMainPageId());
		}
		if (articleManager.updateArticle(article))
		    msg = "Article updated successfuly";
		else
		{
		    return editArticle(article, request);
		}
		break;

	    default:
		break;
	}
	System.out.println("Articles first page on saving: " + article.getFirstPage());
	attributes.addFlashAttribute("info_msg", msg);
	return new ModelAndView(new RedirectView("admin"));
    }

    @RequestMapping("login")
    public ModelAndView login()
    {
	return new ModelAndView();
    }
    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request)
    {
	try
	{
	    request.logout();
	}
	catch (ServletException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return new ModelAndView(new RedirectView(request.getContextPath()));
    }

    // =============================================================== Getters /
    // Setters

    public UserRepository getUserRepository()
    {
	return userRepository;
    }

    public void setUserRepository(UserRepository userRepository)
    {
	this.userRepository = userRepository;
    }

    public AccessRightManager getAccessManger()
    {
	return accessManger;
    }

    public void setAccessManger(AccessRightManager accessManger)
    {
	this.accessManger = accessManger;
    }

    public ArticleManager getArticleManager()
    {
	return articleManager;
    }

    public void setArticleManager(ArticleManager articleManager)
    {
	this.articleManager = articleManager;
    }

}
