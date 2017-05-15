package com.zhklong.selling.service;

import javax.servlet.http.HttpSession;

import com.zhklong.selling.dto.DomainTransfer;
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
	DomainTransfer getType();
	
	/**
	 * 添加公司
	 * @param company 
	 * @param session 得到当前employee
	 * */
	DomainTransfer save(Company company,HttpSession session);
}
