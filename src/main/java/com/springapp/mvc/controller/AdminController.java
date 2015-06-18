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

    @PreAuthorize("hasAnyAuthority('EDIT_USER')")
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

    @PreAuthorize("hasAnyAuthority('EDIT_USER')")
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

    @PreAuthorize("hasAnyAuthority('EDIT_USER')")
    @RequestMapping(value = "/admin-user-create")
    public ModelAndView createUser(HttpServletRequest request)
    {
	return editUser(new UsersEntity(), request);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_USER')")
    @RequestMapping(value = "/admin-user-save", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute("user") UsersEntity user,
	    HttpServletRequest request, RedirectAttributes attr)
    {
	if (!isMatchesAccessRights(Right.EDIT_USER.toString()))
	{
	    return accessDenied();
	}
	int action = 0;
	UsersEntity userInstance = (null == user)? new UsersEntity() : user;
	Status status = Status.valueOf(request.getParameter("status"));

	/*
	 * Check fields
	 */
	{
	    String userFieldCHeckMsg = checkUsersFields(userInstance);
	    if (userFieldCHeckMsg != null)
	    {
		request.setAttribute("error_msg", userFieldCHeckMsg);
		return editUser(userInstance, request);
	    }
	}
	// read and manage user's access rights
	manageUsersAccessRights(userInstance, request);

	/*
	 * Save / update actions
	 */
	String result_msg = null;
	Object userDuplicateCheckResult = checkIfUsersFieldsDuplicated(userInstance);
	if (userDuplicateCheckResult instanceof Integer)
	    action = (Integer) userDuplicateCheckResult;
	else
	{
	    request.setAttribute("error_msg", userDuplicateCheckResult);
	    return editUser(userInstance, request);
	}

	userInstance.setStatus(status);
	switch (action)
	{
	    case SAVE: // save action
		if (userRepository.saveUser(userInstance))
		    result_msg = "Saving was successful";
		else
		{
		    request.setAttribute("error_msg", "Saving was failed.");
		    return editUser(userInstance, request);
		}
		break;
	    case UPDATE: // update action
		if (!userRepository.updateUser(userInstance))
		{
		    request.setAttribute("error_msg", "Update was failed.");
		    return editUser(userInstance, request);
		}
		else
		    result_msg = "Update was successful";
		break;

	    default:
		request.setAttribute("error_msg", "Unknown User save error!!!");
		return editUser(userInstance, request);
	}
	ModelAndView finalView = new ModelAndView("redirect:/admin");
	attr.addFlashAttribute("info_msg", result_msg);
	return finalView;
    }

    private String checkUsersFields(UsersEntity user)
    {
	String msg = String.format("Allowed only <strong><em>%s</em></strong> characters in '%s' field",
			StringUtils.FIELD_NAME_PATTERN);
	if (!StringUtils.checkIfAllowed(user.getName()))
	{
	    return String.format(msg, "Name");
	}
	if (!StringUtils.checkIfAllowed(user.getLogin()))
	{
	    return String.format(msg, "Login");
	}
	return null;
    }
    private void manageUsersAccessRights(UsersEntity user, HttpServletRequest request)
    {
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
	user.getAccessList().clear();
	user.setAccessList(newAccessRightsList);
    }
    public Object checkIfUsersFieldsDuplicated(UsersEntity user)
    {
	Object action = null;
	boolean hasErrors = false;
	List<UsersEntity> existedUsersList = userRepository
			.getUsersByFields(user);
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
		if (existedUser.getName().equals(user.getName())
				&& existedUser.getId() != user.getId())
		{
		    // same name
		    action = "This Name is already presented. Choose other please.";
		    hasErrors = true;
		}
		if (existedUser.getLogin().equals(user.getLogin())
				&& existedUser.getId() != user.getId())
		{
		    // same login
		    action = "This Login is already presented. Choose other please.";
		    hasErrors = true;
		}
	    }
	    if (!hasErrors)
	    {
		if (user.getId() == 0)
		{
		    action = SAVE;
		}
		else
		{
		    // update
		    action = UPDATE;
		}
	    }
	}
	return action;
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

    @PreAuthorize("hasAnyAuthority('EDIT_ARTICLE_MASTER', 'EDIT_ARTICLE_AUTHOR')")
    @RequestMapping("/admin-article-edit")
    public ModelAndView editArticle(@ModelAttribute Article article,
	    HttpServletRequest request) throws UnsupportedEncodingException
    {
	if (article.getId() > 0) article = articleManager.getArticle(article.getId());
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

    @PreAuthorize("hasAnyAuthority('CREATE_ARTICLE')")
    @RequestMapping("/admin-article-create")
    public ModelAndView createArticle(HttpServletRequest request) throws UnsupportedEncodingException
    {
	if (!isMatchesAccessRights(Right.CREATE_ARTICLE.toString()))
	    return accessDenied();
	return manageArticle(new Article(), request);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ARTICLE_MASTER', 'DELETE_ARTICLE_AUTHOR')")
    @RequestMapping("/admin-article-delete")
    public ModelAndView deleteArticle(@ModelAttribute Article article,
	    HttpServletRequest request, RedirectAttributes attributes)
    {
	article = articleManager.getArticle(article.getId());
	if (!isMatchesAccessRights(Right.DELETE_ARTICLE_MASTER.toString()))
	{
	    Principal userPrincipal = request.getUserPrincipal();
	    if (isMatchesAccessRights(Right.DELETE_ARTICLE_AUTHOR.toString())
		    && !userPrincipal.getName().equals(article.getAuthor().getLogin()))
	    {
		return accessDenied(); 
	    }

	}
	if (article.getTagList() != null || !article.getTagList().isEmpty())
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
	String titleColor = request.getParameter("title_color");
	int fontSize = Integer.valueOf(request.getParameter("title_font_size"));
	int position = Integer.valueOf(request.getParameter("position"));
	String tags = request.getParameter("tags");
	Article article = articleInstance.getId() > 0 ? articleManager
		.getArticle(articleInstance.getId()) : articleInstance;
	String content = request.getParameter("editor");
	article.setTitle(request.getParameter("title"));
	article.setContent(content);
	article.setAuthor(userRepository.getUser(request.getUserPrincipal().getName()));
	article.setImageUrl(request.getParameter("image"));
	article.parseAndSetTags(tags, articleManager);
	if (StringUtils.checkIsEmpty(article.getTitle()))
	{
	    request.setAttribute("error_msg", "Title cannot be empty");
	    return editArticle(article, request);
	}
	// First page handler
	List<FirstPage> firstPageList = Collections.emptyList();
	if (isMatchesAccessRights(Right.MANAGE_FIRST_PAGE.toString()))
	{
	    if (isOnFirstPage)
	    {
		if (article.getFirstPage() == null)
		{
		    article.setFirstPage(new FirstPage());
		}
		
		firstPageList = new LinkedList<>(articleManager.getFirstPages());
		if (firstPageList.contains(article.getFirstPage()))
		{
		    firstPageList.remove(article.getFirstPage());
		    //		Collections.swap(firstPageList, firstPageList.indexOf(article.getFirstPage()), position-1);
		}
		firstPageList.add(position - 1, article.getFirstPage());
		article.getFirstPage().setFeatured(isFeatured);
		
		article.setTitleColor(titleColor == null || titleColor.isEmpty()? "#000000" : titleColor);
		article.setTitleFontSize(StringUtils.parseInt(String.valueOf(fontSize), 40));
		
	    }
	    else
	    {
		if (article.getFirstPage() != null)
		{
		    articleManager.getFirstPageManager().removeArticleFromFirstPageDirectly(article);
		    article.setFirstPage(null);
		}
	    }
	}
	//archive handler
	if (isMatchesAccessRights(Right.MANAGE_ARCHIVE.toString()))
	{
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
	}

	if (article.getId() > 0)
	{
	    action = UPDATE;
	}
	else
	{
	    action = SAVE;
	}
	switch (action)
	{
	    case SAVE:
		if (articleManager.saveArticle(article))
		{
		    msg = "Saving was successful";
		}
		else
		{
		    return editArticle(article, request);
		}
		break;
	    case UPDATE:
		if (articleManager.updateArticle(article))
		{
		    msg = "Article updated successfuly";
		}
		else
		{
		    return editArticle(article, request);
		}
		break;

	    default:
		request.setAttribute("error_msg", "Unknown error was occurred!");
		return editArticle(article, request);
	}
	if (isOnFirstPage)
	{
	    for (int i = 0; i < firstPageList.size(); i++)
	    {
		firstPageList.get(i).setShow_order(i + 1);
		articleManager.getFirstPageManager().updateFirstPage(firstPageList.get(i));
	    }
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

    public boolean isMatchesAccessRights(String... rights)
    {
	@SuppressWarnings("unchecked")
	Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
		.getContext().getAuthentication().getAuthorities();
	for (String right : rights)
	{
	    for (SimpleGrantedAuthority simpleGrantedAuthority : authorities)
	    {
		if (right.equals(simpleGrantedAuthority.getAuthority()))
		    return true;
	    }
	}
	return false;
    }

    @RequestMapping("accessdenied")
    private ModelAndView accessDenied()
    {
//	ModelAndView view = new ModelAndView();
//	view.addObject("page_tag", "denied");
//	view.addObject("error_msg", "Access Denied!!!");
	return new ModelAndView("/accessdenied");
    }
    
    @RequestMapping(value = "404")
    public ModelAndView notfound()
    {
	ModelAndView view = new ModelAndView("/404");
	
	return view;
    }

}
