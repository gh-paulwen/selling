package com.zhklong.selling.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.service.IEmployeeService;
import com.zhklong.selling.service.impl.EmployeeService;
import com.zhklong.selling.util.ApplicationUtil;
import com.zhklong.selling.util.ImageVerifyCodeUtil;

/**
 * @author paul
 * @since 2016-11-16 职工控制类
 * 
 * */
@Controller
@RequestMapping(path = "/employee")
public class EmployeeController {

	private IEmployeeService employeeService;
	
	private ImageVerifyCodeUtil imageVerifyCodeUtil;
	
	public void setImageVerifyCodeUtil(ImageVerifyCodeUtil imageVerifyCodeUtil) {
		this.imageVerifyCodeUtil = imageVerifyCodeUtil;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * 获取表单
	 * 
	 * */
	@RequestMapping(path="/{page}",method=RequestMethod.GET)
	public String getForm(@PathVariable("page")String page){
		return page;
	}

	/**
	 * 登录提交
	 * @param employee
	 * @param session 由springmvc 传
	 * */
	@ResponseBody
	@RequestMapping(path = "/submitLogin", method = RequestMethod.POST)
	public Object submitLogin(@ModelAttribute("employee") Employee employee,@RequestParam("verifyCode")String code,HttpServletRequest request)
			throws IOException {
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		Object obj = employeeService.login(employee,session,"",code);
		return obj;
	}

	/**
	 * 发送验证码
	 * */
	@ResponseBody
	@RequestMapping(path = "/sendCode", method = RequestMethod.GET)
	public Object sendCode(HttpServletRequest request) {
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		Object obj = employeeService.sendCode(session);
		return obj;
	}

	/**
	 * 提交验证码
	 * @param verifyCode 用户提交的验证码
	 * @param session 包含在发送验证码环节所生成的验证码和用户的手机号码
	 * */
	@ResponseBody
	@RequestMapping(path = "/submitSMSVerify", method = RequestMethod.POST)
	public Object submitSMSVerify(
			@RequestParam("verifyCode") String verifyCode, HttpServletRequest request) {
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		Object obj = employeeService.verifyCode(verifyCode, session);
		return obj;
	}

	/**
	 * 设置密码提交
	 * @param password
	 * @param repeatPassword
	 * @param session
	 * */
	@ResponseBody
	@RequestMapping(path = "/submitSetPassword", method = RequestMethod.POST)
	public Object submitSetPassword(@RequestParam("password") String password,
			@RequestParam("repeatPassword") String repeatPassword,
			HttpServletRequest request) {
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		return employeeService.setPassword(password, repeatPassword, session);
	}
	
	/**
	 * 请求重置密码 
	 * @param cellphone
	 * @param session
	 * */
	@ResponseBody
	@RequestMapping(path = "/resetPassword",method=RequestMethod.POST)
	public Object forgetPassword(@RequestParam("cellphone")String cellphone,HttpServletRequest request){
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		return employeeService.resetPassword(cellphone, session);
	}
	
	/**
	 * 获取图片验证码
	 * @throws IOException 
	 * */
	@RequestMapping(path="/imageVerifyCode",method=RequestMethod.GET)
	public void imageVerifyCode(HttpServletResponse response,HttpServletRequest request) 
			throws IOException{
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		imageVerifyCodeUtil.createCode();
		String code = imageVerifyCodeUtil.getCode();
		session.setAttribute("imageVerifyCode", code);
		imageVerifyCodeUtil.write(response.getOutputStream());
	}
	
	/**
	 * 获取职员类型
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getType",method=RequestMethod.GET)
	public Object getType(){
		return employeeService.getEmployeeType();
	}
	
	/**
	 * 得到当前在线的employee
	 * */
	@ResponseBody
	@RequestMapping(path="/getCurrentEmployee",method=RequestMethod.GET)
	public Object getCurrentEmployee(HttpServletRequest request){
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		return session.getAttribute("currentEmployee");
	}
	
	/**
	 * 添加职员
	 * @param employee
	 * */
	@ResponseBody
	@RequestMapping(path="/submitSave",method=RequestMethod.POST)
	public Object submitSave(@ModelAttribute("employee")Employee employee,HttpServletRequest request){
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		return employeeService.save(employee, session);
	}
	
	/**
	 * 得到职员对应的权限
	 * @param session
	 * */
	@ResponseBody
	@RequestMapping(path="/getFunctionality",method=RequestMethod.GET)
	public Object getFunctionality(HttpServletRequest request){
		HttpSession session = ApplicationUtil.getSessionByIp(request.getRemoteAddr(),request.getServletContext());
		return employeeService.getFunctionality(session);
	}
}
