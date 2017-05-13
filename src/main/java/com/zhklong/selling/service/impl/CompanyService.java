package com.zhklong.selling.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.entity.Company;
import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.mapper.CompanyMapper;
import com.zhklong.selling.mapper.CompanyTypeMapper;
import com.zhklong.selling.service.ICompanyService;

/**
 * @author paul
 * @since 2016-11-30
 * 公司服务的具体实现
 * */
@Service
public class CompanyService implements ICompanyService{
	
	private static final Logger logger = Logger.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyTypeMapper companyTypeMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	public Object getType() {
		return companyTypeMapper.getAll();
	}

	public Object save(Company company, HttpSession session) {
		Map<String , Object> json = new HashMap<String,Object>();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			json.put("result", 0);
			json.put("message", "未登录");
			return json;
		}
		company.setReviser(curEmp.getId());
		companyMapper.insert(company);
		json.put("result", 1);
		json.put("message", "添加成功");
		logger.info("add company , company name : " + company.getName() + " , emp name : " + curEmp.getName());
		return json;
	}

}
