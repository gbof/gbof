package com.sprsec.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sprsec.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Role getRole(int id) {
		Role role = (Role) getCurrentSession().load(Role.class, id);
		return role;
	}
	

	@Override
	public List<Role> getAllRoles() {
		List<Role> rolesList = new ArrayList<Role>();
		
		String sql = "from Role";
		Query query = getCurrentSession().createQuery(sql);
		
		rolesList = query.list();
		if (rolesList.size() > 0)
			return rolesList;
		else
			return null;	
	}
	
	public List<Role> getRoleId(String role) {
		List<Role> roleList = new ArrayList<Role>();
		String sql = "from Role where role=:role";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("role", role);
		roleList = query.list();
		if (roleList.size() > 0 )
			return roleList;
		else
			return null;	
		
	}


}