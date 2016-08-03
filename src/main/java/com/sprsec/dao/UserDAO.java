package com.sprsec.dao;

import com.sprsec.model.User;
import java.util.List;
public interface UserDAO {
	
	public User getUser(String login);
	public List<User> getAllUsers();

}