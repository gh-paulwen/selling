package com.zhklong.selling.entity;

import java.io.Serializable;

public class Functionality implements Serializable{

	private static final long serialVersionUID = 377021557737364816L;

	private int id;

	private String name;

	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
