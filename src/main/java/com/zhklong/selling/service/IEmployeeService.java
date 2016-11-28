package com.zhklong.selling.service;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.util.SimSession;

/**
 * @author paul
 * @since 2016-11-16 职员服务的接口
 * 
 * */
public interface IEmployeeService {

	/**
	 * 用来保存存在于session中当前职员的key
	 * */
	String CURRENT_EMPLOYEE = "currentEmployee";

	/**
	 * 提供给用户（职工）登录的功能
	 * 
	 * @param employee
	 *            仅包含cellphone和password的employee
	 * @param session
	 *            用来保存用户的cellphone
	 * @return json
	 * 
	 * */
	Object login(Employee employee, SimSession session, String code);

	/**
	 * 发送验证码
	 * 
	 * @param session
	 *            用来获取用户的手机号码，以及保存生成的验证码
	 * @return json
	 * */
	Object sendCode(SimSession session);

	/**
	 * 验证用户输入的验证码
	 * 
	 * @param code
	 *            验证码
	 * @param session
	 *            得到之前生成的验证码
	 * @return json
	 * */
	Object verifyCode(String code, SimSession session);

	/**
	 * 设置密码
	 * 
	 * @param password
	 * @param repeatPassword
	 * @param session
	 *            获取用户的cellphone
	 * @return json
	 * */
	Object setPassword(String password, String repeatPassword,
			SimSession session);

	/**
	 * 获取职员类型
	 * 
	 * @return json
	 * 
	 * */
	Object getEmployeeType();

	/**
	 * 重置密码申请
	 * 
	 * @param cellphone
	 * */
	Object resetPassword(String cellphone, SimSession session);

	/**
	 * 添加职员
	 * 
	 * @param employee
	 *            存有职员信息
	 * @param session
	 *            用于获取当前用户的信息
	 * 
	 * */
	Object save(Employee employee,SimSession session);

	/**
	 * 用职员id得到该只要所有的权限
	 * 
	 * @param session
	 *            职员id在session中
	 * @return 权限列表
	 * */
	Object getFunctionality(SimSession session);

}
