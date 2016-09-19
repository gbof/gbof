package com.ericsson.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

@Controller
public class SettingsController {

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
	
	
	@RequestMapping(value="/settings", method=RequestMethod.GET)
	public ModelAndView settingsPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login = us.getUser(userName).getLogin();
		Integer idDept=us.getUser(userName).getDept().getDeptId();
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

		List<Settings> settingsList=sett.getSettings(idDept);
		
		
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();

		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		

		 Date date = new Date();
		   Date date1 = sett.getSettingsDate(idDept).get(0);
		   if(date1.before(date)){
			   sett.setToFrozen(idDept);
			   
		   }
		   
		for (int i=0; i<rolelistt.size(); i++){
			if (rolelistt.get(i).getRole().equals("superuser") || rolelistt.get(i).getRole().equals("admin"))
				rolelistt.remove(i);
		}
		ModelAndView lista = new ModelAndView();
		
		if(sett.getSettingsFreeze(idDept).get(0)==1)
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
			@RequestParam(value="deadline", required=false, defaultValue="") String deadline1,
			
			@RequestParam(value = "checkbox", required = false, defaultValue = "0") Integer[] isFreeze,
			@RequestParam("helpMsg") String helpMsg)
			 {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName = userDetails.getUsername();

		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingslist = sett.getSettings(idDept);
		

		String deadline = "";
		if (deadline1.equals(settingslist.get(0).getDeadline().toString())){
			deadline = deadline1;
		}
		else
		{
		String[] words = deadline1.toString().split("/");
		deadline = words[2]+"-"+words[0]+"-"+words[1];
		}
		
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
		sett.addSetting(ballsPerPers,money,deadline,freeze,idDept,helpMsg);
		
		
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
		
		List<Settings> settingsList=sett.getSettings(idDept);
		
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		 Date date = new Date();
		   Date date1 = sett.getSettingsDate(idDept).get(0);
		   if(date1.before(date)){
			   sett.setToFrozen(idDept);
			   
		   }
		if(sett.getSettingsFreeze(idDept).get(0)==1)
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
		
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		String login = us.getUser(userName).getLogin();
		List<Double> Lmoney = sett.getMoney(1);
		Double moneyValue = Lmoney.get(0);
		List<Long> ballValue2List = coms.getBallValue2();
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		wynik = sett.round(wynik, 2);
		ModelAndView modelAndView = new ModelAndView("settings");
		wynik = sett.round(wynik, 2);
		
				
		Double withExtraMoney=money+extramoney;
		sett.addExtraMoney(idDept, withExtraMoney);
		
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
		
		List<Settings> settingsList=sett.getSettings(idDept);
		
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		 Date date = new Date();
		   Date date1 = sett.getSettingsDate(idDept).get(0);
		   if(date1.before(date)){
			   sett.setToFrozen(idDept);
			   
		   }
		if(sett.getSettingsFreeze(idDept).get(0)==1)
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
}
