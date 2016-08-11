package com.ericsson.dao;

import java.util.List;

import com.ericsson.model.Department;

public interface DepartmentDAO {
	
	public List<Department> getAllDepts();
	
	public Department getDeptId(String deptName);

	public void addDept(String deptName, Integer leaderID);

}
