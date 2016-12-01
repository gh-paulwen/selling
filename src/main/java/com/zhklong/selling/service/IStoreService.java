package com.zhklong.selling.service;

/**
 * @author paul
 * @since 2016-11-30
 * 门店服务类
 * */
public interface IStoreService {
	
	/**
	 * 根据公司号得到所有门店
	 * */
	Object getByCompany(int company);

}
