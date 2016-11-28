package com.zhklong.selling.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author paul
 * @since 2016-11-24
 * 提供一些接口
 * 
 * */
@Controller
public class CommonController {
	private static final Logger logger = Logger.getLogger(CommonController.class.getName());
	
	@RequestMapping(path="/projectName",method=RequestMethod.GET)
	@ResponseBody
	public Object getProjectName(HttpServletRequest request){
		logger.info("get Project name , uuid : " + request.getAttribute("uuid"));
		return request.getContextPath();
	}
}
