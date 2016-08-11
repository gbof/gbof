package com.ericsson.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="teams")
public class Team {
	@Id
	@GeneratedValue
	@Column(name="team_id")
	private Integer team_id;
	@Column(name="team_name")
	private String team_name;
	@Column(name="dept_id")
	private Integer dept_id;
	@OneToMany
	@JoinColumn(name="team_id", referencedColumnName="team_id")
	private Set<User> users;
	
	
	public Integer getId(){
		return team_id;
	}
	
	public void setId(Integer team_id){
		this.team_id = team_id;
	}
	
	
	public String getName(){
		return this.team_name;
	}

	public void setName(String team_name){
		this.team_name = team_name;
	}
	
	
	public Integer getDeptId(){
		return dept_id;
	}
	
	public void setDeptId(Integer dept_id){
		this.dept_id = dept_id;
	}
	
	public Set<User> getUsers(){
		return users;
	}
	
	public void setUsers(Set<User> users){
		this.users = users;
	}
}
