package com.sprsec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sprsec.dao.CommentDAO;
import com.sprsec.model.Comment;
import com.sprsec.model.User;
import com.sprsec.service.CommentService;
import com.sprsec.service.UserService;

@Controller
public class LinkNavigation {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private CommentService cs;
	
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
	
	@RequestMapping(value="comments", method=RequestMethod.GET)
	public ModelAndView commentsPage() {
		return new ModelAndView("comments");
	}
	
	@RequestMapping(value="commentAdded", method=RequestMethod.POST)
	public ModelAndView commentAdded(@RequestParam("message1") String message1,
			@RequestParam("message2") String message2,
			@RequestParam("ballsNumber") Integer ballsNumber) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		cs.addComment(message1, message2, ballsNumber, user_id);
		ModelAndView modelAndView = new ModelAndView("redirect:/inside");
		//modelAndView.addObject("success-comment", true);
		return modelAndView;
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