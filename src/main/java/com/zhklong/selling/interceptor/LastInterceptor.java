package com.zhklong.selling.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限控制的最后一个拦截器
 * @author paul
 * @since 2016-12-03
 * 
 * */
public class LastInterceptor extends BaseInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(!allow){
			request.getRequestDispatcher("/input.jsp").forward(request, response);
			return false;
		}
		allow = false;
		return true;
	}

}
