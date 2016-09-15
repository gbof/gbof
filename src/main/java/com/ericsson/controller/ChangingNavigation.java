package com.ericsson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ericsson.model.User;
import com.ericsson.service.CommentService;
import com.ericsson.service.SettingService;
import com.ericsson.service.UserService;

@Controller
public class ChangingNavigation {
	@Autowired
	private UserService us;
	
	@Autowired
	private SettingService sett;
	@Autowired
	private CommentService cs;
	
	@RequestMapping(value = "/password-verify", method = RequestMethod.POST)
	public ModelAndView passwordVerify(@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword, 
			@RequestParam("newpassword2") String newpassword2) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String passwordVerify = us.getUser(userName).getPassword();
		Integer id = us.getUser(userName).getId();
		
		String login = us.getUser(userName).getLogin();
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		if(encoder.matches(oldpassword, passwordVerify)){                    
			if(!newpassword.equals(newpassword2)){                    
				ModelAndView modelAndView = new ModelAndView("changePassword");
				modelAndView.addObject("error2", true);
				modelAndView.addObject("money", wynik);
				modelAndView.addObject("kule", kulki);
				modelAndView.addObject("login", login);

				return modelAndView;
			}
			us.setPassword(id, newpassword);
			ModelAndView modelAndView = new ModelAndView("changePassword");
			modelAndView.addObject("correct", true);
			modelAndView.addObject("money", wynik);
			modelAndView.addObject("kule", kulki);
			modelAndView.addObject("login", login);

			return modelAndView;
		}
		else{
			ModelAndView modelAndView = new ModelAndView("changePassword");
			modelAndView.addObject("error", true);
			modelAndView.addObject("money", wynik);
			modelAndView.addObject("kule", kulki);
			modelAndView.addObject("login", login);
			
			return modelAndView;
			//
		}
	}
	@RequestMapping(value="/change", method=RequestMethod.GET)
	public ModelAndView changePage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		
		String login = us.getUser(userName).getLogin();
		
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
	
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("listt", listt);
		lista.addObject("kule", kulki);
		lista.addObject("rola", role);
		lista.addObject("money", wynik);
		lista.addObject("login", login);
		lista.setViewName("changePassword");
		return lista;
	}

}
