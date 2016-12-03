package com.zhklong.selling.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.entity.EmployeeType;
import com.zhklong.selling.entity.Functionality;
import com.zhklong.selling.mapper.EmployeeMapper;
import com.zhklong.selling.mapper.EmployeeTypeMapper;
import com.zhklong.selling.mapper.FunctionalityMapper;
import com.zhklong.selling.service.IEmployeeService;
import com.zhklong.selling.util.CodeGenerator;
import com.zhklong.selling.util.SMSUtil;
import com.zhklong.selling.util.Session;
import com.zhklong.selling.util.ValidateUtil;

/**
 * @author paul
 * @since 2016-11-16 职工服务类
 * 
 * */
public class EmployeeService implements IEmployeeService {

	private EmployeeMapper employeeMapper;

	private EmployeeTypeMapper employeeTypeMapper;
	
	private FunctionalityMapper functionalityMapper;

	private SMSUtil smsUtil;
	
	private static final Logger logger = Logger.getLogger(EmployeeService.class.getName()); 

	public void setEmployeeMapper(EmployeeMapper employeeMapper) {
		this.employeeMapper = employeeMapper;
	}

	public void setEmployeeTypeMapper(EmployeeTypeMapper employeeTypeMapper) {
		this.employeeTypeMapper = employeeTypeMapper;
	}
	
	public void setFunctionalityMapper(FunctionalityMapper functionalityMapper) {
		this.functionalityMapper = functionalityMapper;
	}

	public void setSmsUtil(SMSUtil smsUtil) {
		this.smsUtil = smsUtil;
	}

	public Object login(Employee employee,  Session session, String code) {
		Map<String, Object> json = new HashMap<String, Object>();
		String cellphone = employee.getCellphone();
		String password = employee.getPassword();
		if (!ValidateUtil.checkCellphone(cellphone)) {
			logger.info("NOT a well formed cellphone number,cellphone : " + cellphone);
			json.put("message", "不是有效的电话号码");
			return json;
		}
		employee = employeeMapper.getByCell(employee.getCellphone());
		if (employee == null) {
			logger.info("UNREGISTERED cellphone number,cellphone : " + cellphone);
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		String sessionCode = (String) session.getAttribute("imageVerifyCode");
		if (sessionCode == null || !sessionCode.equalsIgnoreCase(code)) {
			logger.info("wrong verify code");
			json.put("message", "验证码错误");
			return json;
		}
		session.setAttribute("cellphone", employee.getCellphone());
		if (employee.getDeleted() == '1') {
			logger.info("a DELETED employee,cellphone : " + cellphone);
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		if (employee.getStatus() == '0') {
			logger.info("INVALID employee account,cellphone : " + cellphone);
			json.put("message", "状态无效，请联系公司管理员");
			return json;
		}
		if (employee.getPasswordstatus() == '0') {
			logger.info("first login,cellphone : " + cellphone);
			json.put("redirect", 1);
			json.put("message", "首次登录，请输入手机收到的短信验证码");
			return json;
		}
		if (password == null || password.isEmpty()) {
			logger.info("the password is EMPTY,cellphone : " + cellphone);
			json.put("message", "密码不能为空");
			return json;
		}
		if (!password.trim().equals(employee.getPassword())) {
			logger.info("WRONG password,cellphone : " + cellphone);
			json.put("message", "密码错误");
			return json;
		}
		session.setAttribute(Employee.CURRENT_EMPLOYEE, employee);
		logger.info("success , cellphone : " + cellphone + ", employee name :" + employee.getName());
		json.put("message", "登录成功");
		json.put("redirect", 2);
		return json;
	}

	public Object sendCode(Session session) {
		Map<String,Object> json = new HashMap<String, Object>();
		String code = CodeGenerator.generate();
		session.setAttribute("verifyCode", code);
		String cellphone = (String) session.getAttribute("cellphone");
		logger.info("send sms verify code , cellphone : " + cellphone);
		if (cellphone == null || cellphone.isEmpty()){
			logger.info("send sms verify code but FAILED for cellphone is NOT exist , cellphone : " + cellphone);
			json.put("message", "error");
			return json;
		}
		smsUtil.sendVerifyCode(cellphone, code);
		logger.info("send sms verify code SUCCEEDED , cellphone : " + cellphone + ",sms code : " + code);
		json.put("message", "success");
		return json;
	}

	public Object verifyCode(String code, Session session) {
		Map<String, Object> json = new HashMap<String, Object>();
		String message = "验证码错误";
		String sessionCode = (String) session.getAttribute("verifyCode");
		String cellphone  = (String) session.getAttribute("cellphone");
		if (sessionCode == null || sessionCode.isEmpty()) {
			logger.info("session sms code NOT exist");
		}
		if (sessionCode.equals(code)) {
			logger.info("sms verification SUCCEEDED , cellphone : " + cellphone + ",sms code : " + code);
			message = "验证成功";
			json.put("redirect", 1);
		}
		json.put("message", message);
		return json;
	}

	public Object setPassword(String password, String repeatPassword, Session session) {
		Map<String, Object> json = new HashMap<String, Object>();
		
		if (password == null || repeatPassword == null || password.isEmpty()
				|| repeatPassword.isEmpty()) {
			logger.info("EMPTY password");
			json.put("message", "密码不能为空");
			return json;
		} 
		if (!password.trim().equals(repeatPassword.trim())) {
			logger.info("different two , one : " + password + ",other : "+repeatPassword);
			json.put("message","两次输入不一致");
			return json;
		} 
		synchronized (json) {
			json.put("redirect", 1);
			String cellphone = (String) session.getAttribute("cellphone");
			Employee employee = employeeMapper.getByCell(cellphone);
			employee.setPasswordstatus('1');
			employee.setPassword(password);
			employeeMapper.update(employee);
			logger.info("SUCCEEDED,cellphone : "+employee.getCellphone());
		}
		json.put("message", "密码设置成功");
		return json;
	}

	public Object getEmployeeType() {
		List<EmployeeType> list = employeeTypeMapper.getAll();
		return list;
	}

	public Object resetPassword(String cellphone,  Session session) {
		Map<String, Object> json = new HashMap<String, Object>();
		if (!ValidateUtil.checkCellphone(cellphone)) {
			logger.info("NOT well formed cellphone , cellphone : " + cellphone);
			json.put("message", "不是有效的电话号码");
			return json;
		}
		session.setAttribute("cellphone", cellphone);
		Employee employee = employeeMapper.getByCell(cellphone);
		if (employee == null) {
			logger.info("NOT registered cellphone , cellphone : " + cellphone);
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		if (employee.getDeleted() == '1') {
			logger.info("deleted cellphone , cellphone : " + cellphone);
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		if (employee.getStatus() == '0') {
			logger.info("NOT valid status cellphone , cellphone : " + cellphone);
			json.put("message", "状态无效，请联系公司管理员");
			return json;
		}
		logger.info("ready to set password , cellphone : " + cellphone);
		json.put("redirect", 1);
		json.put("message", "手机号有效");
		json.put("employee", employee);
		return json;
	}

	public Object save(Employee employee, Session session) {
		Map<String, Object> json = new HashMap<String, Object>();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if (curEmp == null) {
			logger.info("NO login");
			json.put("message", "未登录");
			return json;
		}
		if (employee.getCode() == null || employee.getCode().isEmpty()) {
			logger.info("EMPTY employee code");
			json.put("message", "员工编号不能为空");
			return json;
		}
		if (employee.getName() == null || employee.getName().isEmpty()) {
			logger.info("EMPTY employee name");
			json.put("message", "员工姓名不能为空");
			return json;
		}
		if (employee.getCellphone() == null	|| employee.getCellphone().isEmpty()) {
			logger.info("EMPTY employee cellphone");
			json.put("message", "员工手机号不能为空");
			return json;
		}
		if (!ValidateUtil.checkCellphone(employee.getCellphone())) {
			logger.info("NOT valid employee cellphone");
			json.put("message", "不是有效的手机号码");
			return json;
		}
		employee.setCompany(curEmp.getCompany());
		employee.setCreateEmployee(curEmp.getId());
		employeeMapper.insert(employee);
		json.put("message", "添加成功");
		logger.info("SUCCEEDED");
		return json;
	}

	public Object getFunctionality( Session session) {
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			logger.info("not login");
			return null;
		}
		List<Functionality> list = functionalityMapper.getByEmployee(curEmp.getId());
		logger.info("functionalitys : " + list.size());
		return list;
	}

	public Object checkCell(String cellphone) {
		Employee emp = employeeMapper.getByCell(cellphone);
		Map<String,Object> json = new HashMap<String,Object>();
		if(emp == null){
			json.put("result", 0);
		}else {
			json.put("result", 1);
		}
		return json;
	}

	public Object checkCode(int company, String code) {
		Employee emp = employeeMapper.getByCodeCompany(code, company);
		Map<String,Object> json = new HashMap<String,Object>();
		if(emp == null){
			json.put("result", 0);
		}else {
			json.put("result", 1);
		}
		return json;
	}

	public Object getAll() {
		return employeeMapper.getAll();
	}

	public Object getByCompany(Session session) {
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		Map<String,Object> json = new HashMap<String,Object>();
		if(curEmp == null){
			json.put("result", 0);
			return json;
		}
		List<Employee> list = employeeMapper.getByCompany(curEmp.getCompany());
		json.put("result", 1);
		json.put("list", list);
		return json;
	}

	public Object delete(int id) {
		Map<String,Object> json = new HashMap<String,Object>();
		Employee emp = employeeMapper.getById(id);
		if(emp == null){
			json.put("message", "职员不存在");
			json.put("result", 0);
			return json;
		}
		emp.setDeleted('1');
		employeeMapper.update(emp);
		json.put("message", "删除成功");
		json.put("result", 1);
		logger.info("delete emp , id : " + emp.getId() + " , name : " + emp.getName());
		return json;
	}

	public Object update(Employee employee) {
		Map<String,Object> json = new HashMap<String,Object>();
		Employee emp = employeeMapper.getById(employee.getId());
		if(emp == null){
			json.put("message", "职员不存在");
			json.put("result", 0);
			return json;
		}
		employeeMapper.update(employee);
		json.put("message", "修改成功");
		json.put("result", 1);
		logger.info("update emp , id : " + employee.getId());
		return json;
	}

	public Object detail(int id) {
		return employeeMapper.getById(id);
	}
}