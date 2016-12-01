package com.zhklong.selling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.service.ICompanyService;

/**
 * @author paul
 * @since 2016-11-30
 * 公司控制器类
 * 
 * */
@Controller
@RequestMapping("/company")
public class CompanyController {
	
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

}
