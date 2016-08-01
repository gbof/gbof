package com.sprsec.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.sprsec.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	public Customer getCustomer(String name) {
		List<Customer> customerList = new ArrayList<Customer>();
		logger.info("name: " + name);
		Query query = openSession().createQuery("from User u where u.name = :name");
		query.setParameter("name", name);
		customerList = query.list();
		if (customerList.size() > 0)
			return customerList.get(0);
		else
			return null;	
	}

}

