package com.disha.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
	 private String name;
	 
	 @ManyToOne  // required annotation 
	 @JoinColumn(name = "department_ID" ) // Employee is the owning side , owner is the one who has foreign key column 
	 private Department dept;
     @Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + " + department name " + dept.getName() + "]";
	}
     
     public Employee(String name) {
	 this.name=name;
	}
     public Employee(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public void setName(String name) {
		this.name = name;
	}
     
}
