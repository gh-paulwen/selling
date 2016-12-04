package com.zhklong.selling.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.entity.Company;
import com.zhklong.selling.service.ICompanyService;
import com.zhklong.selling.util.Session;

/**
 * @author paul
 * @since 2016-11-30
 * 公司控制器类
 * 
 * */
@Controller
@RequestMapping("/company")
public class CompanyController {
	
	private static final Logger logger = Logger.getLogger(CompanyController.class);
	
	private ICompanyService companyService ;
	
	public void setCompanyService(ICompanyService companyService) {
		this.companyService = companyService;
	}
	
	/**
	 * 得到公司类型
	 * */
	@ResponseBody
	@RequestMapping(path="/getType",method=RequestMethod.GET)
	public Object getType(){
		return companyService.getType();
	}
	
	/**
	 * 添加公司
	 * @param company , 需要添加的company
	 * @param request , 获取session中的current Emp
	 * */
	@ResponseBody
	@RequestMapping(path="/submitSave",method=RequestMethod.POST)
	public Object submitSave(@ModelAttribute("company")Company company,HttpServletRequest request){
		Session session = (Session) request.getAttribute(Session._ATTRIBUTE);
		logger.info("try to add company , uuid : " + session.getKey());
		return companyService.save(company, session);
	}

}
