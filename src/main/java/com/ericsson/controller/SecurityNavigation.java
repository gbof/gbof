package com.ericsson.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ericsson.init.MailMail;
import com.ericsson.model.Comment;
import com.ericsson.model.Department;
import com.ericsson.model.Role;

import com.ericsson.model.Settings;
import com.ericsson.model.Team;


import com.ericsson.model.User;
import com.ericsson.service.CommentService;
import com.ericsson.service.DepartmentService;
import com.ericsson.service.RoleService;
import com.ericsson.service.SettingService;
import com.ericsson.service.TeamService;
import com.ericsson.service.UserService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

		   Date date = new Date();
		   Date date1 = sett.getSettingsDate().get(0);
		   if(date1.before(date)){
			   sett.setToFrozen();
			   
		   }
		   
		   List<Settings> settingsList=sett.getSettings();
			
			if(settingsList.get(0).getFreeze()==1)
			{
				System.out.println("login confirm");
				coms.setConfirmAll();
			}
		   
		if (role.getRole().equals("admin"))
			return new ModelAndView("redirect:/adminview");
		else if(sett.getSettingsFreeze().get(0)==1)
				return new ModelAndView("redirect:/freeze");
			else if (role.getRole().equals("moderator"))
				return new ModelAndView("redirect:/inside");
			else if (role.getRole().equals("user"))
				return new ModelAndView("redirect:/inside");
			else
				return new ModelAndView("redirect:/superUser");
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
		List <Team> teamlistt = ts.getAllTeams();
		List<Comment> yourComments=coms.getYourComments(id);
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		
		List<Integer> allBallsGivenTo = new ArrayList<Integer>();
		for(int i=0;i<listt.size();i++){
			allBallsGivenTo.addAll(coms.getAllBallsGivenTo(id, listt.get(i).getId()));
			if(allBallsGivenTo.get(i)==null)allBallsGivenTo.set(i,0);
		}
		
		List<Settings> settingsList=sett.getSettings();
		
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("allBallsGivenTo", allBallsGivenTo);
		lista.addObject("money", wynik);
		lista.addObject("yourComments", yourComments);
		lista.addObject("kule", kulki);
		lista.addObject("login", login);
		lista.addObject("listt", listt);
		lista.addObject("teamlistt", teamlistt);
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
		Integer id = us.getUser(userName).getId();
		List<User> listt = us.getAllUsers();
		List<Team> teamlistt = ts.getAllTeams();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		
		
		
		List<Comment> yourComments=coms.getYourComments(id);

		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		
		List<Comment> commentList = coms.getAllComments();
	
		List<Comment> commentConfirmedList = coms.getConfirmedComments();
		String role = us.getUser(userName).getRole().getRole();
		ModelAndView lista = new ModelAndView();
		
		List<Integer> allBallsGivenTo = new ArrayList<Integer>();
		for(int i=0;i<listt.size();i++){
			allBallsGivenTo.addAll(coms.getAllBallsGivenTo(id, listt.get(i).getId()));
			if(allBallsGivenTo.get(i)==null)allBallsGivenTo.set(i,0);
		}
	        
		List<Settings> settingsList=sett.getSettings();
		
		
		
		lista.addObject("listt", listt);
		lista.addObject("money", wynik);
		lista.addObject("yourComments", yourComments);
		lista.addObject("rola", role);
		lista.addObject("kule", kulki);
		lista.addObject("commentList",commentList);
		lista.addObject("commentConfirmedList",commentConfirmedList);
		lista.addObject("allBallsGivenTo", allBallsGivenTo);
		lista.addObject("login", login);
		lista.addObject("teamlistt", teamlistt);
		lista.setViewName("adminview");
		return lista;
	}
	
	@RequestMapping(value="/superUser", method=RequestMethod.GET)
	public ModelAndView superuserPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login = us.getUser(userName).getLogin();
		
		List<User> listt = us.getAllUsers();
		List<String> userBasiclistt = new ArrayList<String>();
		List<Department> deptlistt = ds.getAllDepts();
		
		System.out.println("====="+us.getUser(userName).getDept().getDeptName());
		
		for (int i=0; i<deptlistt.size(); i++){
			if (deptlistt.get(i).getDeptName().equals(us.getUser(userName).getDept().getDeptName()))
				deptlistt.remove(i);
		}
		List<String> deptLeaders = new ArrayList<String>();
		List<Team> teamlistt = ts.getAllTeams();
		String leader;
		
		for(Department d : deptlistt){
			leader = us.getUserId(d.getDeptLeaderId()).getName() + " " + us.getUserId(d.getDeptLeaderId()).getSurname();
			deptLeaders.add(leader);
		}
		
		String _name;
		String _surname;
		String _login;
		for (int i=0; i<listt.size(); i++){
			_name = listt.get(i).getName();
			_surname = listt.get(i).getSurname();
			_login = listt.get(i).getLogin();
			userBasiclistt.add(_name+" "+_surname+" "+_login);
		}
		

		String rola = "superuser";
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("login", login);
		lista.addObject("rola", rola);
		lista.addObject("userBasiclistt", userBasiclistt);
		lista.addObject("deptlistt", deptlistt);
		lista.addObject("deptLeaders", deptLeaders);
		lista.addObject("teamlistt", teamlistt);
		lista.setViewName("superUser");
		return lista;
	}
	
	@RequestMapping(value = "/freeze", method = RequestMethod.GET)
	public ModelAndView freezeLogin() {
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login = us.getUser(userName).getLogin();
		
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		ModelAndView modelAndView = new ModelAndView("freeze");
		
		
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("login", login);
		return modelAndView;
	}
	
	@RequestMapping(value="/change", method=RequestMethod.GET)
	public ModelAndView changePage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String name = us.getUser(userName).getName();
		String login = us.getUser(userName).getLogin();
		
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
	
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
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
	
	@RequestMapping(value="/settings", method=RequestMethod.GET)
	public ModelAndView settingsPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String name = us.getUser(userName).getName();
		String login = us.getUser(userName).getLogin();
		
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<User> listt = us.getAllUsers();
		List<String> userBasiclistt = new ArrayList<String>();
		
		String _name;
		String _surname;
		String _login;
		for (int i=0; i<listt.size(); i++){
			_name = listt.get(i).getName();
			_surname = listt.get(i).getSurname();
			_login = listt.get(i).getLogin();
			userBasiclistt.add(_name+" "+_surname+" "+_login);
		}
	
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();

		List<Settings> settingsList=sett.getSettings();
		
		
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();

		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		

		 Date date = new Date();
		   Date date1 = sett.getSettingsDate().get(0);
		   if(date1.before(date)){
			   sett.setToFrozen();
			   
		   }
		   
		for (int i=0; i<rolelistt.size(); i++){
			if (rolelistt.get(i).getRole().equals("superuser") || rolelistt.get(i).getRole().equals("admin"))
				rolelistt.remove(i);
		}
		ModelAndView lista = new ModelAndView();
		
		if(sett.getSettingsFreeze().get(0)==1)
			lista.addObject("checked", true);
		else
			lista.addObject("checked", false);
		lista.addObject("settingsList",settingsList);
		lista.addObject("listt", listt);
		lista.addObject("money", wynik);
		lista.addObject("kule", kulki);
		lista.addObject("rola", role);
		lista.addObject("login", login);
		lista.addObject("rolelistt", rolelistt);
		lista.addObject("teamlistt", teamlistt);
		lista.addObject("deptlistt", deptlistt);
		lista.addObject("userBasiclistt", userBasiclistt);
		lista.setViewName("settings");
		return lista;
	}
	@RequestMapping(value="/settingsAdd", params="save", method=RequestMethod.POST)
	public ModelAndView settingsAdd(
			@RequestParam("ballsPerPers") Integer ballsPerPers,
			@RequestParam("money") Double money,
			@RequestParam("deadline") String deadline,
			
			@RequestParam(value = "checkbox", required = false, defaultValue = "0") Integer[] isFreeze,
			@RequestParam("helpMsg") String helpMsg)
			 {
		System.out.println("settingsadd");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		String login = us.getUser(userName).getLogin();
		
		ModelAndView modelAndView = new ModelAndView("settings");
		
		Boolean freeze;
		if(isFreeze[0].intValue()==0)
		freeze=false;
		else
			freeze=true;
		
		if (money==null || ballsPerPers==null || deadline=="")
		{
			;
			modelAndView.addObject("error", true);
		}
		else
		{
		modelAndView.addObject("correct", true);
		sett.addSetting(ballsPerPers,money,deadline,freeze,1,helpMsg);
		
		
		}
		
		List<String> userBasiclistt = new ArrayList<String>();
		List<User> listt = us.getAllUsers();

		String _name;
		String _surname;
		String _login;
		for (int i=0; i<listt.size(); i++){
			_name = listt.get(i).getName();
			_surname = listt.get(i).getSurname();
			_login = listt.get(i).getLogin();
			userBasiclistt.add(_name+" "+_surname+" "+_login);
		}
		List<Settings> settingsList=sett.getSettings();
		System.out.println(settingsList.get(0).getHelpMsg());
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		 Date date = new Date();
		   Date date1 = sett.getSettingsDate().get(0);
		   if(date1.before(date)){
			   sett.setToFrozen();
			   
		   }
		if(sett.getSettingsFreeze().get(0)==1)
			modelAndView.addObject("checked", true);
		else
			modelAndView.addObject("checked", false);
		List<Double> Lmoney = sett.getMoney(1);
		Double moneyValue = Lmoney.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		modelAndView.addObject("settingsList",settingsList);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("login", login);
		modelAndView.addObject("rolelistt", rolelistt);
		modelAndView.addObject("teamlistt", teamlistt);
		modelAndView.addObject("deptlistt", deptlistt);
		modelAndView.addObject("userBasiclistt", userBasiclistt);
		return modelAndView;
	}

	
	@RequestMapping(value="/settingsAdd", params="extramoney1", method=RequestMethod.POST)
	public ModelAndView settingsAddExtraMonney(
			
			@RequestParam("money") Double money,
			@RequestParam("extramoney") Double extramoney
			)
			 {
		
		System.out.println("extramoneyadd");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		String login = us.getUser(userName).getLogin();
		List<Double> Lmoney = sett.getMoney(1);
		Double moneyValue = Lmoney.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		ModelAndView modelAndView = new ModelAndView("settings");
		wynik = sett.round(wynik, 2);
		Boolean freeze;
		
		freeze=false;
		
		Double withExtraMoney=money+extramoney;
		sett.addExtraMoney(1, withExtraMoney);
		
		List<String> userBasiclistt = new ArrayList<String>();
		List<User> listt = us.getAllUsers();

		String _name;
		String _surname;
		String _login;
		for (int i=0; i<listt.size(); i++){
			_name = listt.get(i).getName();
			_surname = listt.get(i).getSurname();
			_login = listt.get(i).getLogin();
			userBasiclistt.add(_name+" "+_surname+" "+_login);
		}
		List<Settings> settingsList=sett.getSettings();
		
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		 Date date = new Date();
		   Date date1 = sett.getSettingsDate().get(0);
		   if(date1.before(date)){
			   sett.setToFrozen();
			   
		   }
		if(sett.getSettingsFreeze().get(0)==1)
			modelAndView.addObject("checked", true);
		else
			modelAndView.addObject("checked", false);
		modelAndView.addObject("settingsList",settingsList);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("login", login);
		modelAndView.addObject("rolelistt", rolelistt);
		modelAndView.addObject("teamlistt", teamlistt);
		modelAndView.addObject("deptlistt", deptlistt);
		modelAndView.addObject("userBasiclistt", userBasiclistt);
		return modelAndView;
	}
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView usersPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<User> listt = us.getAllUsers();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();

		Integer kulki = us.getUser(userName).getBall().getBallsToGive();
		ModelAndView lista = new ModelAndView();
		lista.addObject("money", wynik);
		lista.addObject("kule", kulki);
		lista.addObject("login", login);

		lista.addObject("listt", listt);
		lista.setViewName("users");
		return lista;
	}
	

	@RequestMapping(value="/403", method=RequestMethod.GET)
	public ModelAndView errorPage() {
		ModelAndView lista = new ModelAndView();
		lista.setViewName("403");
		return lista;
	}
	


}