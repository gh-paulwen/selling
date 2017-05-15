package com.zhklong.selling.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhklong.selling.entity.Employee;
import com.zhklong.selling.entity.Functionality;
import com.zhklong.selling.mapper.FunctionalityMapper;

public class FunctionalityInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = Logger.getLogger(FunctionalityInterceptor.class);
	
	@Autowired
	private FunctionalityMapper fm;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI().substring(request.getContextPath().length());
		
		List<Functionality> getByUrl = fm.getByUrl(url);
		if(getByUrl.size() < 1){
			return true;
		}
		
		logger.info("getByUrl size:" + getByUrl.size());
		
		Employee curEmp = (Employee) request.getSession().getAttribute(Employee.CURRENT_EMPLOYEE);
		if(curEmp == null){
			response.getWriter().write("not authenticated");
			return false;
		}
		List<Functionality> getByEmp = fm.getByEmployee(curEmp.getId());
		logger.info("getByEmp size:" + getByUrl.size());
		for(Functionality func:getByUrl){
			for(Functionality func2:getByEmp){
				if(func.getId() == func2.getId()){
					return true;
				}
			}
		}
		
		response.getWriter().write("not authenticated");
		return false;
	}

}
