package com.btc.model;

public class Employee {
	private String id;
	private String name;
	private String departmentID;
	public Employee(String id, String name, String departmentID) {
		super();
		this.id = id;
		this.name = name;
		this.departmentID = departmentID;
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
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	
	@Override
	public String toString() {
		return this.getName();
	
	}
	
}