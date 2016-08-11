package com.ericsson.service;

import java.util.List;

import com.ericsson.model.Role;

public interface RoleService {
	
	public Role getRole(int id);

	public List<Role> getAllRoles();
	
	public List<Role> getRoleId(String roleName);

}
