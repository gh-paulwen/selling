package com.zhklong.selling.util;

import javax.servlet.ServletContext;

/**
 * @author paul
 * @since 2016-11-24
 * 一个处理用户唯一标识的工具类
 * 
 * */
public final class ApplicationUtil {

	private ApplicationUtil() {

	}

	private final static String UUID_CONTAINER = "uuidContainer";
	
	private static SessionContainer getUuidContainer(ServletContext application){
		SessionContainer container = (SessionContainer) application.getAttribute(UUID_CONTAINER);
		return container;
	}
	
	public static SimSession getByUuid(String uuid,ServletContext application){
		SessionContainer container = getUuidContainer(application);
		SimSession map = container.get(uuid);
		return map;
	}
	
	public static void putIntoContainer(String uuid,SimSession session,ServletContext application){
		SessionContainer container = getUuidContainer(application);
		container.put(uuid, session);
	}

}
