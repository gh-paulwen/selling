package com.zhklong.selling.mapper;

import java.util.List;

import com.zhklong.selling.entity.Store;

public interface StoreMapper {
	
	void insert(Store store);
	
	List<Store> getByCompany(int companyid);

}
