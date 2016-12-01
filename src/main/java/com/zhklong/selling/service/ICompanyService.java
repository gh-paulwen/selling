package com.zhklong.selling.service;

import com.zhklong.selling.entity.Company;

/**
 * @author paul
 * @since 2016-11-30
 * 公司类型服务接口
 * 
 * */
public interface ICompanyService {
	
	/**
	 * 得到所有公司类型
	 * 
	 * */
	Object getType();
	
	/**
	 * 添加公司
	 * 
	 * */
	Object save(Company company);
}
