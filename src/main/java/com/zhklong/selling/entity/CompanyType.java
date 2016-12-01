package com.zhklong.selling.entity;

import java.io.Serializable;

public class CompanyType implements Serializable{
	
	private static final long serialVersionUID = -1054180915011758771L;

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

}
