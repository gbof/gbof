package com.sprsec.dao;

import java.util.List;

import com.sprsec.model.Team;

public interface TeamDAO {

	public List<Team> getAllTeams();

	public Team getTeamID(String teamName);
}
