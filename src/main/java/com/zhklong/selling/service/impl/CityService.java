package com.zhklong.selling.service.impl;

import com.zhklong.selling.mapper.CityMapper;
import com.zhklong.selling.service.ICityService;

/**
 * ICityService 实现类
 * @author paul
 * @since 2016-12-04
 * 
 * */
public class CityService implements ICityService{
	
	private CityMapper cityMapper;
	
	public void setCityMapper(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	public Object getProvince() {
		return cityMapper.getProvince();
	}

	public Object getByProvince(int province) {
		return cityMapper.getByProvince(province);
	}

}
