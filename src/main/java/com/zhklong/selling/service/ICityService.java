package com.zhklong.selling.service;

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
	Object getProvince();
	
	/**
	 * 用省id获取市
	 * */
	Object getByProvince(int province);
}
