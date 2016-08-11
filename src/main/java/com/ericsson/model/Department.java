package com.ericsson.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="departments")
public class Department {

	@Id
	@GeneratedValue
	@Column(name="dept_id")
	private Integer dept_id;
	@Column(name="dept_name")
	private String dept_name;
	@Column(name="dept_leader_id")
	private Integer dept_leader_id;

	@OneToMany
	@JoinColumn(name="dept_id", referencedColumnName="dept_id")
	private Set<User> users;
	
	
	public Integer getDeptId(){
		return dept_id;
	}
	
	public void setDeptId(Integer dept_id){
		this.dept_id = dept_id;
	}
	
	
	public String getDeptName(){
		return dept_name;
	}
	
	public void setDeptName(String dept_name){
		this.dept_name = dept_name;
	}
	
	
	public Integer getDeptLeaderId(){
		return dept_leader_id;
	}
	
	public void setDeptLeaderId(Integer dept_leader_id){
		this.dept_leader_id = dept_leader_id;
	}
	
	
	public Set<User> getUser(){
		return users;
	}
	
	public void setUser(Set<User> users){
		this.users = users;
	}
	
	

	
	
	
}
