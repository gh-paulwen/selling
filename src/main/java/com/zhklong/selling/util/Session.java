package com.zhklong.selling.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Session implements Serializable{
	
	public static final String _ATTRIBUTE = "_session";
	
	private static final long serialVersionUID = 3972290815770859806L;
	
	private boolean change = false;
	
	private String key;
	
	public String getKey() {
		return key;
	}
	
	public Session(String key){
		this.key = key;
	}
	
	public void resetChange(){
		this.change = false;
	}
	
	public boolean isChange() {
		return change;
	}
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public void setAttribute(String key,Object obj){
		map.put(key, obj);
		this.change = true;
	}
	
	public Object getAttribute(String key){
		return map.get(key);
	}
	
	public void removeAttribute(String key){
		map.remove(key);
	}

}
