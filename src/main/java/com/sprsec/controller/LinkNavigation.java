package com.sprsec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sprsec.model.User;
import com.sprsec.service.UserService;

@Controller
public class LinkNavigation {
	
	@Autowired
	private UserService us;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView homePage() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView indexPage() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/sec/moderation", method=RequestMethod.GET)
	public ModelAndView moderatorPage() {
		return new ModelAndView("moderation");
	}
	
	@RequestMapping(value="/admin/first", method=RequestMethod.GET)
	public ModelAndView firstAdminPage() {
		return new ModelAndView("admin-first");
	}
	
	@RequestMapping(value="/admin/second", method=RequestMethod.GET)
	public ModelAndView secondAdminPage() {
		return new ModelAndView("admin-second");
	}
	
	@RequestMapping(value="/change", method=RequestMethod.GET)
	public ModelAndView changePassword() {
		return new ModelAndView("change-password");
	}
	/*
	@RequestMapping(value="/inside", method=RequestMethod.GET)
	public ModelAndView insidePage() {
		return new ModelAndView("inside2");
	}*/
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public ModelAndView testPage() {

		ModelAndView lista = new ModelAndView();
		List<User> list = us.getAllUsers();
		lista.addObject("list", list);
		lista.setViewName("test");
		return lista;
	}
	

}