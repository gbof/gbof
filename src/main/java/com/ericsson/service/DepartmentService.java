package com.ericsson.service;

import java.util.List;

import com.ericsson.model.Department;

public interface DepartmentService {
	
	public List<Department> getAllDepts();

	public Department getDeptID(String deptName);

	public void addDept(String deptName, Integer leaderID);

	public String getDeptName(Integer deptId);
	
	public Department getDept(Integer deptId);

	public void editDepartment(Integer deptID, String name, Integer leaderID);

	public void removeDept(Integer dept_id);

}
