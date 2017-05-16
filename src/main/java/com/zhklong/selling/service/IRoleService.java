package com.zhklong.selling.service;

import com.zhklong.selling.dto.DomainTransfer;

/**
 * @author paul
 * 
 * */
public interface IRoleService {
	
	/**
	 * 得到所有的角色
	 * 
	 * */
	DomainTransfer getAll();
	
	/**
	 * 往角色加functionality
	 * @param roleid
	 * @param funcid
	 * */
	DomainTransfer addFunctionality(int roleid,int funcid);
	
	/**
	 * 从角色移除functionality
	 * @param roleid
	 * @param funcid
	 * */
	DomainTransfer removeFunctionality(int roleid,int funcid);
	
	/**
	 * 获得某角色的所有func
	 * @param roleid
	 * 
	 * */
	DomainTransfer getFunctionality(int roleid);
	
	/**
	 * 得到某角色没有的func
	 * @param roleid
	 * */
	DomainTransfer getNotInRole(int roleid);
	
	/**
	 * 得到emp的所有角色
	 * @param empid
	 * */
	DomainTransfer getByEmp(int empid);
	
	/**
	 * 得到emp没有的角色
	 * @param empid
	 * */
	DomainTransfer getNotInEmp(int empid);
	
	/**
	 * 把角色和emp关联起来
	 * @param roleid
	 * @param empid
	 * 
	 * */
	DomainTransfer addRole2Emp(int roleid,int empid);
	
	/**
	 * 解除角色和emp的关联
	 * @param roleid
	 * @param empid
	 * */
	DomainTransfer removeRoleFromEmp(int roleid,int empid);
}
