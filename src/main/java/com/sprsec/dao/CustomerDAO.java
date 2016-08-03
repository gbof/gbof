package com.sprsec.dao;

import java.util.ArrayList;
import java.util.List;
import com.sprsec.model.Customer;

public interface CustomerDAO {
	
	public Customer getCustomer(String login);
	public List<Customer> getAllCustomer();
	
}
