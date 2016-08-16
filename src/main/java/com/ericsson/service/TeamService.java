package com.ericsson.service;

import java.util.List;

import com.ericsson.model.Team;

public interface TeamService {
	
	public List<Team> getAllTeams();

	public Team getTeamID(String teamName);

	public void addTeam(String teamName, Integer leaderID, Integer deptID);

	public void removeTeam(Integer team_id);
	

}
