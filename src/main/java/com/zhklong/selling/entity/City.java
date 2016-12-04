package com.zhklong.selling.entity;

import java.io.Serializable;

public class City implements Serializable{

	private static final long serialVersionUID = 2325267237834515341L;
	
	private int id;
	
	private String name;
	
	private int superCity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSuperCity() {
		return superCity;
	}

	public void setSuperCity(int superCity) {
		this.superCity = superCity;
	}
	
	

}
