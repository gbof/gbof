package com.sprsec.service;

import java.util.List;


import com.sprsec.model.User;

public interface UserService {
	
	public User getUser(String login);
	public List<User> getAllUsers();
	public void setPassword(Integer id, String password);
	public User getUserId(Integer id);
	public void setBallsAfterComment(Integer id, Integer balls);
}
