package com.ericsson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ericsson.service.UserService;

@Controller
public class ChangingNavigation {
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/password-verify", method = RequestMethod.POST)
	public ModelAndView passwordVerify(@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword, 
			@RequestParam("newpassword2") String newpassword2) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String passwordVerify = service.getUser(userName).getPassword();
		Integer id = service.getUser(userName).getId();
		if(oldpassword.equals(passwordVerify)){                    
			if(!newpassword.equals(newpassword2)){                    
				ModelAndView modelAndView = new ModelAndView("changePassword");
				modelAndView.addObject("error2", true);
				return modelAndView;
			}
			service.setPassword(id, newpassword);
			ModelAndView modelAndView = new ModelAndView("changePassword");
			modelAndView.addObject("correct", true);
			return modelAndView;
		}
		else{
			ModelAndView modelAndView = new ModelAndView("changePassword");
			modelAndView.addObject("error", true);
			return modelAndView;
			//
		}
	}

}
