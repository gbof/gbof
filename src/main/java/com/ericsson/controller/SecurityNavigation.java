package com.ericsson.controller;


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

import com.ericsson.model.Comment;
import com.ericsson.model.Department;
import com.ericsson.model.Role;

import com.ericsson.model.Settings;
import com.ericsson.model.Team;

import com.ericsson.model.User;
import com.ericsson.service.BallService;
import com.ericsson.service.CommentService;
import com.ericsson.service.DepartmentService;
import com.ericsson.service.RoleService;
import com.ericsson.service.SettingService;
import com.ericsson.service.TeamService;

import com.ericsson.service.UserService;


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
	
	@Autowired
	private BallService bs;
	
	
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
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		   Date date = new Date();
		   Date date1 = sett.getSettingsDate(idDept).get(0);
		   if(date1.before(date)){
			   sett.setToFrozen(idDept);
			   
		   }
		   
		   List<Settings> settingsList=sett.getSettings(idDept);
			if(settingsList != null)
			{
			if(settingsList.get(0).getFreeze()==1)
			{
				
				coms.setConfirmAll();
			}
			}
		if (role.getRole().equals("admin"))
			return new ModelAndView("redirect:/adminview");
			else if (role.getRole().equals("superuser"))
				return new ModelAndView("redirect:/superUser");
			else if(sett.getSettingsFreeze(idDept).get(0)==1)
				return new ModelAndView("redirect:/freeze");
			else if (role.getRole().equals("moderator"))
				return new ModelAndView("redirect:/inside");
			else 
				return new ModelAndView("redirect:/inside");
	
	}
	@RequestMapping(value="/inside", method=RequestMethod.GET)
	public ModelAndView insidePage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		
		String login = us.getUser(userName).getLogin();

		Integer id = us.getUser(userName).getId();
		

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
		
		String login = us.getUser(userName).getLogin();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		Integer id = us.getUser(userName).getId();
		List<User> listt = us.getAllUsers();
		List<Team> teamlistt = ts.getAllTeams();
		Integer settingsId = us.getUser(userName).getDept().getDeptId();
		List<Double> money = sett.getMoney(settingsId);
		Double moneyValue = money.get(0);
		
		Integer checkAreAllCommentsConfirmed = 0;
		if(coms.getConfirmedComments()==null){
			checkAreAllCommentsConfirmed=1;
		}
		
		
		List<Comment> yourComments=coms.getYourComments(id);

		List<Long> ballValue2List = coms.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		
		List<Comment> commentList = coms.getAllComments();
	
		List<Comment> commentConfirmedList = coms.getConfirmedComments();
		String role = us.getUser(userName).getRole().getRole();
		ModelAndView lista = new ModelAndView();
		
		List<Integer> allBallsGivenTo = new ArrayList<Integer>();
		for(int i=0;i<listt.size();i++){
			allBallsGivenTo.addAll(coms.getAllBallsGivenTo(id, listt.get(i).getId()));
			if(allBallsGivenTo.get(i)==null)allBallsGivenTo.set(i,0);
		}
						
		Double suma = 0.0;
		Double extraMoney;
		List<Double> moneyList = new ArrayList<Double>();
		List<Double> extraMoneyList = new ArrayList<Double>();
		List<Double> summedMoneyList = new ArrayList<Double>();
		for(int i=0;i<listt.size();i++){
			Integer userMoney = bs.getReceivedMoney(listt.get(i).getBall().getBallsId(), wynik).get(0);
			Double wynik1 = userMoney*wynik;
			extraMoney = listt.get(i).getBall().getCash();getClass();
			Double wynik3 = sett.round(wynik1, 2);
			moneyList.add(wynik3);
			wynik1 = wynik1 + extraMoney;
			Double wynik2 = wynik1;
			wynik1 = sett.round(wynik1, 2);
			summedMoneyList.add(wynik1);
			suma=suma+wynik2;	
			extraMoney = sett.round(extraMoney, 2);
			extraMoneyList.add(listt.get(i).getBall().getCash());
		}
		suma = sett.round(suma, 2);
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingsList=sett.getSettings(idDept);
		lista.addObject("suma",suma);
		lista.addObject("moneyList", moneyList);
		lista.addObject("summedMoneyList", summedMoneyList);
		lista.addObject("extraMoneyList", extraMoneyList);
		lista.addObject("settingsList", settingsList);
		lista.addObject("checkAreAllCommentsConfirmed",checkAreAllCommentsConfirmed);
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
		
		List<User> listt = us.getAllUsersForSuperUser();
		List<String> userBasiclistt = new ArrayList<String>();
		List<Department> deptlistt = ds.getAllDepts();
		
		
		
		for (int i=0; i<deptlistt.size(); i++){
			if (deptlistt.get(i).getDeptName().equals(us.getUser(userName).getDept().getDeptName()))
				deptlistt.remove(i);
		}
		List<String> deptLeaders = new ArrayList<String>();
		List<Team> teamlistt = ts.getAllTeams();
		String leader;
		
		for(Department d : deptlistt){
			leader = us.getUserId(d.getDeptLeaderId()).getName() + " " + us.getUserId(d.getDeptLeaderId()).getSurname() + " (" + us.getUserId(d.getDeptLeaderId()).getLogin() + ")";
			deptLeaders.add(leader);
		}
		
		List<Department> deptlistt1 = ds.getAllDepts();
		String _name;
		String _surname;
		String _login;
		boolean isLeader = false;
		
		Integer superuserroleID = rs.getRoleId("superuser").get(0).getId();
		Integer superuserID = 0;
		for (User u : listt){
			if (u.getRole().getId().equals(superuserroleID))
				superuserID = u.getId();
		}
		
		for (int i=0; i<listt.size(); i++){
			for (int j=0; j<deptlistt1.size(); j++){
				Integer l = deptlistt1.get(j).getDeptLeaderId();
				if (l.equals(listt.get(i).getId()) && !l.equals(superuserID)){
					isLeader = true;
				}
			}
			if (isLeader == false){
				_name = listt.get(i).getName();
				_surname = listt.get(i).getSurname();
				_login = listt.get(i).getLogin();
				userBasiclistt.add(_name+" "+_surname+" "+_login);
			}
			isLeader = false;
		}
		String rola = "superuser";
		
		List<User> adminlistt = new ArrayList<User>();
		for (User u:listt){
			if (u.getRole().getRole().equals("admin")){
				adminlistt.add(u);
			}
		}
		
		ModelAndView lista = new ModelAndView();
		lista.addObject("login", login);
		lista.addObject("rola", rola);
		lista.addObject("userBasiclistt", userBasiclistt);
		lista.addObject("deptlistt", deptlistt);
		lista.addObject("deptLeaders", deptLeaders);
		lista.addObject("teamlistt", teamlistt);
		lista.addObject("adminlistt", adminlistt);
		lista.setViewName("superUser");
		return lista;
	}
	
	
	
	
	
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView usersPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<User> listt = us.getAllUsers();
		String userName = userDetails.getUsername();
		Integer id = us.getUser(userName).getId();
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
		lista.addObject("id", id);
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