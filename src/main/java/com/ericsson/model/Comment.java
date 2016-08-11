package com.ericsson.model;

import javax.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
	
	@Id
	@GeneratedValue
	@Column(name="com_id")
	private Integer com_id;
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="user_id")
	private User user;
	@Column(name="first_com")
	private String first_com;
	@Column(name="second_com")
	private String second_com;
	@Column(name="confirmed")
	private boolean confirmed;
	@Column(name="creator_id")
	private Integer creator_id;
	@Column(name="balls_per_com")
	private Integer balls_per_com;
	
	
	public Integer getComId(){
		return com_id;
	}
	
	public void setComId(Integer com_id){
		this.com_id = com_id;
	}
	

	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	
	public String getFirstCom(){
		return first_com;
	}
	
	public void setFirstCom(String first_com){
		this.first_com = first_com;
	}
	

	public String getSecondCom(){
		return second_com;
	}
	
	public void setSecondCom(String second_com){
		this.second_com = second_com;
	}
	
	
	public boolean getConfirmed(){
		return confirmed;
	}
	
	public void setConfirmed(boolean confirmed){
		this.confirmed = confirmed;
	}
	
	
	public Integer getCreatorId(){
		return creator_id;
	}
	
	public void set(Integer creator_id){
		this.creator_id = creator_id;
	}
	
	
	public Integer getBallsPerCom(){
		return balls_per_com;
	}
	
	public void setBallsPerCom(Integer balls_per_com){
		this.balls_per_com = balls_per_com;
	}
	
}
