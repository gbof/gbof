package com.sprsec.service;

import java.util.List;

import com.sprsec.model.Role;

public interface RoleService {
	
	public Role getRole(int id);

	public List<Role> getAllRoles();
	
	public List<Role> getRoleId(String roleName);

}
