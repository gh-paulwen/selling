package com.zhklong.selling.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.dto.DomainTransfer;
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
	
	public DomainTransfer getProvince() {
		DomainTransfer dt = new DomainTransfer();
		dt.save("listProvince", cityMapper.getProvince());
		return dt;
	}

	public DomainTransfer getByProvince(int province) {
		DomainTransfer dt = new DomainTransfer();
		dt.save("listProvince", cityMapper.getProvince());
		return dt;
	}

}
