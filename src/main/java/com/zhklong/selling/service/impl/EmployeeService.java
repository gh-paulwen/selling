package com.zhklong.selling.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.entity.EmployeeType;
import com.zhklong.selling.entity.Functionality;
import com.zhklong.selling.mapper.EmployeeMapper;
import com.zhklong.selling.mapper.EmployeeTypeMapper;
import com.zhklong.selling.mapper.FunctionalityMapper;
import com.zhklong.selling.service.IEmployeeService;
import com.zhklong.selling.util.CodeGenerator;
import com.zhklong.selling.util.SMSUtil;
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

	public Object login(Employee employee, HttpSession session,String ip, String code) {
//		ServletContext application = session.getServletContext();
//		Map<String,String> cellphoneMap = ApplicationUtil.getMap(application, "cellphoneMap");
//		Map<String,String> imageVerifyCodeMap = ApplicationUtil.getMap(application, "imageVerifyCodeMap");
		Map<String, Object> json = new HashMap<String, Object>();
		String cellphone = employee.getCellphone();
		String password = employee.getPassword();
		if (!ValidateUtil.checkCellphone(cellphone)) {
			json.put("message", "不是有效的电话号码");
			return json;
		}
		employee = employeeMapper.getByCell(employee.getCellphone());
		if (employee == null) {
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
//		String sessionCode = imageVerifyCodeMap.get(ip);
		String sessionCode = (String) session.getAttribute("imageVerifyCode");
		if (sessionCode == null || !sessionCode.equalsIgnoreCase(code)) {
			json.put("message", "验证码错误");
			return json;
		}
		session.setAttribute("cellphone", employee.getCellphone());
		if (employee.getDeleted() == '1') {
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		if (employee.getStatus() == '0') {
			json.put("message", "状态无效，请联系公司管理员");
			return json;
		}
		if (employee.getPasswordstatus() == '0') {
			json.put("redirect", 1);
			json.put("message", "首次登录，请输入手机收到的短信验证码");
			return json;
		}
		if (password == null || password.isEmpty()) {
			json.put("message", "密码不能为空");
			return json;
		}
		if (!password.trim().equals(employee.getPassword())) {
			json.put("message", "密码错误");
			return json;
		}
		session.setAttribute(CURRENT_EMPLOYEE, employee);
		json.put("message", "登录成功");
		json.put("redirect", 2);
		return json;
	}

	public Object sendCode(HttpSession session) {
		String code = CodeGenerator.generate();
		session.setAttribute("verifyCode", code);
		String cellphone = (String) session.getAttribute("cellphone");
		System.out.println(cellphone);
		if (cellphone == null || cellphone.isEmpty())
			return "error";
		smsUtil.sendVerifyCode(cellphone, code);
		return "success";
	}

	public Object verifyCode(String code, HttpSession session) {
		Map<String, Object> json = new HashMap<String, Object>();
		String message = "验证码错误";
		String sessionCode = (String) session.getAttribute("verifyCode");
		if (sessionCode == null || sessionCode.isEmpty()) {

		}
		if (sessionCode.equals(code)) {
			message = "验证成功";
			json.put("redirect", 1);
		}
		json.put("message", message);
		return json;
	}

	public Object setPassword(String password, String repeatPassword,
			HttpSession session) {
		Map<String, Object> json = new HashMap<String, Object>();
		
		if (password == null || repeatPassword == null || password.isEmpty()
				|| repeatPassword.isEmpty()) {
			json.put("message", "密码不能为空");
			return json;
		} 
		if (!password.trim().endsWith(repeatPassword.trim())) {
			json.put("message","两次输入不一致");
			return json;
		} 
		synchronized (this) {
			json.put("redirect", 1);
			String cellphone = (String) session.getAttribute("cellphone");
			Employee employee = employeeMapper.getByCell(cellphone);
			employee.setPasswordstatus('1');
			employee.setPassword(password);
			employeeMapper.update(employee);
		}
		json.put("message", "密码设置成功");
		return json;
	}

	public Object getEmployeeType() {
		List<EmployeeType> list = employeeTypeMapper.getAll();
		return list;
	}

	public Object resetPassword(String cellphone, HttpSession session) {
		Map<String, Object> json = new HashMap<String, Object>();
		if (!ValidateUtil.checkCellphone(cellphone)) {
			json.put("message", "不是有效的电话号码");
			return json;
		}
		session.setAttribute("cellphone", cellphone);
		Employee employee = employeeMapper.getByCell(cellphone);
		if (employee == null) {
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		if (employee.getDeleted() == '1') {
			json.put("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return json;
		}
		if (employee.getStatus() == '0') {
			json.put("message", "状态无效，请联系公司管理员");
			return json;
		}
		json.put("redirect", 1);
		json.put("message", "手机号有效");
		json.put("employee", employee);
		return json;
	}

	public Object save(Employee employee, HttpSession session) {
		Map<String, Object> json = new HashMap<String, Object>();

		Employee curEmp = (Employee) session.getAttribute(CURRENT_EMPLOYEE);

		if (curEmp == null) {
			json.put("message", "未登录");
			return json;
		}
		if (employee.getCode() == null || employee.getCode().isEmpty()) {
			json.put("message", "员工编号不能为空");
			return json;
		}
		if (employee.getName() == null || employee.getName().isEmpty()) {
			json.put("message", "员工姓名不能为空");
			return json;
		}
		if (employee.getCellphone() == null
				|| employee.getCellphone().isEmpty()) {
			json.put("message", "员工手机号不能为空");
			return json;
		}
		if (!ValidateUtil.checkCellphone(employee.getCellphone())) {
			json.put("message", "不是有效的手机号码");
			return json;
		}
		employee.setCompany(curEmp.getCompany());
		employee.setStore(curEmp.getStore());
		employee.setCreateEmployee(curEmp.getId());
		employeeMapper.insert(employee);
		json.put("message", "添加成功");
		return json;
	}

	public Object getFunctionality(HttpSession session) {
		Employee curEmp = (Employee) session.getAttribute(CURRENT_EMPLOYEE);
		if(curEmp == null)
			return null;
		List<Functionality> list = functionalityMapper.getByEmployee(curEmp.getId());
		return list;
	}

}