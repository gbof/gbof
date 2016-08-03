package com.sprsec.model;

import javax.persistence.*;

@Entity
@Table(name="balls")
public class Ball {

	@Id
	@GeneratedValue
	@Column(name="balls_id")
	private Integer balls_id;
	@Column(name="received_balls")
	private Integer received_balls;
	@Column(name="balls_to_give")
	private Integer balls_to_give;
	@Column(name="locked")
	private boolean locked;
	@Column(name="cash")
	private double cash;
	
	@OneToOne
	@JoinColumn(name="balls_id", referencedColumnName="balls_id")
	private User user;
	
	
	public Integer getBallsId(){
		return balls_id;
	}
	
	public void setBallsId(Integer balls_id){
		this.balls_id = balls_id;
	}
	
	
	public Integer getReceivedBalls(){
		return received_balls;
	}
	
	public void setReceivedBalls(Integer received_balls){
		this.received_balls = received_balls;
	}
	
	
	public Integer getBallsToGive(){
		return balls_to_give;
	}
	
	public void setBallsToGive(Integer balls_to_give){
		this.balls_to_give = balls_to_give;
	}
	
	
	public boolean getLocked(){
		return locked;
	}
	
	public void setLocked(boolean locked){
		this.locked = locked;
	}
	
	
	public double getCash(){
		return cash;
	}
	
	public void setCash(double cash){
		this.cash = cash;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
}
