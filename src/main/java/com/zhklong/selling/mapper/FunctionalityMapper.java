package com.zhklong.selling.mapper;

import java.util.List;

import com.zhklong.selling.entity.Functionality;

public interface FunctionalityMapper {
	
	List<Functionality> getByEmployee(int id);

}
