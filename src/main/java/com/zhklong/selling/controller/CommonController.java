package com.zhklong.selling.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public void getProjectName(HttpServletRequest request,HttpServletResponse response){
		logger.info("get Project name , uuid : " + request.getAttribute("uuid"));
		Cookie cookie = new Cookie("projectName",request.getContextPath());
		response.addCookie(cookie);
	}
	
	@RequestMapping(path="/downloadExcel",method=RequestMethod.GET)
	public void downloadExcel(HttpServletResponse response,OutputStream output) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=test.xlsx");
		List<Employee> list = empMapper.getAll();
		excelUtil.getExcel(list, Employee.class, output);
	}
	
	@RequestMapping(path="/admin1",method=RequestMethod.GET)
	public void admin1(Writer writer) throws IOException{
		writer.write("admin1");
	}
	
	@RequestMapping(path="/admin2",method=RequestMethod.GET)
	public void admin2(Writer writer) throws IOException{
		writer.write("admin2");
	}
	
	@RequestMapping(path="/normal1",method=RequestMethod.GET)
	public void normal1(Writer writer) throws IOException{
		writer.write("normal1");
	}
	
	@RequestMapping(path="/normal2",method=RequestMethod.GET)
	public void normal2(Writer writer) throws IOException{
		writer.write("normal2");
	}
}
