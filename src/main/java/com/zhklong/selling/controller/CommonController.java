package com.zhklong.selling.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {

	
	@RequestMapping(path="/projectName",method=RequestMethod.GET)
	@ResponseBody
	public Object getProjectName(HttpServletRequest req){
		return req.getContextPath();
	}
}
