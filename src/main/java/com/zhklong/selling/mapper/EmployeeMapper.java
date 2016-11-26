package com.zhklong.selling.mapper;

import com.zhklong.selling.entity.Employee;

public interface EmployeeMapper {
	
	void insert(Employee employee);
	
	Employee getByCell(String cellphone);
	
	void update(Employee employee);
	
	Employee getByCodeCompany(String code,int company);

}
