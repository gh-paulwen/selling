package com.zhklong.selling.service;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.util.Session;

/**
 * @author paul
 * @since 2016-11-16 职员服务的接口
 * 
 * */
public interface IEmployeeService {

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
	Object login(Employee employee, Session session, String code);

	/**
	 * 发送验证码
	 * 
	 * @param session
	 *            用来获取用户的手机号码，以及保存生成的验证码
	 * @return json
	 * */
	Object sendCode( Session session);

	/**
	 * 验证用户输入的验证码
	 * 
	 * @param code
	 *            验证码
	 * @param session
	 *            得到之前生成的验证码
	 * @return json
	 * */
	Object verifyCode(String code,  Session session);

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
			 Session session);

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
	Object resetPassword(String cellphone,  Session session);

	/**
	 * 添加职员
	 * 
	 * @param employee
	 *            存有职员信息
	 * @param session
	 *            用于获取当前用户的信息
	 * 
	 * */
	Object save(Employee employee, Session session);

	/**
	 * 用职员id得到该只要所有的权限
	 * 
	 * @param session
	 *            职员id在session中
	 * @return 权限列表
	 * */
	Object getFunctionality(Session session);
	
	/**
	 * 查询手机号是否存在
	 * @param cellphone
	 * */
	Object checkCell(String cellphone);
	
	/**
	 * 查询公司中是否已经存在工号
	 * @param company
	 * @param code
	 * 
	 * */
	Object checkCode(int company,String code);
	
	/**
	 * 得到所有的职员
	 * */
	Object getAll();
	
	/**
	 * 得到当前用户所在公司的所有职员
	 * @param session 用以获取当前用户
	 * */
	Object getByCompany(Session session);
	
	/**
	 * 删除职员
	 * @param id
	 * 
	 * */
	Object delete(int id);
	
	/**
	 * 修改职员
	 * @param employee
	 * */
	Object update(Employee employee);
	
	/**
	 * 得到职员详细信息
	 * @param id
	 * */
	Object detail(int id);
}
