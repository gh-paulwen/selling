package com.zhklong.selling.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.mapper.EmployeeMapper;
import com.zhklong.selling.util.ExcelUtil;


/**
 * @author paul
 * @since 2016-11-24
 * 提供一些接口
 * 
 * */
@Controller
public class CommonController {
	
	private static final Logger logger = Logger.getLogger(CommonController.class.getName());
	
	@Autowired
	private ExcelUtil excelUtil ;
	
	@Autowired
	private EmployeeMapper empMapper;
	
	@RequestMapping(path="/projectName",method=RequestMethod.GET)
	@ResponseBody
	public Object getProjectName(HttpServletRequest request){
		logger.info("get Project name , uuid : " + request.getAttribute("uuid"));
		return request.getContextPath();
	}
	
	@RequestMapping(path="/downloadExcel",method=RequestMethod.GET)
	public void downloadExcel(HttpServletResponse response,OutputStream output) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=test.xlsx");
		List<Employee> list = empMapper.getAll();
		excelUtil.getExcel(list, Employee.class, output);
	}		
}
