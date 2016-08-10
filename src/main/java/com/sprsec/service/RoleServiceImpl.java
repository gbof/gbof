package com.sprsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.RoleDAO;
import com.sprsec.model.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDAO roleDAO;

	public Role getRole(int id) {
		return roleDAO.getRole(id);
	}
	
	@Override
	public List<Role> getAllRoles() {
		return roleDAO.getAllRoles();
	}

	@Override
	public List<Role> getRoleId(String roleName) {
		return roleDAO.getRoleId(roleName);
	}

}
