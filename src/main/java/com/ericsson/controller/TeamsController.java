package com.ericsson.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
import com.ericsson.service.UserRolesService;
import com.ericsson.service.UserService;

@Controller
public class TeamsController {
	@Autowired
	private UserService us;

	@Autowired
	private CommentService cs;

	@Autowired
	private SettingService sett;

	@Autowired
	private RoleService rs;

	@Autowired
	private TeamService ts;

	@Autowired
	private DepartmentService ds;
	

	private Integer received_balls;
	private boolean locked;
	private double cash;

	@RequestMapping(value = "/teamAdded", method = RequestMethod.POST)
	public ModelAndView teamAdded(@RequestParam("teamName") String teamName,
			@RequestParam("leaderLogin") String leaderLogin) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		
		String login = us.getUser(userName).getLogin();
		

		User userList;
		Integer leaderID;
		
		if (leaderLogin.equals("no leader")){
			userList = us.getUser(login);
			leaderID = userList.getId();
		} else {
			String[] words = leaderLogin.split("\\s+");
			leaderLogin = words[2];
			
			userList = us.getUser(leaderLogin);
			leaderID = userList.getId();
		}
		

		Integer deptID = us.getUser(userName).getDept().getDeptId();

		ts.addTeam(teamName, leaderID, deptID);
		ModelAndView modelAndView = new ModelAndView("settings");
		
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingsList = sett.getSettings(idDept);
	
		List<User> listt = us.getAllUsers();
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		
		List<Role> rolelistt = rs.getAllRoles();
		
		
		List<Department> deptlistt = ds.getAllDepts();
	
		
		List<Team> teamlistt = ts.getAllTeams();
	
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
		
		for (int i=0; i<rolelistt.size(); i++){
			if (rolelistt.get(i).getRole().equals("superuser") || rolelistt.get(i).getRole().equals("admin"))
				rolelistt.remove(i);
		}
		
		modelAndView.addObject("tadded", true);
		modelAndView.addObject("settingsList",settingsList);
		modelAndView.addObject("listt", listt);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("rola", role);
		modelAndView.addObject("login", login);
		modelAndView.addObject("rolelistt", rolelistt);
		modelAndView.addObject("teamlistt", teamlistt);
		modelAndView.addObject("deptlistt", deptlistt);
		modelAndView.addObject("userBasiclistt", userBasiclistt);
		return modelAndView;
	}

	@RequestMapping(value = "/editteam",  params="edit", method = RequestMethod.POST)
	public String editTeamPage(
		@RequestParam(value = "edit") Integer buttonComId, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login = us.getUser(userName).getLogin();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		List<User> listt = us.getAllUsersTeam(buttonComId);
		
		Team team = ts.getTeam(buttonComId);
		Integer user_id = ts.getTeam(buttonComId).getLeaderId();
		Integer dept_id = ts.getTeam(buttonComId).getDeptId();
		String deptName = ds.getDeptName(dept_id);
		String leaderName = us.getUserId(user_id).getName();
		String leaderSurname = us.getUserId(user_id).getSurname();
		String leaderLogin = us.getUserId(user_id).getLogin();
		

		List<User> users = new ArrayList<User>();
		if (listt != null){
			for (User t: listt){
				users.add(t);
			}
			for (int i=0; i<users.size(); i++){
				if ( users.get(i).getId() == user_id)
					users.remove(i);
			}
			
			listt.clear();
			for (User t: users){
				listt.add(t);
			}
		}
		deptlistt.remove(dept_id-1);
		
		model.addAttribute("team", team);
		model.addAttribute("leaderName", leaderName);
		model.addAttribute("leaderSurname", leaderSurname);
		model.addAttribute("leaderLogin", leaderLogin);
		model.addAttribute("deptName", deptName);
		model.addAttribute("teamlistt", teamlistt);
		model.addAttribute("listt", listt);
		model.addAttribute("deptlistt", deptlistt);
		model.addAttribute("login", login);
		return "editteam";
	}
	
	//Delete team
		@RequestMapping(value = "/editteam", params="delete", method = RequestMethod.POST)
		public ModelAndView teamRemoved(@RequestParam(value = "delete") Integer teamDelId){
			List<User> listt = us.getAllUsers();

		
			ModelAndView modelAndView = new ModelAndView();
			boolean check = true;
			for(int i=0;i<listt.size();i++){
				if(ts.getTeam(teamDelId).getId()==listt.get(i).getTeam().getId())
					check = false;
			}
			
			if(check){
				ts.removeTeam(teamDelId);
				modelAndView.setViewName("redirect:/teamRemoved");
			}
			else
				modelAndView.setViewName("redirect:/settings");
				
			return modelAndView;
		}
	
	@RequestMapping(value = "/teamEdited", params="save", method = RequestMethod.POST)
	public ModelAndView teamEdited(

			@RequestParam(value = "name") String name, 
			@RequestParam(value = "user") String user,
			@RequestParam(value = "team_id") Integer team_id,
			Model model

	) {
		String[] words = user.split(" ");
		String leader = words[2];
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login = us.getUser(userName).getLogin();
		
		Integer leaderID = us.getUser(leader).getId();
		Integer deptID = us.getUser(userName).getDept().getDeptId();
		
		ts.editTeam(team_id, leaderID, name, deptID);
		ModelAndView modelAndView = new ModelAndView("redirect:/teams");

		model.addAttribute("login", login);
		return modelAndView;
	}
	@RequestMapping(value = "/teamRemoved",  method = RequestMethod.GET)
	public ModelAndView  teamRemoved()
		 {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		
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
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingsList=sett.getSettings(idDept);
		
		
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();

		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		

		 Date date = new Date();
		   Date date1 = sett.getSettingsDate(idDept).get(0);
		   if(date1.before(date)){
			   sett.setToFrozen(idDept);
		   }

		
		ModelAndView lista = new ModelAndView();
		
		if(sett.getSettingsFreeze(idDept).get(0)==1)
			lista.addObject("checked", true);
		else
			lista.addObject("checked", false);
		
		lista.addObject("tremoved", true);
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
	
	
	@RequestMapping(value = "/teams", method = RequestMethod.GET)
	public ModelAndView teamsPage() {
		List<Team> teamlistt = ts.getAllTeams();
		List<String> deptNames = new ArrayList<String>();
		List<String> leaderNames = new ArrayList<String>();
		List<String> leaderSurnames = new ArrayList<String>();
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		ModelAndView lista = new ModelAndView();
		
		for(int i=0;i<teamlistt.size();i++)
		{
			if(teamlistt.get(i).getName().equals("no-team"))
				teamlistt.remove(i);
		}
		
		if(teamlistt==null)
		{
			lista.setViewName("noTeams");
			return lista;
		}
		else{
		for (Team t : teamlistt){
			deptNames.add(ds.getDeptName(t.getDeptId()));
			leaderNames.add(us.getUserId(t.getLeaderId()).getName());
			leaderSurnames.add(us.getUserId(t.getLeaderId()).getSurname());
		}}
		
		lista.addObject("money", wynik);
		lista.addObject("login", login);
		lista.addObject("kule", kulki);
		lista.addObject("teamlistt", teamlistt);
		lista.addObject("deptNames", deptNames);
		lista.addObject("leaderNames", leaderNames);
		lista.addObject("leaderSurnames", leaderSurnames);
		lista.setViewName("teams");
		return lista;
	}
	
	@RequestMapping(value = "/teamEdited", params = "addMore", method = RequestMethod.POST)
	public ModelAndView teamsPageMore(@RequestParam(value = "team_id") Integer team_id) {
		List<User> listt = us.getAllUsers();
		Team team = ts.getTeam(team_id);
		Integer teamId = ts.getTeam(team_id).getId();
		for(int i=0;i<listt.size();i++){
			for(int j=0;j<listt.size();j++)
			if(listt.get(j).getTeam().getId().equals(teamId))
				listt.remove(j);
		}
		ModelAndView lista = new ModelAndView("newUsers");
		lista.addObject("team", team);
		lista.addObject("listt", listt);
		return lista;
	}
	
	@RequestMapping(value = "/teamEdited1", method = RequestMethod.POST)
	public String teamsEdit1(@RequestParam(value = "userAddMoreIds", required = false, defaultValue = "") Integer[] userAddMoreIds, 
			@RequestParam(value = "team_id") Integer team_id) {
		for(int i=0;i<userAddMoreIds.length;i++)
			us.editTeamId(team_id, userAddMoreIds[i]);
		return "redirect:/teams";
	}
}
