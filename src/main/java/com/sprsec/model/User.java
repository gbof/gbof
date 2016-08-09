package com.sprsec.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue
	private Integer user_id;
	
	private String login;
	
	private String password;
	
	private String mail;
	
	private String name;
	
	private String surname;
	
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="balls_id", referencedColumnName="balls_id")
	private Ball ball;
	
	@OneToMany
	@JoinColumn(name="user_id", referencedColumnName="user_id")
	private Set<Comment> comment;

	@ManyToOne
	@JoinColumn(name="dept_id", referencedColumnName="dept_id")
	private Department dept;
	
	@ManyToOne
	@JoinColumn(name="team_id", referencedColumnName="team_id")
	private Team team;	
	
	@OneToOne
	@JoinTable(name="user_roles",
		joinColumns = {@JoinColumn(name="user_id", referencedColumnName="user_id")},
		inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private Role role;

	public Integer getId() {
		return user_id;
	}

	public void setId(Integer user_id) {
		this.user_id = user_id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}


	public Ball getBall(){
		return ball;
	}
	
	public void setBall(Ball ball){
		this.ball = ball;
	}
	
	public Set<Comment> getComment(){
		return comment;
	}
	
	public void setComment(Set<Comment> comment){
		this.comment = comment;
	}
	
	public Department getDept(){
		return dept;
	}
	
	public void setDept(Department dept){
		this.dept = dept;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public void setTeam(Team team){
		this.team = team;
	}
	
}
