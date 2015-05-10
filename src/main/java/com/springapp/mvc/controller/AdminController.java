package com.springapp.mvc.controller;

import com.springapp.mvc.domain.UsersEntity;
import com.springapp.mvc.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by kapitoha on 06.05.15.
 */
@Controller
public class AdminController {
    private UserRepository userRepository;
    
    @Autowired
    public AdminController(UserRepository userRepository)
    {
	this.userRepository = userRepository;
    }
    
    @RequestMapping("/admin")
    public ModelAndView showAdmin()
    {
        ModelAndView view = new ModelAndView();
        return view;
    }
    @RequestMapping("/admin-users")
    public ModelAndView showUsers()
    {
        ModelAndView view = new ModelAndView();
        List<UsersEntity> list = userRepository.getAllUsers();
        view.setViewName("/admin");
        System.out.println(list);
        view.addObject("user_list", list);
        return view;
    }
    
    @RequestMapping("admin-user-edit")
    public ModelAndView editUser(@RequestParam int id)
    {
	 ModelAndView view = new ModelAndView();
	 view.setViewName("/admin");
	 UsersEntity user = userRepository.getUser(id);
	 view.addObject("user", user);
	 return view;
    }

}
