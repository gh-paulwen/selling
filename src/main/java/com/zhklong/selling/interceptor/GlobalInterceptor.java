package com.zhklong.selling.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.zhklong.selling.util.MemcachedUtil;
import com.zhklong.selling.util.Session;

public class GlobalInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = Logger.getLogger(GlobalInterceptor.class
			.getName());

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 允许跨域
		((HttpServletResponse) response).setHeader(
				"Access-Control-Allow-Origin", "*");
		// response.setHeader("Access-Control-Allow-Methods",
		// "POST, GET, OPTIONS, DELETE");
		// response.setHeader("Access-Control-Max-Age", "3600");
		// response.setHeader("Access-Control-Allow-Headers",
		// "x-requested-with");
		((HttpServletResponse) response).addHeader(
				"Access-Control-Allow-Credentials", "true");

		String uuid = request.getParameter("uuid");
		if (uuid == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "not with uuid");
			logger.info("not with uuid");
			String str = new Gson().toJson(map);
			response.getWriter().print(str);
			response.getWriter().flush();
			return false;
		} else {
			Session origin = (Session) MemcachedUtil.get(uuid);
			if (origin == null) {
				// 用来代替session域
				origin = new Session();
				MemcachedUtil.add(uuid, origin);
			}
			request.setAttribute("SESSION", origin);
			request.setAttribute("uuid", uuid);
			return true;
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Session afterProcess = (Session) request.getAttribute("SESSION");
		String afteruuid = (String) request.getAttribute("uuid");
		if(afteruuid == null || afterProcess == null){
			logger.info("data lost");
			return;
		}
		MemcachedUtil.cas(afteruuid, afterProcess);
	}

}
