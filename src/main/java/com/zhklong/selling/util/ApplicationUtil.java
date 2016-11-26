package com.zhklong.selling.util;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public final class ApplicationUtil {

	private ApplicationUtil() {

	}

	private final static String IP_MAP = "ipSessionMap";
	
	private final static String SESSION_MAP = "sessionMap";

	public static Map<String, String> getIPMap(ServletContext application) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) application.getAttribute(IP_MAP);
		return map;
	}
	
	public static Map<String,HttpSession> getSessionMap(ServletContext application){
		@SuppressWarnings("unchecked")
		Map<String,HttpSession> map = (Map<String, HttpSession>) application.getAttribute(SESSION_MAP);
		return map;
	}
	
	public static void linkIPSession(String ip,HttpSession session){
		ServletContext application = session.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) application.getAttribute(IP_MAP);
		map.put(ip, session.getId());
	}
	
	public static HttpSession getSessionByIp(String ip,ServletContext application){
		Map<String,String> ipMap = getIPMap(application);
		Map<String,HttpSession> sessionMap = getSessionMap(application);
		String sessionid = ipMap.get(ip);
		if(sessionid != null){
			return sessionMap.get(sessionid);
		}
		return null;
	}

}
