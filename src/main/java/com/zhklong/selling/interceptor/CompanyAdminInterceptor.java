package com.zhklong.selling.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zhklong.selling.entity.Employee;

/**
 * 拦截并检查是否符合权限
 * 
 * @author paul
 * @since 2016-12-03
 * */
public class CompanyAdminInterceptor extends BaseInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (allow)
			return true;
		HttpSession session = request.getSession();
		if (session == null) {
			request.getRequestDispatcher("/input.jsp").forward(request,response);
			return false;
		}
		Employee curEmp = (Employee) session
				.getAttribute(Employee.CURRENT_EMPLOYEE);
		if (curEmp == null) {
			request.getRequestDispatcher("/input.jsp").forward(request,response);
			return false;
		}

		char type = curEmp.getType();
		if (type == Employee.TYPE_COMADMIN)
			allow = true;
		return true;
	}

}
