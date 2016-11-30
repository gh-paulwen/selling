package com.zhklong.selling.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

import org.apache.log4j.Logger;

public final class MemcachedUtil {
	
	private static final Logger logger = Logger.getLogger(MemcachedUtil.class.getName()); 
	
	private MemcachedUtil(){}
	
	private static MemcachedClient mmc  = null;
	
	private static final String HOST = "localhost";
	
	private static final int PORT = 11211;
	
	private static final int EXPTIME = 3600;
	
	public static void start(){
		try{
			InetSocketAddress ip = new InetSocketAddress(HOST,PORT);
			mmc = new MemcachedClient(ip);
		}catch(IOException e){
			logger.info(e.getMessage());
		}
	}
	
	public static void stop(){
		mmc.shutdown();
	}
	
	public static boolean add(String key,Object obj){
		boolean res = false; 
		try {
			OperationFuture<Boolean> future = mmc.add(key, EXPTIME, obj);
			res = future.get();
			logger.info("add obj , key :" + key );
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		} catch (ExecutionException e) {
			logger.info(e.getMessage());
		}
		return res; 
	}
	
	public static Object get(String key){
		return mmc.get(key);
	}
	
	public static void cas(String key ,Object obj){
		CASValue<Object> casValue = mmc.gets(key);
		mmc.cas(key, casValue.getCas(), obj);
		logger.info("cas , key : " + key);
	}

}
