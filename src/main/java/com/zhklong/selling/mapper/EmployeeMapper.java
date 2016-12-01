package com.zhklong.selling.mapper;

import java.util.List;

import com.zhklong.selling.entity.Employee;

public interface EmployeeMapper {

	void insert(Employee employee);

	void update(Employee employee);
	
	Employee getById(int id);

	Employee getByCell(String cellphone);

	Employee getByCodeCompany(String code, int company);
	
	List<Employee> getAll();
	
	List<Employee> getByCompany(int company);

}
