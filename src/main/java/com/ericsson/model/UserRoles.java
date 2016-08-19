package com.ericsson.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRoles {

	@Id
	@GeneratedValue
	@Column(name="role_id")
	private Integer role_id;
	
	@Column(name="user_id")
	private Integer user_id;
	
	
	public Integer getRoleId(){
		return role_id;
	}
	
	public void setRoleId(Integer role_id){
		this.role_id = role_id;
	}
	
	public Integer getUserId(){
		return user_id;
	}
	
	public void setUserId(Integer user_id){
		this.user_id = user_id;
	}
	
}
