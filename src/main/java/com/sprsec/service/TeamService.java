package com.sprsec.service;

import java.util.List;

import com.sprsec.model.Team;

public interface TeamService {
	
	public List<Team> getAllTeams();

	public Team getTeamID(String teamName);
	

}
