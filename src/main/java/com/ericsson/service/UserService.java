package com.ericsson.service;

import java.util.List;


import com.ericsson.model.User;

public interface UserService {
	
	public User getUser(String login);
	public List<User> getAllUsers();
	public void setPassword(Integer id, String password);
	public User getUserId(Integer id);

	public void setBallsAfterComment(Integer id, Integer balls, Integer commentToUserId);

	public void addUser(String name, String surname, String login, Integer roleID, Integer teamID, 
			Integer ballsID, String mail, Integer deptID);
	public void removeUser(Integer user_id);
	public void editUser(Integer user_id, String name, String surname, String login, String mail, Integer roleID,
			Integer deptID, Integer teamID);
	public void addUser2(User user);
	public void setBallsAfterCommentEdit(Integer id, Integer oldBalls, Integer balls, Integer commentToUserId);
	public void setBallsAfterCommentDelete(Integer id, Integer balls, Integer commentToUserId);
	public Boolean checkLogin(String login);
	
}
