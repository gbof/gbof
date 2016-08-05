package com.sprsec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sprsec.model.Role;
import com.sprsec.model.User;
import com.sprsec.service.UserService;

@Controller
public class SecurityNavigation {
	@Autowired
	private UserService us;

	@RequestMapping(value = "/user-login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/error-login", method = RequestMethod.GET)
	public ModelAndView invalidLogin() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("error", true);
		return modelAndView;
	}

	@RequestMapping(value = "/success-login", method = RequestMethod.GET)
	public ModelAndView successLogin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Role role = us.getUser(userName).getRole();
		if (role.getRole().equals("admin"))
			return new ModelAndView("admin-first");
		else if (role.getRole().equals("moderator"))
			return new ModelAndView("moderation");
		else
			return new ModelAndView("user");
		//return new ModelAndView("redirect:/inside");}
	}
	
	@RequestMapping(value="/inside", method=RequestMethod.GET)
	public ModelAndView insidePage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String name = us.getUser(userName).getName();
		String login = us.getUser(userName).getLogin();
		User zalogowany=us.getUser(userName);
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		System.out.println("Zalogowano: "+name);
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("listt", listt);
		lista.addObject("kule", kulki);
		lista.addObject("login", login);
		lista.setViewName("inside");
		return lista;
	}
}