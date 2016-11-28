package com.zhklong.selling.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.zhklong.selling.util.SessionContainer;

/**
 * @author paul
 * @since 2016-11-24
 * tomcat 启动时存放一个SessionContainer到application域
 * 
 * */
public class ApplicationLifeListener implements ServletContextListener{

	private final static Logger logger = Logger.getLogger(ApplicationLifeListener.class.getName());
	public void contextInitialized(ServletContextEvent sce) {
		/**
		 * sessionContainer
		 * */
		sce.getServletContext().setAttribute("uuidContainer", new SessionContainer());
		
		logger.info("****************************server start****************************");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("uuidContainer");
		logger.info("****************************server stop*****************************");
	}

}
