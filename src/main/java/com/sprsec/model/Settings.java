package com.sprsec.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="settings")
public class Settings {
	
	@Id
	@GeneratedValue
	@Column(name="settings_id")
	private Integer settings_id;
	@Column(name="extra_balls")
	private Integer extra_balls;
	@Column(name="balls_per_person")
	private Integer balls_per_person;
	@Column(name="money")
	private double money;
	@Column(name="deadline")
	private Date deadline;
	@Column(name="freeze")
	private boolean freeze;
	@Column(name="balls_left")
	private Integer balls_left;
	
	
	public Integer getSettingsID(){
		return settings_id;
	}
	
	public void setSettingsID(Integer settings_id){
		this.settings_id = settings_id;
	}
	
	public Integer getExtraBalls(){
		return extra_balls;
	}
	
	public void setExtraBalls(Integer extra_balls){
		this.extra_balls = extra_balls;
	}
	
	
	public Integer getBallsPerPerson(){
		return balls_per_person;
	}
	
	public void setBallsPerPerson(Integer balls_per_person){
		this.balls_per_person = balls_per_person;
	}
	
	
	public double getMoney(){
		return money;
	}
	
	public void setMoney(double money){
		this.money = money;
	}
	
	
	public Date getDeadline(){
		return deadline;
	}
	
	public void setDeadline(Date deadline){
		this.deadline = deadline;
	}
	
	
	public boolean getFreeze(){
		return freeze;
	}
	
	public void setFreeze(boolean freeze){
		this.freeze = freeze;
	}
	
	
	public Integer getBallsLeft(){
		return balls_left;
	}
	
	public void setBallsLeft(Integer balls_left){
		this.balls_left = balls_left;
	}

}
