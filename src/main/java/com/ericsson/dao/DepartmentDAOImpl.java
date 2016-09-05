package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ericsson.model.Department;
import com.ericsson.model.Role;
import com.ericsson.model.Team;

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
	@Override
	public Department getDept(Integer dept_id) {
		List<Department> deptList = new ArrayList<Department>();
		String sql = "from Department where dept_id=:dept_id";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("dept_id", dept_id);
		deptList = query.list();
		if (deptList.size() > 0)
			return deptList.get(0);
		else
			return null;
	}

	@Override
	public void addDept(String deptName, Integer leaderID) {
		String query = "INSERT INTO departments (dept_name, dept_leader_id) VALUES ('" + deptName +"', '"+ leaderID +"')";
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}


	@Override
	public String getDeptName(Integer dept_id) {
		List<Department> deptList = new ArrayList<Department>();
		String sql = "from Department where dept_id=:dept_id";
		Query query = getCurrentSession().createQuery(sql);
		query.setParameter("dept_id", dept_id);
		deptList = query.list();
		if (deptList.size() > 0)
			return deptList.get(0).getDeptName();
		else
			return null;
		
	}

}
