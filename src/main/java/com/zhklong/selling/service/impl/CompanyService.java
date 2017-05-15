package com.zhklong.selling.service.impl;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.dto.DomainTransfer;
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
	
	public DomainTransfer getType() {
		DomainTransfer dt = new DomainTransfer();
		dt.save("listCompany", companyTypeMapper.getAll());
		return dt;
	}

	public DomainTransfer save(Company company, HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			dt.save("result", 0);
			dt.save("message", "未登录");
			return dt;
		}
		company.setReviser(curEmp.getId());
		companyMapper.insert(company);
		dt.save("result", 1);
		dt.save("message", "添加成功");
		logger.info("add company , company name : " + company.getName() + " , emp name : " + curEmp.getName());
		return dt;
	}

}
