package com.sprsec.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprsec.dao.UserDAO;

import com.sprsec.model.User;

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
	public void addUser(String name, String surname, String login, String password, Integer roleID, Integer teamID,
			Integer ballsID, String mail, Integer deptID) {
		userDAO.addUser(name, surname, login, password, roleID, teamID, ballsID, mail, deptID);
		

	}
}
