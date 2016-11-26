package com.zhklong.selling.util;

import java.util.Random;

public final class CodeGenerator {
	
	private CodeGenerator(){}
	
	private static final Random RANDOM = new Random();
	
	public static String generate(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<6;i++){
			int element = RANDOM.nextInt(10);
			sb.append(element);
		}
		return sb.toString();
	}

}
