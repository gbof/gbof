package com.ericsson.service;

import java.util.List;

import com.ericsson.model.Department;

public interface DepartmentService {
	
	public List<Department> getAllDepts();

	public Department getDeptID(String deptName);

}
