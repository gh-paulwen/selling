package com.zhklong.selling.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.mapper.StoreMapper;
import com.zhklong.selling.service.IStoreService;

@Service
public class StoreService implements IStoreService{
	
	@Autowired
	private StoreMapper storeMapper;
	
	public Object getByCompany(int company) {
		return storeMapper.getByCompany(company);
	}

}
