package com.zhklong.selling.entity;

public class EmployeeType {

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
