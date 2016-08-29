package com.ericsson.dao;

public interface UserRolesDAO {

	void add(Integer userId, Integer roleId);

	void removeUserRole(Integer user_id);

	void editRole(Integer user_id, Integer roleID);

	//void editUserRoles(Integer userId, Integer roleId);

}
