package com.zhklong.selling.entity;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String CURRENT_EMPLOYEE = "currentEmployee";
	
	//type constants
	public static final char TYPE_SYSADMIN = 'a';
	
	public static final char TYPE_COMADMIN = '0';
	
	public static final char TYPE_STOREADMIN = '1';
	
	public static final char TYPE_STORAGEADMIN = '3';
	
	public static final char TYPE_SALESMAN = '2';
	
	
	private int id;
	
	private String code;
	
	private String name;
	
	private Integer company;
	
	private Integer store;
	
	private String cellphone;
	
	private char status;
	
	private Integer createEmployee;
	
	private Date createtime;
	
	private char deleted;
	
	private String password;
	
	private char passwordstatus;
	
	private char type;
	
	private String description;

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Integer getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(Integer createEmployee) {
		this.createEmployee = createEmployee;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public char getDeleted() {
		return deleted;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getPasswordstatus() {
		return passwordstatus;
	}

	public void setPasswordstatus(char passwordstatus) {
		this.passwordstatus = passwordstatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", code=" + code + ", name=" + name
				+ ", company=" + company + ", store=" + store + ", cellphone="
				+ cellphone + ", status=" + status + ", createEmployee="
				+ createEmployee + ", createtime=" + createtime + ", deleted="
				+ deleted + ", password=" + password + ", passwordstatus="
				+ passwordstatus + ", type=" + type + ", description="
				+ description + "]";
	}

}
