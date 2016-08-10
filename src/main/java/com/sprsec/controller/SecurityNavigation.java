package com.sprsec.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sprsec.model.Comment;
import com.sprsec.model.Department;
import com.sprsec.model.Role;

import com.sprsec.model.Settings;
import com.sprsec.model.Team;


import com.sprsec.model.User;
import com.sprsec.service.CommentService;
import com.sprsec.service.DepartmentService;
import com.sprsec.service.RoleService;
import com.sprsec.service.SettingService;
import com.sprsec.service.TeamService;
import com.sprsec.service.UserService;

@Controller
public class SecurityNavigation {
	@Autowired
	private UserService us;

	
	@Autowired
	private CommentService coms;
	

	@Autowired 
	private SettingService sett;
	
	@Autowired
	private RoleService rs;
	
	@Autowired
	private TeamService ts;
	
	@Autowired
	private DepartmentService ds;
	

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

		Integer id = us.getUser(userName).getId();
		User zalogowany=us.getUser(userName);

		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);

		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) Math.round(moneyValue/ballValue2);
		List<Integer> allBallsGivenTo = new ArrayList<Integer>();
		for(int i=0;i<listt.size();i++){
			allBallsGivenTo.addAll(coms.getAllBallsGivenTo(id, listt.get(i).getId()));
			if(allBallsGivenTo.get(i)==null)allBallsGivenTo.set(i,0);
		}
		
		System.out.println("Zalogowano: "+name);
		ModelAndView lista = new ModelAndView();
		lista.addObject("allBallsGivenTo", allBallsGivenTo);
		lista.addObject("money", wynik);
		lista.addObject("kule", kulki);
		lista.addObject("login", login);
		lista.addObject("listt", listt);
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
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Double> ballValue = coms.getBallValue(moneyValue);
		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);

		List<Comment> commentList = coms.getAllComments();
		
		System.out.println("Kom1:" + commentList.get(0).getUser().getName());
		
		
		
		List<Comment> commentConfirmedList = coms.getConfirmedComments();
		
		System.out.println("Zalogowano: "+name);
		String role = us.getUser(userName).getRole().getRole();
		ModelAndView lista = new ModelAndView();
		System.out.println("Zatwierdzne: " + commentConfirmedList);
		System.out.println("Nie Zatwierdzne: " + commentList);
		lista.addObject("listt", listt);
		lista.addObject("money", wynik);
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
		
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		
		System.out.println("Wchodze do ustawien");
		ModelAndView lista = new ModelAndView();
		lista.addObject("listt", listt);
		lista.addObject("kule", kulki);
		lista.addObject("rola", role);
		lista.addObject("login", login);
		lista.addObject("rolelistt", rolelistt);
		lista.addObject("teamlistt", teamlistt);
		lista.addObject("deptlistt", deptlistt);
		lista.setViewName("settings");
		return lista;
	}
	@RequestMapping(value="/settingsAdd", method=RequestMethod.POST)
	public ModelAndView settingsAdd(
			@RequestParam("ballsPerPers") Integer ballsPerPers,
			@RequestParam("money") Double money,
			@RequestParam("deadline") String deadline,
			@RequestParam("extraBalls") Integer extraBalls)
			 {
		ModelAndView modelAndView = new ModelAndView("settings");
		
		
		System.out.println("data: "+deadline);
	    Boolean freeze=false;
		sett.addSetting(extraBalls,ballsPerPers,money,deadline,freeze,2);
		System.out.println("Zapisuje ustawinia");
		modelAndView.addObject("correct", true);
		return modelAndView;
	}
	
	
}