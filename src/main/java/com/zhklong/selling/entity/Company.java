package com.zhklong.selling.entity;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable{
	
	private static final long serialVersionUID = -5942167551100492911L;

	private int id;
	
	private String cellphone;
	
	private String name;
	
	private int region;
	
	private String address;
	
	private int reviser;
	
	private Date reviseTime;
	
	private int type;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getReviser() {
		return reviser;
	}

	public void setReviser(int reviser) {
		this.reviser = reviser;
	}

	public Date getReviseTime() {
		return reviseTime;
	}

	public void setReviseTime(Date reviseTime) {
		this.reviseTime = reviseTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;
}
