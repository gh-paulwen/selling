package com.zhklong.selling.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhklong.selling.BaseTest;
import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.mapper.EmployeeMapper;

public class ExcelUtilTest extends BaseTest{
	
	@Autowired
	private ExcelUtil excelUtil;
	
	@Autowired
	private EmployeeMapper empMapper;
	
	@Test
	public void testExcel() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		OutputStream output = new FileOutputStream("/home/paul/test.xlsx");
		List<Employee> list = empMapper.getAll();
		excelUtil.getExcel(list,Employee.class,output);
	}

}
