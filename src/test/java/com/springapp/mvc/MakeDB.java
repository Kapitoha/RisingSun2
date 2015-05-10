package com.springapp.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.springapp.mvc.dao.DAOManager;
import com.springapp.mvc.dao.DAOmngr;
import com.springapp.mvc.domain.UsersEntity;

/**
 * @author kapitoha
 *
 */
public class MakeDB {
    @Transactional
    public static class Runner {
	@Autowired
	DAOmngr mngr;
	public DAOmngr getD()
	{
	    return mngr;
	}
    }
    
    public static void main(String[] args)
    {
//	ApplicationContext context = new AnnotationConfigApplicationContext(DAOmngr.class);
	DAOmngr mngr = new DAOmngr();
	UsersEntity user = new UsersEntity();
	user.setLogin("login");
	user.setName("name");
	mngr.saveInstance(user);
    }
}
