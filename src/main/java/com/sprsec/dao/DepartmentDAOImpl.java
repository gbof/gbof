package com.sprsec.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprsec.model.Department;
import com.sprsec.model.Role;
import com.sprsec.model.Team;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	public List<Department> getAllDepts() {
		List<Department> departmentsList = new ArrayList<Department>();
		
		String sql = "from Department";
		Query query = getCurrentSession().createQuery(sql);
		
		departmentsList = query.list();
		if (departmentsList.size() > 0)
			return departmentsList;
		else
			return null;
	}

	@Override
	public Department getDeptId(String dept_name) {
		List<Department> deptList = new ArrayList<Department>();
		String sql = "from Department where dept_name=:dept_name";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("dept_name", dept_name);
		deptList = query.list();
		if (deptList.size() > 0)
			return deptList.get(0);
		else
			return null;
	}

}
