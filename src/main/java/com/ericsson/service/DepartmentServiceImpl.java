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

	@Override
	public void addDept(String deptName, Integer leaderID) {
		deptDAO.addDept(deptName, leaderID);
		
	}

	@Override
	public String getDeptName(Integer deptId) {
		return deptDAO.getDeptName(deptId);
	}
	@Override
	public Department getDept(Integer deptId)
	{
		return deptDAO.getDept(deptId);
	}

	@Override
	public void editDepartment(Integer deptID, String name, Integer leaderID) {
		deptDAO.editDepartment(deptID, name, leaderID);
		
	}

	@Override
	public void removeDept(Integer dept_id) {
		deptDAO.removeDept(dept_id);
		
	}
	
}
