package com.sprsec.dao;

import java.util.List;

import com.sprsec.model.Role;

public interface RoleDAO {
	
	public Role getRole(int id);
	
	public List<Role> getAllRoles();
	
	public List<Role> getRoleId(String roleName);

}