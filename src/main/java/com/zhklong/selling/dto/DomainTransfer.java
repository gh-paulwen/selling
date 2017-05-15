package com.zhklong.selling.dto;

import java.util.HashMap;
import java.util.Map;

public class DomainTransfer {
	
	private Map<String,Object> map = new HashMap<>();
	
	
	public void save(String key,Object obj){
		map.put(key, obj);
	}
	
	public void remove(String key){
		map.remove(key);
	}
	
	public Map<String,Object> get(){
		return map;
	}
	
}
