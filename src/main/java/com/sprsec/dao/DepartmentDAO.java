package com.sprsec.dao;

import java.util.List;

import com.sprsec.model.Department;

public interface DepartmentDAO {
	
	public List<Department> getAllDepts();
	
	public Department getDeptId(String deptName);

}
