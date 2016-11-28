package com.zhklong.selling.util;

import java.util.HashMap;
import java.util.Map;

public class SimSession {
	
	Map<String,Object> map = new HashMap<String,Object>();
	
	public void setAttribute(String key , Object value){
		map.put(key, value);
	}
	
	public Object getAttribute(String key){
		return map.get(key);
	}
	
	public void removeAttribute(String key){
		map.remove(key);
	}

}
