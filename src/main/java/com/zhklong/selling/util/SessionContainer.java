package com.zhklong.selling.util;

import java.util.HashMap;
import java.util.Map;

public class SessionContainer {
	
	private Map<String,SimSession> map = new HashMap<String,SimSession>();
	
	private final static int LIMIT = 100;
	
	private final static int OFFSET = 10 ; 
	
	public void put(String key,SimSession session){
		if(this.map.size() <= LIMIT - OFFSET){
			map.put(key, session);
		}else{
			clear();
			map.put(key, session);
		}
	}
	
	private void clear(){
		map.clear();
	}
	
	public SimSession get(String key){
		return map.get(key);
	}
	
	

}
