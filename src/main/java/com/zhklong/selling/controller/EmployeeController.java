package com.zhklong.selling.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.service.IEmployeeService;
import com.zhklong.selling.util.ImageVerifyCodeUtil;


/**
 * @author paul
 * @since 2016-11-16 职工控制类
 * 
 * */
@Controller
@RequestMapping(path = "/employee")
public class EmployeeController {
	
	private static final Logger logger = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private ImageVerifyCodeUtil imageVerifyCodeUtil;
	
	/**
	 * 登录提交
	 * @param employee
	 * @param code 图片验证码 
	 * */
	@ResponseBody
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public Object submitLogin(@ModelAttribute("employee") Employee employee,@RequestParam("verifyCode")String code,HttpSession session)
			throws IOException {
		Object obj = employeeService.login(employee,session,code);
		return obj;
	}

	/**
	 * 发送验证码
	 * */
	@ResponseBody
	@RequestMapping(path = "/sendCode", method = RequestMethod.GET)
	public Object sendCode(HttpServletRequest request,HttpSession session) {
		Object obj = employeeService.sendCode(session);
		return obj;
	}

	/**
	 * 提交验证码
	 * @param verifyCode 用户提交的验证码
	 * @param request 包含在发送验证码环节所生成的验证码和用户的手机号码
	 * */
	@ResponseBody
	@RequestMapping(path = "/smsVerify", method = RequestMethod.POST)
	public Object submitSMSVerify(@RequestParam("verifyCode") String verifyCode,HttpSession session) {
		Object obj = employeeService.verifyCode(verifyCode, session);
		return obj;
	}

	/**
	 * 设置密码提交
	 * @param password
	 * @param repeatPassword
	 * 
	 * */
	@RequestMapping(path = "/setPassword", method = RequestMethod.POST)
	public @ResponseBody Object submitSetPassword(@RequestParam("password") String password,
			@RequestParam("repeatPassword") String repeatPassword,HttpSession session) {
		return employeeService.setPassword(password, repeatPassword, session);
	}
	
	/**
	 * 请求重置密码 
	 * @param cellphone
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path = "/resetPassword",method=RequestMethod.POST)
	public Object forgetPassword(@RequestParam("cellphone")String cellphone,HttpSession session){
		return employeeService.resetPassword(cellphone, session);
	}
	
	/**
	 * 获取图片验证码
	 * @throws IOException 
	 * */
	@RequestMapping(path="/imageVerifyCode",method=RequestMethod.GET)
	public void imageVerifyCode(HttpServletResponse response,HttpSession session) 
			throws IOException{
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
	public Object getType(HttpServletRequest request){
		return employeeService.getEmployeeType();
	}
	
	/**
	 * 得到当前在线的employee
	 * */
	@ResponseBody
	@RequestMapping(path="/getCurrentEmployee",method=RequestMethod.GET)
	public Object getCurrentEmployee(HttpSession session){
		return session.getAttribute("currentEmployee");
	}
	
	/**
	 * 添加职员
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/save",method=RequestMethod.POST)
	public Object submitSave(@ModelAttribute("employee")Employee employee,HttpSession session){
		return employeeService.save(employee, session);
	}
	
	/**
	 * 得到职员对应的权限
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getFunctionality",method=RequestMethod.GET)
	public Object getFunctionality(HttpSession session){
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
	public Object getByCompany(HttpSession session){
		return employeeService.getByCompany(session);
	}
	
	/**
	 * 删除职员
	 * @param id
	 * */
	@ResponseBody
	@RequestMapping(path="/delete",method=RequestMethod.GET)
	public Object delete(@RequestParam("id") int id){
		return employeeService.delete(id);
	}
	
	/**
	 * 得到职员详细信息
	 * @param id
	 * */
	@ResponseBody
	@RequestMapping(path="/detail",method=RequestMethod.GET)
	public Object detail(@RequestParam("id") int id){
		return employeeService.detail(id);
	}
	
	/**
	 * 修改职员资料
	 * @param employee
	 * */
	@ResponseBody
	@RequestMapping(path="/update",method=RequestMethod.POST)
	public Object update(@ModelAttribute("employee") Employee employee){
		return employeeService.update(employee);
	}
}
