package com.ericsson.dao;

public interface UserRolesDAO {

	void add(Integer userId, Integer roleId);

	void removeUserRole(Integer user_id);

	//void editUserRoles(Integer userId, Integer roleId);

}
