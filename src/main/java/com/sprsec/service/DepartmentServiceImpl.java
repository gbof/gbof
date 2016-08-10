package com.sprsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.DepartmentDAO;
import com.sprsec.model.Department;

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
