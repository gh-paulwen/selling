package com.zhklong.selling.listener;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zhklong.selling.util.ApplicationUtil;

public class SessionLifeListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent se) {
		Map<String,HttpSession> sessionMap = ApplicationUtil.getSessionMap(se.getSession().getServletContext());
		sessionMap.put(se.getSession().getId(),se.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		Map<String,HttpSession> sessionMap = ApplicationUtil.getSessionMap(se.getSession().getServletContext());
		Map<String,String> ipMap = ApplicationUtil.getIPMap(se.getSession().getServletContext());
		sessionMap.remove(se.getSession().getId());
		for(String key:ipMap.keySet()){
			String value = ipMap.get(key);
			if(se.getSession().getId().equals(value)){
				ipMap.remove(key);
				break;
			}
		}
	}

}
