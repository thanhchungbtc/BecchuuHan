package com.btc.model;

import java.util.Date;

import com.btc.supports.BukkenType;

public class Bukken {

	private String id;
	private String name;
	private String depsf;
	private int type; // 0 is XEVO, 1 is Σ
	private String shiten;
	private Date nouki;
	public Bukken() {
		
	}
	public Bukken(String id, String name, String depsf, int type, String shiten, Date nouki) {
		this.id = id;
		this.name = name;
		this.depsf = depsf;
		this.type = type;
		this.shiten = shiten;
		this.nouki = nouki;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepsf() {
		return depsf;
	}
	public void setDepsf(String depsf) {
		this.depsf = depsf;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getShiten() {
		return shiten;
	}
	public void setShiten(String shiten) {
		this.shiten = shiten;
	}
	public Date getNouki() {
		return nouki;
	}
	public void setNouki(Date nouki) {
		this.nouki = nouki;
	}
}
