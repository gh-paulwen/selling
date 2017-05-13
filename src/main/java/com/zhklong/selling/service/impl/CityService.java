package com.zhklong.selling.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.mapper.CityMapper;
import com.zhklong.selling.service.ICityService;

/**
 * ICityService 实现类
 * @author paul
 * @since 2016-12-04
 * 
 * */
@Service
public class CityService implements ICityService{
	
	@Autowired
	private CityMapper cityMapper;
	
	public Object getProvince() {
		return cityMapper.getProvince();
	}

	public Object getByProvince(int province) {
		return cityMapper.getByProvince(province);
	}

}
