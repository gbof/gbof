package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.ericsson.model.Team;
import com.ericsson.model.User;
import com.ericsson.service.TeamService;

@Repository
public class UserDAOImpl implements UserDAO {

	 
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private TeamService teamService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	public User getUser(String login) {
		List<User> userList = new ArrayList<User>();
		logger.info("Login: " + login);
		Query query = openSession().createQuery("from User u where u.login = :login");
		query.setParameter("login", login);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;	
	}
	
	public User getUserId(Integer id) {
		List<User> userList = new ArrayList<User>();
		Query query = openSession().createQuery("from User u where u.id = :id");
		query.setParameter("id", id);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;	
	}
	

	public List<User> getAllUsers() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer dept_id = getUser(userName).getDept().getDeptId();
		
		List<User> usersList = new ArrayList<User>();
		
		String sql = "from User where dept_id = '" + dept_id + "' order by surname";
		Query query = openSession().createQuery(sql);
		
		usersList = query.list();
		if (usersList.size() > 0)
			return usersList;
		else
			return null;	
	}
	
	
	public void setPassword(Integer id, String password){
		String query = "UPDATE users SET password = '"+ password +"' WHERE user_id = '"+ id + "'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public void setBallsAfterComment(Integer id, Integer balls, Integer commentToUserId){
		Integer ballsToGive = getUserId(id).getBall().getBallsToGive()-balls;
		Integer reveivedBalls = getUserId(commentToUserId).getBall().getReceivedBalls()+balls;
		String query = "UPDATE balls b, users u set b.balls_to_give = '" + ballsToGive + "' where u.user_id = '" + id + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		String query1 = "UPDATE balls b, users u set b.received_balls = '" + reveivedBalls + "' where u.user_id = '" + commentToUserId + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery1 = openSession().createSQLQuery(query1);
		sqlQuery.executeUpdate();
		sqlQuery1.executeUpdate();
	}
	
	public void setBallsAfterCommentDelete(Integer id, Integer balls, Integer commentToUserId){
		Integer ballsToGive = getUserId(id).getBall().getBallsToGive()+balls;
		Integer reveivedBalls = getUserId(commentToUserId).getBall().getReceivedBalls()-balls;
		String query = "UPDATE balls b, users u set b.balls_to_give = '" + ballsToGive + "' where u.user_id = '" + id + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		String query1 = "UPDATE balls b, users u set b.received_balls = '" + reveivedBalls + "' where u.user_id = '" + commentToUserId + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery1 = openSession().createSQLQuery(query1);
		sqlQuery.executeUpdate();
		sqlQuery1.executeUpdate();
	}
	
	
	public void setBallsAfterCommentEdit(Integer id, Integer oldBalls, Integer balls, Integer commentToUserId){
		Integer ballsToGive;
		Integer reveivedBalls;
		if(oldBalls>balls){
		ballsToGive = getUserId(id).getBall().getBallsToGive()+(oldBalls-balls);
		reveivedBalls = getUserId(commentToUserId).getBall().getReceivedBalls()-(oldBalls-balls);
		}
		else {
			ballsToGive = getUserId(id).getBall().getBallsToGive()-(balls-oldBalls);
			reveivedBalls = getUserId(commentToUserId).getBall().getReceivedBalls()+(balls-oldBalls);
		}
		String query = "UPDATE balls b, users u set b.balls_to_give = '" + ballsToGive + "' where u.user_id = '" + id + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		String query1 = "UPDATE balls b, users u set b.received_balls = '" + reveivedBalls + "' where u.user_id = '" + commentToUserId + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery1 = openSession().createSQLQuery(query1);
		sqlQuery.executeUpdate();
		sqlQuery1.executeUpdate();
	}
	

	@Override
	public void addUser(String name, String surname, String login, Integer roleID, Integer teamID, Integer ballsID, String mail, Integer deptID){
			
			Random r = new Random();
			String RandomPassword="";
			for(int i=0;i<10;i++)
			{
			
			int a = r.nextInt(25) + 97;
			
			System.out.println((char)a);
			RandomPassword+=(char)a;
			}
			
			System.out.println(RandomPassword);
			String FullMail=mail+"@ericsson.com";
			
			RandomPassword="user";
			String query = "INSERT INTO users (name, surname, login, password, role_id, team_id, balls_id, mail, dept_id) VALUES ('" + name +"', '"+ surname +"', '"+ login +"', '"+ RandomPassword +"', '"+ roleID +"', '"+teamID+"', '"+ballsID+"', '"+FullMail+"', '"+deptID+"')";
			SQLQuery sqlQuery = openSession().createSQLQuery(query);
			sqlQuery.executeUpdate();
		}
	
	public void addUser2(User user){
		openSession().persist(user);
	}

	@Override
	public void removeUser(Integer user_id) {
		List <Team> teamList = teamService.getAllTeams();
		for(int i=0;i<teamList.size();i++){
			if(teamList.get(i).getLeaderId().equals(user_id)){
				teamService.editTeamLeader(teamList.get(i).getId());
			}
		}
		String query = "delete from users where user_id="+user_id;
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}

	@Override
	public void editUser(Integer user_id, String name, String surname, String login, String mail, Integer roleID,
			Integer deptID, Integer teamID) {
		String query = "UPDATE users SET name='"+name+"', surname='"+surname+"', login='"+login+"', mail='"+mail+"', role_id='"+roleID+"', dept_id='"+deptID+"', team_id='"+teamID+"'  WHERE user_id ='"+user_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}
	
	public Boolean checkLogin(String login)
	{
		List<User> loginList = new ArrayList<User>();
		
		Query query = openSession().createQuery("from User u where u.login = :login");
		query.setParameter("login", login);
		loginList = query.list();
		
		if (loginList.size() == 0)
		{
			System.out.println("Login wolny");
			return true;
		}
		else
		{
			System.out.println("Login zajety");
			return false;
		}
		
	}

	@Override
	public void editRoleID(Integer leader_id, int role_id) {
		String query = "UPDATE users SET role_id='"+role_id+"' where user_id='"+leader_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}

	
	
}