package com.zhklong.selling.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.dto.DomainTransfer;
import com.zhklong.selling.entity.Store;
import com.zhklong.selling.mapper.StoreMapper;
import com.zhklong.selling.service.IStoreService;

@Service
public class StoreService implements IStoreService{
	
	@Autowired
	private StoreMapper storeMapper;
	
	public DomainTransfer getByCompany(int company) {
		DomainTransfer dt = new DomainTransfer();
		List<Store> list = storeMapper.getByCompany(company);
		dt.save("listStore", list);
		return dt;
	}

}
