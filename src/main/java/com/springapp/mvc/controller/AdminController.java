package com.springapp.mvc.controller;

import com.springapp.mvc.domain.*;
import com.springapp.mvc.repository.AccessRightManager;
import com.springapp.mvc.repository.ArticleManager;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

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
	if (!isMatchesAccessRights(Right.EDIT_USER.toString()))
	{
	    return accessDenied();
	}
	ModelAndView view = new ModelAndView();
	view.setViewName("/admin");
	if (null != user && user.getId() > 0)
	{
	    user = getUserRepository().getUser(user.getId());
	}
	else
	{
	    user = new UsersEntity();
	    user.setAccessList(new HashSet<AccessRight>());
	}
	Collection<AccessRight> rulesList = accessManger.getAccessRightsList();
	// service solution delete if necessary
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
	if (!isMatchesAccessRights(Right.EDIT_USER.toString()))
	    return accessDenied();
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
	Set<AccessRight> newAccessRightsList = new LinkedHashSet<>();
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
	    HttpServletRequest request) throws UnsupportedEncodingException
    {
	if (!isMatchesAccessRights(Right.EDIT_ARTICLE_MASTER.toString()))
	{
	    Principal userPrincipal = request.getUserPrincipal();
	    if (isMatchesAccessRights(Right.EDIT_ARTICLE_AUTHOR.toString())
		    && !userPrincipal.getName().equals(article.getAuthor().getLogin()))
	    {
		return accessDenied();
	    }

	}
	return manageArticle(article, request);
    }

    private ModelAndView manageArticle(@ModelAttribute Article article,
	    HttpServletRequest request) throws UnsupportedEncodingException
    {
	request.setCharacterEncoding("UTF-8");
	Article art = article.getId() > 0 ? articleManager.getArticle(article.getId())
		: article;
	ModelAndView view = new ModelAndView("/admin");
	// FirstPage firstPage = articleManager.getFirstPage(article);
	view.addObject("page_tag", "articles_edit");
	view.addObject("article_obj", art);
	view.addObject("first_page",
		null != art && art.getFirstPage() != null ? art.getFirstPage() : null);
	return view;

    }

    @PreAuthorize("hasRole('CREATE_ARTICLE')")
    @RequestMapping("/admin-article-create")
    public ModelAndView createArticle(HttpServletRequest request) throws UnsupportedEncodingException
    {
	if (!isMatchesAccessRights(Right.CREATE_ARTICLE.toString()))
	    return accessDenied();
	return manageArticle(new Article(), request);
    }

    @PreAuthorize("hasAnyRole('DELETE_ARTICLE_MASTER', 'DELETE_ARTICLE_AUTHOR')")
    @RequestMapping("/admin-article-delete")
    public ModelAndView deleteArticle(@ModelAttribute Article article,
	    HttpServletRequest request, RedirectAttributes attributes)
    {
	if (!isMatchesAccessRights(Right.DELETE_ARTICLE_MASTER.toString()))
	{
	    Principal userPrincipal = request.getUserPrincipal();
	    if (isMatchesAccessRights(Right.DELETE_ARTICLE_AUTHOR.toString())
		    && !userPrincipal.getName().equals(article.getAuthor().getLogin()))
	    {
		return accessDenied(); 
	    }

	}
	articleManager.getTagsManager().removeAllArticleTagsDirectly(article);
	article.getTagList().clear();
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
    @RequestMapping(value="/admin-article-save", method=RequestMethod.POST)
    public ModelAndView saveArticle(@ModelAttribute Article articleInstance,
	    HttpServletRequest request, RedirectAttributes attributes) throws UnsupportedEncodingException
    {
	request.setCharacterEncoding("UTF-8");
	int action = 0;
	String msg = null;
	boolean isArchived = request.getParameter("archived") != null;
	boolean isOnFirstPage = request.getParameter("show_main") != null && !isArchived;
	boolean isFeatured = request.getParameter("featured") != null;
	String tags = request.getParameter("tags");
	Article article = articleInstance.getId() > 0 ? articleManager
		.getArticle(articleInstance.getId()) : articleInstance;
	String content = request.getParameter("editor");
	article.setTitle(request.getParameter("title"));
	article.setContent(StringUtils.decodeString(content));
	article.setAuthor(userRepository.getUser(StringUtils.decodeString(request
		.getUserPrincipal().getName())));
	article.setImageUrl(request.getParameter("image"));
	article.parseAndSetTags(StringUtils.decodeString(tags), articleManager);
	// First page handler
	if (isOnFirstPage)
	{
	    if (article.getFirstPage() == null)
		article.setFirstPage(new FirstPage());
	    article.getFirstPage().setFeatured(isFeatured);
	}
	else
	{
	    if (article.getFirstPage() != null)
	    {
		articleManager.removeArticleFromFirstPageDirectly(article);
		article.setFirstPage(null);
	    }
	}
	//archive handler
	if (isArchived)
	{
	    if (article.getArchive() == null) article.setArchive(new Archive(new Date()));
	}
	else
	{
	    if (article.getArchive() != null) 
	    {
		articleManager.deleteFromArchiveDirectly(article);
		article.setArchive(null);
	    }
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
	attributes.addFlashAttribute("info_msg", msg);
	return new ModelAndView(new RedirectView("admin"));
    }

    @RequestMapping(value = "admin-article-view")
    public ModelAndView viewArticle(@ModelAttribute Article article,
	    HttpServletRequest request)
    {
	ModelAndView view = new ModelAndView("/admin");
	view.addObject("page_tag", "article_view");
	view.addObject("article_obj", articleManager.getArticle(article.getId()));
	System.out.println("Article id: " + article.getId() + " | " + article.getTitle());
	return view;
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

    public boolean isMatchesAccessRights(String... roles)
    {
	@SuppressWarnings("unchecked")
	Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
		.getContext().getAuthentication().getAuthorities();
	System.out.println(authorities);
	for (String role : roles)
	{
	    for (SimpleGrantedAuthority simpleGrantedAuthority : authorities)
	    {
		if (role.equals(simpleGrantedAuthority.getAuthority()))
		    return true;
	    }
	}
	return false;
    }
    
    private ModelAndView accessDenied()
    {
	return new ModelAndView(new RedirectView("denied"));
    }

}
