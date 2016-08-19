package com.ericsson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.dao.UserRolesDAO;

@Service
@Transactional
public class UserRolesServiceImpl implements UserRolesService {

	
	@Autowired
	private UserRolesDAO roleusersDAO;
	
	@Override
	public void add(Integer userId, Integer roleId) {
		
		roleusersDAO.add(userId, roleId);
	}

	/*@Override
	public void editUserRoles(Integer userId, Integer roleId) {
		roleusersDAO.editUserRoles(userId, roleId);
		
	}
*/

}
