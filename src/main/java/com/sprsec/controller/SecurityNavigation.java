package com.sprsec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sprsec.model.Comment;
import com.sprsec.model.Role;
import com.sprsec.model.User;
import com.sprsec.service.CommentService;
import com.sprsec.service.UserService;

@Controller
public class SecurityNavigation {
	@Autowired
	private UserService us;

	@Autowired
	private CommentService coms;
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
			return new ModelAndView("redirect:/adminview");
		else if (role.getRole().equals("moderator"))
			return new ModelAndView("redirect:/inside");
		else
			return new ModelAndView("redirect:/inside");
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
	
	@RequestMapping(value="/adminview", method=RequestMethod.GET)
	public ModelAndView adminviewPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String name = us.getUser(userName).getName();
		String login = us.getUser(userName).getLogin();
		User zalogowany=us.getUser(userName);
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		
		List<Comment> commentList = coms.getAllComments();
		
		System.out.println("Kom1:" + commentList.get(0).getUser().getName());
		
		
		
		List<Comment> commentConfirmedList = coms.getConfirmedComments();
		
		System.out.println("Zalogowano: "+name);
		String role = us.getUser(userName).getRole().getRole();
		ModelAndView lista = new ModelAndView();
		System.out.println("Zatwierdzne: " + commentConfirmedList);
		System.out.println("Nie Zatwierdzne: " + commentList);
		lista.addObject("listt", listt);
		lista.addObject("rola", role);
		lista.addObject("kule", kulki);
		lista.addObject("commentList",commentList);
		lista.addObject("commentConfirmedList",commentConfirmedList);
		lista.addObject("login", login);
		lista.setViewName("adminview");
		return lista;
	}
	
	@RequestMapping(value="/change", method=RequestMethod.GET)
	public ModelAndView changePage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String name = us.getUser(userName).getName();
		String login = us.getUser(userName).getLogin();
		User zalogowany=us.getUser(userName);
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		System.out.println("Zalogowano: "+name);
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("listt", listt);
		lista.addObject("kule", kulki);
		lista.addObject("rola", role);
		lista.addObject("login", login);
		lista.setViewName("changePassword");
		return lista;
	}
	
	@RequestMapping(value="/settings", method=RequestMethod.GET)
	public ModelAndView settingsPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String name = us.getUser(userName).getName();
		String login = us.getUser(userName).getLogin();
		User zalogowany=us.getUser(userName);
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		System.out.println("Zalogowano: "+name);
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("listt", listt);
		lista.addObject("kule", kulki);
		lista.addObject("rola", role);
		lista.addObject("login", login);
		lista.setViewName("settings");
		return lista;
	}
}