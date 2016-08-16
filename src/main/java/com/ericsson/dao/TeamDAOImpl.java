package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ericsson.model.Role;
import com.ericsson.model.Team;

@Repository
public class TeamDAOImpl implements TeamDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<Team> getAllTeams() {
		List<Team> teamsList = new ArrayList<Team>();
		
		String sql = "from Team";
		Query query = openSession().createQuery(sql);
		
		teamsList = query.list();
		if (teamsList.size() > 0)
			return teamsList;
		else
			return null;
	}

	@Override
	public Team getTeamID(String team_name) {
		List<Team> teamList = new ArrayList<Team>();
		String sql = "from Team where team_name=:team_name";
		Query query = openSession().createQuery(sql);
		query.setParameter("team_name", team_name);
		teamList = query.list();
		if (teamList.size() > 0)
			return teamList.get(0);
		else
			return null;	
	}

	@Override
	public void addTeam(String teamName, Integer leaderID, Integer deptID) {
		String query = "INSERT INTO teams (team_name, leader_id, dept_id) VALUES ('" + teamName +"', '"+ leaderID +"', '"+ deptID +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}

	@Override
	public void removeTeam(Integer team_id) {
		String query = "delete from teams where team_id="+team_id;
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}

}
