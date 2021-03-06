package com.zhklong.selling.entity;

import java.io.Serializable;

public class EmployeeType implements Serializable{

	private static final long serialVersionUID = -1657561900872733522L;

	private char code;

	private String name;

	public char getCode() {
		return code;
	}

	public void setCode(char code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EmployeeType [code=" + code + ", name=" + name + "]";
	}
	
	

}
