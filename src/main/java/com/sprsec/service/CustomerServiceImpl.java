

package com.sprsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.CustomerDAO;
import com.sprsec.model.Customer;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	

	@Autowired
	private CustomerDAO customerDAO;

	public List<Customer> getAllCustomer() {
		return customerDAO.getAllCustomer();
	}
}