package com.ericsson.service;

public interface UserRolesService {

	public void add(Integer userId, Integer roleId);

	public void removeUserRole(Integer user_id);

	public void editRole(Integer user_id, Integer roleID);

	//public void editUserRoles(Integer userId, Integer roleId);

}
