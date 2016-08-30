package com.ericsson.dao;

import java.util.List;

import com.ericsson.model.Team;

public interface TeamDAO {

	public List<Team> getAllTeams();

	public Team getTeamID(String teamName);

	public void addTeam(String teamName, Integer leaderID, Integer deptID);

	public void removeTeam(Integer team_id);

	public void editTeam(Integer team_id, Integer leader_id, String name, Integer dept_id);

	public Team getTeam(Integer team_id);
}
