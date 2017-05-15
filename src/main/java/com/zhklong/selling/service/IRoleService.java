package com.zhklong.selling.service;

import com.zhklong.selling.dto.DomainTransfer;

public interface IRoleService {
	
	DomainTransfer getAll();
	
	DomainTransfer addFunctionality(int roleid,int funcid);
	
	DomainTransfer removeFunctionality(int roleid,int funcid);
	
	DomainTransfer getFunctionality(int roleid);
	
	DomainTransfer getNotInRole(int roleid);
}
