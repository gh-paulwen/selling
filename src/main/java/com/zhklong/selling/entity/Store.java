package com.zhklong.selling.entity;

import java.io.Serializable;
import java.util.Date;

public class Store implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private int region;

	private String address;

	private int createEmployee;

	private Date createTime;

	private int company;

	private String desciption;

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

	public int getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(int createEmployee) {
		this.createEmployee = createEmployee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

}
