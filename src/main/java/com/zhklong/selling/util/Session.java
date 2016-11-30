package com.zhklong.selling.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Session implements Serializable{
	private static final long serialVersionUID = 3972290815770859806L;
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public void setAttribute(String key,Object obj){
		map.put(key, obj);
	}
	
	public Object getAttribute(String key){
		return map.get(key);
	}

}
