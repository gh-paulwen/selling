package com.zhklong.selling.service.impl;

import com.zhklong.selling.entity.Company;
import com.zhklong.selling.mapper.CompanyMapper;
import com.zhklong.selling.mapper.CompanyTypeMapper;
import com.zhklong.selling.service.ICompanyService;

/**
 * @author paul
 * @since 2016-11-30
 * 公司服务的具体实现
 * */
public class CompanyService implements ICompanyService{
	
	private CompanyTypeMapper companyTypeMapper;
	
	private CompanyMapper companyMapper;
	
	public void setCompanyMapper(CompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}
	
	public void setCompanyTypeMapper(CompanyTypeMapper companyTypeMapper) {
		this.companyTypeMapper = companyTypeMapper;
	}

	public Object getType() {
		return companyTypeMapper.getAll();
	}

	public Object save(Company company) {
		return null;
	}

}
