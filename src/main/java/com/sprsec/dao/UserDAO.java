package com.sprsec.dao;

import com.sprsec.model.User;
import java.util.List;
public interface UserDAO {
	
	public User getUser(String login);
	public List<User> getAllUsers();
	public void setPassword(Integer id, String password);
	public User getUserId(Integer id);

}