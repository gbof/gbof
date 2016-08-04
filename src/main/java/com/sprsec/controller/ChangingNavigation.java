package com.sprsec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sprsec.service.UserService;

@Controller
public class ChangingNavigation {
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/password-verify", method = RequestMethod.POST)
	public ModelAndView passwordVerify(@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String passwordVerify = service.getUser(userName).getPassword();
		if(oldpassword.equals(passwordVerify)){
			ModelAndView modelAndView = new ModelAndView("change-password");
			modelAndView.addObject("correct", true);
			return modelAndView;
		}
		else{
			ModelAndView modelAndView = new ModelAndView("change-password");
			modelAndView.addObject("error", true);
			return modelAndView;
			//
		}
	}

}
