package com.ericsson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.dao.DepartmentDAO;
import com.ericsson.model.Department;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDAO deptDAO;
	
	@Override
	public List<Department> getAllDepts() {
		return deptDAO.getAllDepts();
	}

	@Override
	public Department getDeptID(String deptName) {
		return deptDAO.getDeptId(deptName);
	}

}
