package com.ericsson.dao;

import java.util.List;

import com.ericsson.model.Role;

public interface RoleDAO {
	
	public Role getRole(int id);
	
	public List<Role> getAllRoles();
	
	public List<Role> getRoleId(String roleName);

}