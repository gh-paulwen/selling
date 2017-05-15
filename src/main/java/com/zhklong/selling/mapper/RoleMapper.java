package com.zhklong.selling.mapper;

import java.util.List;

import com.zhklong.selling.entity.Role;

public interface RoleMapper {
	
	List<Role> getAll();
	
	List<Role> getNotInEmployee(int empid);
	
	List<Role> getByEmployee(int empid);
	
	void addRole2Employee(int empid,int roleid);
	
	void removeRoleFromEmployee(int empid,int roleid);
	
}
