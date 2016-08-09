package com.sprsec.service;

import java.util.List;


import com.sprsec.model.User;

public interface UserService {
	
	public User getUser(String login);
	public List<User> getAllUsers();
	public void setPassword(Integer id, String password);
	public User getUserId(Integer id);
<<<<<<< Upstream, based on origin/master
	public void setBallsAfterComment(Integer id, Integer balls);
=======
	public void addUser(String name, String surname, String login, String password, Integer roleID, Integer teamID, 
			Integer ballsID, String mail, Integer deptID);
>>>>>>> e7b4862 Add employee 
}
