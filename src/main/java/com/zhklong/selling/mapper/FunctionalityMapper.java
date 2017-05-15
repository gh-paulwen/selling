package com.zhklong.selling.mapper;

import java.util.List;

import com.zhklong.selling.entity.Functionality;

public interface FunctionalityMapper {
	
	List<Functionality> getByEmployee(int id);
	
	List<Functionality> getByRole(int id);
	
	List<Functionality> getNotInRole(int id);
	
	List<Functionality> getByUrl(String url);
	
	void addFunctionality2Role(int roleid,int funcid);
	
	void removeFunctionalityFromRole(int roleid,int funcid);
}
