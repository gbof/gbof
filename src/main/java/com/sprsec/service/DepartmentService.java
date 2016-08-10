package com.sprsec.service;

import java.util.List;

import com.sprsec.model.Department;

public interface DepartmentService {
	
	public List<Department> getAllDepts();

	public Department getDeptID(String deptName);

}
