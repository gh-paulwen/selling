package com.zhklong.selling.service;

import com.zhklong.selling.dto.DomainTransfer;

/**
 * 省市服务类
 * @author paul
 * @since 2016-12-04
 * 
 * */
public interface ICityService {
	/**
	 * 获取省
	 * */
	DomainTransfer getProvince();
	
	/**
	 * 用省id获取市
	 * */
	DomainTransfer getByProvince(int province);
}
