package com.zhklong.selling.mapper;

import java.util.List;

import com.zhklong.selling.entity.City;

public interface CityMapper {
	
	List<City> getProvince();
	
	List<City> getByProvince(int id);

}
