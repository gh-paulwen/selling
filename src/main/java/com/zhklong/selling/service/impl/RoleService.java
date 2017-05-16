package com.zhklong.selling.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.dto.DomainTransfer;
import com.zhklong.selling.entity.Functionality;
import com.zhklong.selling.entity.Role;
import com.zhklong.selling.mapper.FunctionalityMapper;
import com.zhklong.selling.mapper.RoleMapper;
import com.zhklong.selling.service.IRoleService;

@Service
public class RoleService implements IRoleService{
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private FunctionalityMapper functionalityMapper;

	@Override
	public DomainTransfer addFunctionality(int roleid, int funcid) {
		DomainTransfer dt = new DomainTransfer();
		functionalityMapper.addFunctionality2Role(roleid, funcid);
		dt.save("message", "添加成功");
		return dt;
	}

	@Override
	public DomainTransfer removeFunctionality(int roleid, int funcid) {
		DomainTransfer dt = new DomainTransfer();
		functionalityMapper.removeFunctionalityFromRole(roleid, funcid);
		dt.save("message", "移除成功");
		return dt;
	}

	@Override
	public DomainTransfer getFunctionality(int roleid) {
		DomainTransfer dt = new DomainTransfer();
		List<Functionality> list = functionalityMapper.getByRole(roleid);
		dt.save("listFunctionality", list);
		return dt;
	}

	@Override
	public DomainTransfer getAll() {
		DomainTransfer dt = new DomainTransfer();
		List<Role> list = roleMapper.getAll();
		dt.save("listRole", list);
		return dt;
	}

	@Override
	public DomainTransfer getNotInRole(int roleid) {
		DomainTransfer dt= new DomainTransfer();
		List<Functionality> list = functionalityMapper.getNotInRole(roleid);
		dt.save("listFunctionality", list);
		return dt;
	}

	@Override
	public DomainTransfer getByEmp(int empid) {
		DomainTransfer dt= new DomainTransfer();
		List<Role> list = roleMapper.getByEmployee(empid);
		dt.save("listRole", list);
		return dt;
	}

	@Override
	public DomainTransfer getNotInEmp(int empid) {
		DomainTransfer dt= new DomainTransfer();
		List<Role> list = roleMapper.getNotInEmployee(empid);
		dt.save("listRole", list);
		return dt;
	}

	@Override
	public DomainTransfer addRole2Emp(int roleid, int empid) {
		DomainTransfer dt = new DomainTransfer();
		roleMapper.addRole2Employee(empid, roleid);
		dt.save("message", "添加成功");
		return dt;
	}

	@Override
	public DomainTransfer removeRoleFromEmp(int roleid, int empid) {
		DomainTransfer dt = new DomainTransfer();
		roleMapper.removeRoleFromEmployee(empid, roleid);
		dt.save("message", "移除成功");
		return dt;
	}
}
