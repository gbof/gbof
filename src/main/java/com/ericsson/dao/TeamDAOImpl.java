package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.ericsson.model.Team;
import com.ericsson.service.UserService;

@Repository
public class TeamDAOImpl implements TeamDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService us;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<Team> getAllTeams() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer dept_id = us.getUser(userName).getDept().getDeptId();
		List<Team> teamsList = new ArrayList<Team>();
		
		String sql = "from Team where dept_id='"+dept_id+"'";
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

	@Override
	public void editTeam(Integer team_id, Integer user_id, String name, Integer dept_id) {
		String query = "UPDATE teams SET team_name='"+name+"', leader_id='"+user_id+"', dept_id='"+dept_id+"' where team_id='"+team_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}

	@Override
	public Team getTeam(Integer team_id) {
		List<Team> teamList = new ArrayList<Team>();
		String sql = "from Team where team_id=:team_id";
		Query query = openSession().createQuery(sql);
		query.setParameter("team_id", team_id);
		teamList = query.list();
		if (teamList.size() > 0)
			return teamList.get(0);
		else
			return null;
	}
	
	@Override
	public void editTeamLeader(Integer team_id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		String query = "UPDATE teams SET leader_id='"+user_id+"' where team_id='"+team_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}

	@Override
	public List<Team> getTeamsID(String team_name) {
		List<Team> teamList = new ArrayList<Team>();
		String sql = "from Team where team_name=:team_name";
		Query query = openSession().createQuery(sql);
		query.setParameter("team_name", team_name);
		teamList = query.list();
		if (teamList.size() > 0)
			return teamList;
		else
			return null;
	}
}
