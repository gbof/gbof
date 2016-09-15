package com.ericsson.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="settings")
public class Settings {
	
	@Id
	@GeneratedValue
	@Column(name="settings_id")
	private Integer settings_id;
	
	@Column(name="balls_per_person")
	private Integer balls_per_person;
	@Column(name="money")
	private Double money;
	@Column(name="deadline")
	private Date deadline;
	@Column(name="freeze")
	private Integer freeze;
	@Column(name="balls_left")
	private Integer balls_left;
	@Column(name="helpMsg")
	private String helpMsg;
	
	public Integer getSettingsID(){
		return settings_id;
	}
	
	public void setSettingsID(Integer settings_id){
		this.settings_id = settings_id;
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
	
	public void setMoney(Double money){
		this.money = money;
	}
	
	
	public Date getDeadline(){
		return deadline;
	}
	
	public void setDeadline(Date deadline){
		this.deadline = deadline;
	}
	
	
	public Integer getFreeze(){
		return freeze;
	}
	
	public void setFreeze(Integer freeze){
		this.freeze = freeze;
	}
	
	
	public Integer getBallsLeft(){
		return balls_left;
	}
	
	public void setBallsLeft(Integer balls_left){
		this.balls_left = balls_left;
	}
	public String getHelpMsg()
	{
		return helpMsg;
	}
	public void setHelpMsg(String helpMsg)
	{
		this.helpMsg=helpMsg;
	}
}
