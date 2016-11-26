package com.zhklong.selling.listener;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

public class ApplicationLifeListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
		/**
		 * 存放sessionid及session
		 * */
		sce.getServletContext().setAttribute("sessionMap", new HashMap<String,HttpSession>());
		
		/**
		 * 存放ip地址及sessionid
		 * */
		sce.getServletContext().setAttribute("ipSessionMap", new HashMap<String,String>());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("sessionMap");
		sce.getServletContext().removeAttribute("ipSessionMap");
	}

}
