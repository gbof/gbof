package com.ericsson.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.dao.UserDAO;

import com.ericsson.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	public User getUser(String login) {
		return userDAO.getUser(login);
	}
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
	public void setPassword(Integer id, String password){
		userDAO.setPassword(id, password);
	}
	public User getUserId(Integer id){
		return userDAO.getUserId(id);
	}

	public void setBallsAfterComment(Integer id, Integer balls, Integer commentToUserId){
		userDAO.setBallsAfterComment(id, balls, commentToUserId);}

	
	@Override
	public void addUser(String name, String surname, String login, Integer roleID, Integer teamID,
			Integer ballsID, String mail, Integer deptID) {
		userDAO.addUser(name, surname, login, roleID, teamID, ballsID, mail, deptID);
		

	}
	@Override
	public void removeUser(Integer user_id) {
		userDAO.removeUser(user_id);
		
	}
	@Override
	public void editUser(Integer user_id, String name, String surname, String login, String mail, Integer roleID,
			Integer deptID, Integer teamID) {
		userDAO.editUser(user_id, name, surname, login, mail, roleID, deptID, teamID);
		
	}
	@Override
	public void addUser2(User user) {
		userDAO.addUser2(user);
		
	}
	@Override
	public void setBallsAfterCommentEdit(Integer id, Integer oldBalls, Integer balls, Integer commentToUserId){
		userDAO.setBallsAfterCommentEdit(id, oldBalls, balls, commentToUserId);
	}

	
	@Override
	public void setBallsAfterCommentDelete(Integer id, Integer balls, Integer commentToUserId){
		userDAO.setBallsAfterCommentDelete(id, balls, commentToUserId);
	}
	public Boolean checkLogin(String login){
		return userDAO.checkLogin(login);
	}
	@Override
	public void editRoleID(Integer leader_id, int role_id) {
		userDAO.editRoleID(leader_id, role_id);
		
	}
	@Override
	public List<User> getAllUsersForSuperUser() {
		return userDAO.getAllUsersForSuperUser();
	}
	@Override
	public void editDept(Integer user_id, Integer dept_id) {
		userDAO.editDept(user_id, dept_id);
		
	}


}
