package com.zhklong.selling.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.service.IEmployeeService;
import com.zhklong.selling.service.impl.EmployeeService;
import com.zhklong.selling.util.ImageVerifyCodeUtil;
import com.zhklong.selling.util.Session;


/**
 * @author paul
 * @since 2016-11-16 职工控制类
 * 
 * */
@Controller
@RequestMapping(path = "/employee")
public class EmployeeController {
	
	private static final String SESSION = "SESSION";
	
	private static final Logger logger = Logger.getLogger(EmployeeController.class.getName());

	private IEmployeeService employeeService;
	
	private ImageVerifyCodeUtil imageVerifyCodeUtil;
	
	public void setImageVerifyCodeUtil(ImageVerifyCodeUtil imageVerifyCodeUtil) {
		this.imageVerifyCodeUtil = imageVerifyCodeUtil;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * 登录提交
	 * @param employee
	 * @param code 图片验证码 
	 * */
	@ResponseBody
	@RequestMapping(path = "/submitLogin", method = RequestMethod.POST)
	public Object submitLogin(@ModelAttribute("employee") Employee employee,@RequestParam("verifyCode")String code,HttpServletRequest request)
			throws IOException {
		logger.info("submitLogin , uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		Object obj = employeeService.login(employee,session,code);
		return obj;
	}

	/**
	 * 发送验证码
	 * */
	@ResponseBody
	@RequestMapping(path = "/sendCode", method = RequestMethod.GET)
	public Object sendCode(HttpServletRequest request) {
		logger.info("request sendCode , uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		Object obj = employeeService.sendCode(session);
		return obj;
	}

	/**
	 * 提交验证码
	 * @param verifyCode 用户提交的验证码
	 * @param request 包含在发送验证码环节所生成的验证码和用户的手机号码
	 * */
	@ResponseBody
	@RequestMapping(path = "/submitSMSVerify", method = RequestMethod.POST)
	public Object submitSMSVerify(@RequestParam("verifyCode") String verifyCode,HttpServletRequest request) {
		logger.info("submitSMSVerify , uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		Object obj = employeeService.verifyCode(verifyCode, session);
		return obj;
	}

	/**
	 * 设置密码提交
	 * @param password
	 * @param repeatPassword
	 * 
	 * */
	@RequestMapping(path = "/submitSetPassword", method = RequestMethod.POST)
	public @ResponseBody Object submitSetPassword(@RequestParam("password") String password,
			@RequestParam("repeatPassword") String repeatPassword,HttpServletRequest request) {
		logger.info("submitSetPassword , uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		return employeeService.setPassword(password, repeatPassword, session);
	}
	
	/**
	 * 请求重置密码 
	 * @param cellphone
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path = "/resetPassword",method=RequestMethod.POST)
	public Object forgetPassword(@RequestParam("cellphone")String cellphone,HttpServletRequest request){
		logger.info("request resetPassword , uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		return employeeService.resetPassword(cellphone, session);
	}
	
	/**
	 * 获取图片验证码
	 * @throws IOException 
	 * */
	@RequestMapping(path="/imageVerifyCode",method=RequestMethod.GET)
	public void imageVerifyCode(HttpServletResponse response,HttpServletRequest request) 
			throws IOException{
		Session session =  (Session) request.getAttribute(SESSION);
		imageVerifyCodeUtil.createCode();
		String code = imageVerifyCodeUtil.getCode();
		session.setAttribute("imageVerifyCode", code);
		logger.info("try to get image verify code , uuid : " + request.getAttribute("uuid") + ", code : " + code);
		imageVerifyCodeUtil.write(response.getOutputStream());
	}
	
	/**
	 * 获取职员类型
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getType",method=RequestMethod.GET)
	public Object getType(HttpServletRequest request){
		logger.info("get Employee Type , uuid : " + request.getAttribute("uuid"));
		return employeeService.getEmployeeType();
	}
	
	/**
	 * 得到当前在线的employee
	 * */
	@ResponseBody
	@RequestMapping(path="/getCurrentEmployee",method=RequestMethod.GET)
	public Object getCurrentEmployee(HttpServletRequest request){
		logger.info("get Current Employee, uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		return session.getAttribute("currentEmployee");
	}
	
	/**
	 * 添加职员
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/submitSave",method=RequestMethod.POST)
	public Object submitSave(@ModelAttribute("employee")Employee employee,HttpServletRequest request){
		logger.info("add Employee , uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		return employeeService.save(employee, session);
	}
	
	/**
	 * 得到职员对应的权限
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getFunctionality",method=RequestMethod.GET)
	public Object getFunctionality(HttpServletRequest request){
		logger.info("get what employee can do, uuid : " + request.getAttribute("uuid"));
		Session session =  (Session) request.getAttribute(SESSION);
		return employeeService.getFunctionality(session);
	}
	
	/**
	 * 查询手机号码是否已经存在
	 * @param cellphone
	 * */
	@ResponseBody
	@RequestMapping(path="/checkCell",method=RequestMethod.GET)
	public Object checkCell(@RequestParam("cellphone") String cellphone){
		logger.info("cellphone : " + cellphone);
		return employeeService.checkCell(cellphone);
	}
	
	/**
	 * 查询工号在一个公司是否已存在
	 * @param code
	 * @param company
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/checkCode",method=RequestMethod.GET)
	public Object checkCode(@RequestParam("company")int company,@RequestParam("code") String code){
		logger.info("code : " + code + ", company : " + company);
		return employeeService.checkCode(company, code);
	}
	
	/**
	 * 得到所有职员
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getAll",method=RequestMethod.GET)
	public Object getAll(){
		logger.info("");
		return employeeService.getAll();
	}
	
	/**
	 * 得到当前员工所在公司的所有职员
	 * @param request 用来得到session 
	 * */
	@ResponseBody
	@RequestMapping(path="/getByCompany",method=RequestMethod.GET)
	public Object getByCompany(HttpServletRequest request){
		Session session = (Session) request.getAttribute(SESSION);
		logger.info("uuid : " + request.getAttribute("uuid"));
		return employeeService.getByCompany(session);
	}
}
