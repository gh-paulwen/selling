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

import com.zhklong.selling.dto.DomainTransfer;
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
		DomainTransfer dt = employeeService.login(employee,session,code);
		return dt.get();
	}

	/**
	 * 发送验证码
	 * */
	@ResponseBody
	@RequestMapping(path = "/sendCode", method = RequestMethod.GET)
	public Object sendCode(HttpServletRequest request,HttpSession session) {
		DomainTransfer dt = employeeService.sendCode(session);
		return dt.get();
	}

	/**
	 * 提交验证码
	 * @param verifyCode 用户提交的验证码
	 * @param request 包含在发送验证码环节所生成的验证码和用户的手机号码
	 * */
	@ResponseBody
	@RequestMapping(path = "/smsVerify", method = RequestMethod.POST)
	public Object submitSMSVerify(@RequestParam("verifyCode") String verifyCode,HttpSession session) {
		DomainTransfer dt = employeeService.verifyCode(verifyCode, session);
		return dt.get();
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
		DomainTransfer dt = employeeService.setPassword(password, repeatPassword, session);
		return dt.get();
	}
	
	/**
	 * 请求重置密码 
	 * @param cellphone
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path = "/resetPassword",method=RequestMethod.POST)
	public Object forgetPassword(@RequestParam("cellphone")String cellphone,HttpSession session){
		DomainTransfer dt = employeeService.resetPassword(cellphone, session);
		return dt.get();
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
		DomainTransfer dt = employeeService.getEmployeeType();
		return dt.get();
	}
	
	/**
	 * 得到当前在线的employee
	 * */
	@ResponseBody
	@RequestMapping(path="/getCurrentEmployee",method=RequestMethod.GET)
	public Object getCurrentEmployee(HttpSession session){
		DomainTransfer dt = new DomainTransfer();
		Employee emp = (Employee) session.getAttribute("currentEmployee");
		dt.save("currentEmployee",emp);
		return dt.get();
	}
	
	/**
	 * 添加职员
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/save",method=RequestMethod.POST)
	public Object submitSave(@ModelAttribute("employee")Employee employee,HttpSession session){
		DomainTransfer dt = employeeService.save(employee, session);
		return dt.get();
	}
	
	/**
	 * 得到职员对应的权限
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getFunctionality",method=RequestMethod.GET)
	public Object getFunctionality(HttpSession session){
		DomainTransfer dt = employeeService.getFunctionality(session);
		return dt.get();
	}
	
	/**
	 * 查询手机号码是否已经存在
	 * @param cellphone
	 * */
	@ResponseBody
	@RequestMapping(path="/checkCell",method=RequestMethod.GET)
	public Object checkCell(@RequestParam("cellphone") String cellphone){
		logger.info("cellphone : " + cellphone);
		DomainTransfer dt = employeeService.checkCell(cellphone);
		return dt.get();
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
		DomainTransfer dt = employeeService.checkCode(company, code);
		return dt.get();
	}
	
	/**
	 * 得到所有职员
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/getAll",method=RequestMethod.GET)
	public Object getAll(){
		DomainTransfer dt =  employeeService.getAll();
		return dt.get();
	}
	
	/**
	 * 得到当前员工所在公司的所有职员
	 * @param request 用来得到session 
	 * */
	@ResponseBody
	@RequestMapping(path="/getByCompany",method=RequestMethod.GET)
	public Object getByCompany(HttpSession session){
		DomainTransfer dt = employeeService.getByCompany(session);
		return dt.get();
	}
	
	/**
	 * 删除职员
	 * @param id
	 * */
	@ResponseBody
	@RequestMapping(path="/delete",method=RequestMethod.GET)
	public Object delete(@RequestParam("id") int id){
		DomainTransfer dt = employeeService.delete(id);
		return dt.get();
	}
	
	/**
	 * 得到职员详细信息
	 * @param id
	 * */
	@ResponseBody
	@RequestMapping(path="/detail",method=RequestMethod.GET)
	public Object detail(@RequestParam("id") int id){
		DomainTransfer dt =  employeeService.detail(id);
		return dt.get();
	}
	
	/**
	 * 修改职员资料
	 * @param employee
	 * */
	@ResponseBody
	@RequestMapping(path="/update",method=RequestMethod.POST)
	public Object update(@ModelAttribute("employee") Employee employee){
		DomainTransfer dt = employeeService.update(employee);
		return dt.get();
	}
	
	/**
	 * 得到当前用户的角色
	 * @param session
	 * 
	 * */
	@RequestMapping(path="/getRole",method=RequestMethod.GET)
	@ResponseBody
	public Object getRole(HttpSession session){
		return employeeService.getRole(session).get();
	}
	
	/**
	 * 得到当前用户不拥有的角色
	 * @param session
	 * 
	 * */
	@RequestMapping(path="/getNotIn",method=RequestMethod.GET)
	@ResponseBody
	public Object getRoleNotIn(HttpSession session){
		return employeeService.getRoleNotIn(session).get();
	}
	
	/**
	 * 给当前用户添加角色
	 * @param session
	 * @param roleid
	 * */
	@RequestMapping(path="/addRole2Emp",method=RequestMethod.GET)
	@ResponseBody
	public Object addRole(@RequestParam("roleid") int roleid,HttpSession session){
		DomainTransfer dt = employeeService.addRole(roleid, session);
		return dt.get();
	}
	
	/**
	 * 移除当前用户的某角色
	 * @param session
	 * @param roleid
	 * */
	@RequestMapping(path="/removeRole",method=RequestMethod.GET)
	@ResponseBody
	public Object removeRole(@RequestParam("roleid") int roleid,HttpSession session){
		return employeeService.removeRole(roleid, session).get();
	}
	
}
