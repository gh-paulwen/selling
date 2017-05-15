package com.zhklong.selling.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhklong.selling.dto.DomainTransfer;
import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.entity.EmployeeType;
import com.zhklong.selling.entity.Functionality;
import com.zhklong.selling.entity.Role;
import com.zhklong.selling.mapper.EmployeeMapper;
import com.zhklong.selling.mapper.EmployeeTypeMapper;
import com.zhklong.selling.mapper.FunctionalityMapper;
import com.zhklong.selling.mapper.RoleMapper;
import com.zhklong.selling.service.IEmployeeService;
import com.zhklong.selling.util.CodeGenerator;
import com.zhklong.selling.util.SMSUtil;
import com.zhklong.selling.util.ValidateUtil;

/**
 * @author paul
 * @since 2016-11-16 职工服务类
 * 
 * */
@Service
public class EmployeeService implements IEmployeeService {
	
	private static final Logger logger = Logger.getLogger(EmployeeService.class.getName());

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private EmployeeTypeMapper employeeTypeMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private FunctionalityMapper functionalityMapper;

	@Autowired
	private SMSUtil smsUtil;
	
	public DomainTransfer login(Employee employee,  HttpSession session, String code) {
		DomainTransfer dt = new DomainTransfer();
		String cellphone = employee.getCellphone();
		String password = employee.getPassword();
		if (!ValidateUtil.checkCellphone(cellphone)) {
			logger.info("NOT a well formed cellphone number,cellphone : " + cellphone);
			dt.save("message", "不是有效的电话号码");
			return dt;
		}
		employee = employeeMapper.getByCell(employee.getCellphone());
		if (employee == null) {
			logger.info("UNREGISTERED cellphone number,cellphone : " + cellphone);
			dt.save("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return dt;
		}
		String sessionCode = (String) session.getAttribute("imageVerifyCode");
		if (sessionCode == null || !sessionCode.equalsIgnoreCase(code)) {
			logger.info("wrong verify code");
			dt.save("message", "验证码错误");
			return dt;
		}
		session.setAttribute("cellphone", employee.getCellphone());
		if (employee.getDeleted() == '1') {
			logger.info("a DELETED employee,cellphone : " + cellphone);
			dt.save("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return dt;
		}
		if (employee.getStatus() == '0') {
			logger.info("INVALID employee account,cellphone : " + cellphone);
			dt.save("message", "状态无效，请联系公司管理员");
			return dt;
		}
		if (employee.getPasswordstatus() == '0') {
			logger.info("first login,cellphone : " + cellphone);
			dt.save("redirect", 1);
			dt.save("message", "首次登录，请输入手机收到的短信验证码");
			return dt;
		}
		if (password == null || password.isEmpty()) {
			logger.info("the password is EMPTY,cellphone : " + cellphone);
			dt.save("message", "密码不能为空");
			return dt;
		}
		if (!password.trim().equals(employee.getPassword())) {
			logger.info("WRONG password,cellphone : " + cellphone);
			dt.save("message", "密码错误");
			return dt;
		}
		session.setAttribute(Employee.CURRENT_EMPLOYEE, employee);
		logger.info("success , cellphone : " + cellphone + ", employee name :" + employee.getName());
		dt.save("message", "登录成功");
		dt.save("redirect", 2);
		return dt;
	}

	public DomainTransfer sendCode(HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		String code = CodeGenerator.generate();
		session.setAttribute("verifyCode", code);
		String cellphone = (String) session.getAttribute("cellphone");
		logger.info("send sms verify code , cellphone : " + cellphone);
		if (cellphone == null || cellphone.isEmpty()){
			logger.info("send sms verify code but FAILED for cellphone is NOT exist , cellphone : " + cellphone);
			dt.save("message", "error");
			return dt;
		}
		smsUtil.sendVerifyCode(cellphone, code);
		logger.info("send sms verify code SUCCEEDED , cellphone : " + cellphone + ",sms code : " + code);
		dt.save("message", "success");
		return dt;
	}

	public DomainTransfer verifyCode(String code, HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		String message = "验证码错误";
		String sessionCode = (String) session.getAttribute("verifyCode");
		String cellphone  = (String) session.getAttribute("cellphone");
		if (sessionCode == null || sessionCode.isEmpty()) {
			logger.info("session sms code NOT exist");
			dt.save("message", "验证码未生成");
			return dt;
		}
		if (sessionCode.equals(code)) {
			logger.info("sms verification SUCCEEDED , cellphone : " + cellphone + ",sms code : " + code);
			message = "验证成功";
			dt.save("redirect", 1);
		}
		dt.save("message", message);
		return dt;
	}

	public DomainTransfer setPassword(String password, String repeatPassword, HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		
		if (password == null || repeatPassword == null || password.isEmpty()
				|| repeatPassword.isEmpty()) {
			logger.info("EMPTY password");
			dt.save("message", "密码不能为空");
			return dt;
		} 
		if (!password.trim().equals(repeatPassword.trim())) {
			logger.info("different two , one : " + password + ",other : "+repeatPassword);
			dt.save("message","两次输入不一致");
			return dt;
		} 
		synchronized (EmployeeService.this) {
			dt.save("redirect", 1);
			String cellphone = (String) session.getAttribute("cellphone");
			Employee employee = employeeMapper.getByCell(cellphone);
			employee.setPasswordstatus('1');
			employee.setPassword(password);
			employeeMapper.update(employee);
			logger.info("SUCCEEDED,cellphone : "+employee.getCellphone());
		}
		dt.save("message", "密码设置成功");
		return dt;
	}

	public DomainTransfer getEmployeeType() {
		DomainTransfer dt = new DomainTransfer();
		List<EmployeeType> list = employeeTypeMapper.getAll();
		dt.save("listEmployee", list);
		return dt;
	}

	public DomainTransfer resetPassword(String cellphone,  HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		if (!ValidateUtil.checkCellphone(cellphone)) {
			logger.info("NOT well formed cellphone , cellphone : " + cellphone);
			dt.save("message", "不是有效的电话号码");
			return dt;
		}
		session.setAttribute("cellphone", cellphone);
		Employee employee = employeeMapper.getByCell(cellphone);
		if (employee == null) {
			logger.info("NOT registered cellphone , cellphone : " + cellphone);
			dt.save("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return dt;
		}
		if (employee.getDeleted() == '1') {
			logger.info("deleted cellphone , cellphone : " + cellphone);
			dt.save("message", "非企业注册职工，请联系公司管理员为该手机号注册");
			return dt;
		}
		if (employee.getStatus() == '0') {
			logger.info("NOT valid status cellphone , cellphone : " + cellphone);
			dt.save("message", "状态无效，请联系公司管理员");
			return dt;
		}
		logger.info("ready to set password , cellphone : " + cellphone);
		dt.save("redirect", 1);
		dt.save("message", "手机号有效");
		dt.save("employee", employee);
		return dt;
	}

	public DomainTransfer save(Employee employee, HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if (curEmp == null) {
			logger.info("NO login");
			dt.save("message", "未登录");
			return dt;
		}
		if (employee.getCode() == null || employee.getCode().isEmpty()) {
			logger.info("EMPTY employee code");
			dt.save("message", "员工编号不能为空");
			return dt;
		}
		if (employee.getName() == null || employee.getName().isEmpty()) {
			logger.info("EMPTY employee name");
			dt.save("message", "员工姓名不能为空");
			return dt;
		}
		if (employee.getCellphone() == null	|| employee.getCellphone().isEmpty()) {
			logger.info("EMPTY employee cellphone");
			dt.save("message", "员工手机号不能为空");
			return dt;
		}
		if (!ValidateUtil.checkCellphone(employee.getCellphone())) {
			logger.info("NOT valid employee cellphone");
			dt.save("message", "不是有效的手机号码");
			return dt;
		}
		employee.setCompany(curEmp.getCompany());
		employee.setCreateEmployee(curEmp.getId());
		employeeMapper.insert(employee);
		dt.save("message", "添加成功");
		logger.info("SUCCEEDED");
		return dt;
	}

	public DomainTransfer getFunctionality(HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			logger.info("not login");
			return null;
		}
		List<Functionality> list = functionalityMapper.getByEmployee(curEmp.getId());
		dt.save("listFunctionality", list);
		logger.info("functionalitys : " + list.size());
		return dt;
	}

	public DomainTransfer checkCell(String cellphone) {
		DomainTransfer dt = new DomainTransfer();
		Employee emp = employeeMapper.getByCell(cellphone);
		if(emp == null){
			dt.save("result", 0);
		}else {
			dt.save("result", 1);
		}
		return dt;
	}

	public DomainTransfer checkCode(int company, String code) {
		DomainTransfer dt = new DomainTransfer();
		Employee emp = employeeMapper.getByCodeCompany(code, company);
		if(emp == null){
			dt.save("result", 0);
		}else {
			dt.save("result", 1);
		}
		return dt;
	}

	public DomainTransfer getAll() {
		DomainTransfer dt = new DomainTransfer();
		List<Employee> list =  employeeMapper.getAll();
		dt.save("listEmployee", list);
		return dt;
	}

	public DomainTransfer getByCompany(HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			dt.save("result", 0);
			return dt;
		}
		List<Employee> list = employeeMapper.getByCompany(curEmp.getCompany());
		dt.save("result", 1);
		dt.save("list", list);
		return dt;
	}

	public DomainTransfer delete(int id) {
		DomainTransfer dt = new DomainTransfer();
		Employee emp = employeeMapper.getById(id);
		if(emp == null){
			dt.save("message", "职员不存在");
			dt.save("result", 0);
			return dt;
		}
		emp.setDeleted('1');
		employeeMapper.update(emp);
		dt.save("message", "删除成功");
		dt.save("result", 1);
		logger.info("delete emp , id : " + emp.getId() + " , name : " + emp.getName());
		return dt;
	}

	public DomainTransfer update(Employee employee) {
		DomainTransfer dt = new DomainTransfer();
		Employee emp = employeeMapper.getById(employee.getId());
		if(emp == null){
			dt.save("message", "职员不存在");
			dt.save("result", 0);
			return dt;
		}
		employeeMapper.update(employee);
		dt.save("message", "修改成功");
		dt.save("result", 1);
		logger.info("update emp , id : " + employee.getId());
		return dt;
	}

	public DomainTransfer detail(int id) {
		DomainTransfer dt = new DomainTransfer();
		Employee emp = employeeMapper.getById(id);
		dt.save("employee", emp);
		return dt;
	}

	@Override
	public DomainTransfer getRole(HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			dt.save("message", "未登录");
			return dt;
		}
		List<Role> list = roleMapper.getByEmployee(curEmp.getId());
		dt.save("listRole", list);
		return dt;
	}

	@Override
	public DomainTransfer addRole(int roleid, HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			dt.save("message", "未登录");
			return dt;
		}
		roleMapper.addRole2Employee(curEmp.getId(), roleid);
		dt.save("message", "添加成功");
		return dt;
	}

	@Override
	public DomainTransfer removeRole(int roleid, HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			dt.save("message", "未登录");
			return dt;
		}
		roleMapper.removeRoleFromEmployee(curEmp.getId(), roleid);
		dt.save("message", "移除成功");
		return dt;
	}

	@Override
	public DomainTransfer getRoleNotIn(HttpSession session) {
		DomainTransfer dt = new DomainTransfer();
		Employee curEmp = (Employee) session.getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			dt.save("message", "未登录");
			return dt;
		}
		List<Role> list = roleMapper.getNotInEmployee(curEmp.getId());
		dt.save("listRole", list);
		return dt;
	}
}