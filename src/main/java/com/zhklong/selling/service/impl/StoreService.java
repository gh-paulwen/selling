package com.zhklong.selling.service.impl;

import com.zhklong.selling.mapper.StoreMapper;
import com.zhklong.selling.service.IStoreService;

public class StoreService implements IStoreService{
	
	private StoreMapper storeMapper;
	
	public void setStoreMapper(StoreMapper storeMapper) {
		this.storeMapper = storeMapper;
	}

	public Object getByCompany(int company) {
		return storeMapper.getByCompany(company);
	}

}
