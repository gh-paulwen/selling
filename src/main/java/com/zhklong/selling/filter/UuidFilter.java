package com.zhklong.selling.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zhklong.selling.util.ApplicationUtil;
import com.zhklong.selling.util.SimSession;

/**
 * @author paul
 * @since 2016-11-27 拦截请求以获取uuid并在第一次访问时创建SimSession，最后把这个SimSession放到request域中
 * @see com.zhklong.selling.util.SimSession
 * 
 * */
public class UuidFilter implements Filter {
	
	private final static Logger logger = Logger.getLogger(UuidFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 允许跨域
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
		// response.setHeader("Access-Control-Allow-Methods",
		// "POST, GET, OPTIONS, DELETE");
		// response.setHeader("Access-Control-Max-Age", "3600");
		// response.setHeader("Access-Control-Allow-Headers",
		// "x-requested-with");
		((HttpServletResponse)response).addHeader("Access-Control-Allow-Credentials", "true");

		String uuid = request.getParameter("uuid");
		if (uuid == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "not with uuid");
			logger.info("not with uuid");
			String str = new Gson().toJson(map);
			response.getWriter().print(str);
			response.getWriter().flush();
		} else {
			SimSession origin = ApplicationUtil.getByUuid(uuid,
					request.getServletContext());
			if (origin == null) {
				// 用来代替session域
				origin = new SimSession();
				ApplicationUtil.putIntoContainer(uuid, origin,
						request.getServletContext());
			}
			request.setAttribute("SIM_SESSION", origin);
			chain.doFilter(request, response);
		}
	}

	public void destroy() {

	}

}
