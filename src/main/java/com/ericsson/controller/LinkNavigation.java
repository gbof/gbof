package com.ericsson.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ericsson.init.MailMail;
import com.ericsson.model.Ball;
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
import com.ericsson.service.UserRolesService;
import com.ericsson.service.SettingService;
import com.ericsson.service.TeamService;
import com.ericsson.service.UserService;

@Controller
@SessionAttributes("userList")
public class LinkNavigation {

	@Autowired
	private UserService us;

	@Autowired
	private CommentService cs;

	@Autowired
	private BallService bs;

	@Autowired
	private SettingService sett;

	@Autowired
	private RoleService rs;

	@Autowired
	private TeamService ts;

	@Autowired
	private DepartmentService ds;
	
	@Autowired
	private UserRolesService rus;

	private Integer received_balls;
	private boolean locked;
	private double cash;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView indexPage() {
		return new ModelAndView("home");
	}

	
	@RequestMapping(value = "/helpPage", method = RequestMethod.GET)
	public ModelAndView helpPage() {
		
		ModelAndView modelandview = new ModelAndView();
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();
		Integer kulki = us.getUser(userName).getBall().getBallsToGive();
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingsList=sett.getSettings(idDept);
		modelandview.addObject("settingsList",settingsList);
		modelandview.addObject("money", wynik);
		modelandview.addObject("kule", kulki);
		modelandview.addObject("login", login);
		return modelandview;
	}

	
	@RequestMapping(value = "/SUuserAdded", method = RequestMethod.POST)
	public ModelAndView userAddedforsuperuser(
			@RequestParam("name") String name, 
			@RequestParam("surname") String surname,
			@RequestParam("login") String login,  
			@RequestParam("mail") String mail,
			@RequestParam("deptName") String deptName) {

		// default initial values
		received_balls = 0;
		locked = false;
		cash = 0;

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login2 = us.getUser(userName).getLogin();
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingsList = sett.getSettings(idDept);
		Integer balls_to_give = settingsList.get(0).getBallsPerPerson();
		
		
		List<Role> roleList = rs.getRoleId("admin");
		Integer roleId = roleList.get(0).getId();
	

		Integer deptID = ds.getDeptID(deptName).getDeptId();
		Integer current_leader_id = ds.getDeptID(deptName).getDeptLeaderId();
		Integer teamID = 0;
		
		List<Team> teamList = ts.getTeamsID("no-team");
		for ( int i=0; i<teamList.size(); i++){
			if (teamList.get(i).getDeptId() == deptID){
				teamID = teamList.get(i).getId();
			}
		}

		List<User> listt = us.getAllUsersForSuperUser();
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		List<String> userBasiclistt = new ArrayList<String>();
		List<Department> deptlistt1 = ds.getAllDepts();
		Integer superuserroleID = rs.getRoleId("superuser").get(0).getId();
		Integer superuserID = 0;
		for (User u : listt){
			if (u.getRole().getId().equals(superuserroleID))
				superuserID = u.getId();
		}
		String _name;
		String _surname;
		String _login;
		boolean isLeader = false;
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
				

		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);

		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");

		
		if (us.checkLogin(login)==true)
		{
			
			bs.addBall(received_balls, balls_to_give, locked, cash);
			List<Ball> lastBallId = bs.getBallId();
			Integer ballsID = lastBallId.get(lastBallId.size() - 1).getBallsId();
			us.addUser(name, surname, login, roleId, teamID, ballsID, mail, deptID);
			Integer userId = us.getUser(login).getId();
			Integer leaderID = us.getUser(login).getId();			
			rus.add(userId, roleId);
			if (!us.getUserId(current_leader_id).getRole().getRole().equals("superuser")){
				rus.editRole(current_leader_id, rs.getRoleId("user").get(0).getId());
				us.editRoleID(current_leader_id, rs.getRoleId("user").get(0).getId());
			}
			String dname = ds.getDeptName(deptID);
			ds.editDepartment(deptID, dname, leaderID);
			modelAndView.addObject("Uadded", true);
			modelAndView.addObject("Ubadlogin", false);
		}
		else
		{
			modelAndView.addObject("Ubadlogin", true);
			modelAndView.addObject("Uadded", false);
		}

		List<String> deptLeaders = new ArrayList<String>();
		String leader;
		
		for(Department d : deptlistt){
			leader = us.getUserId(d.getDeptLeaderId()).getName() + " " + us.getUserId(d.getDeptLeaderId()).getSurname();
			deptLeaders.add(leader);
		}
		modelAndView.addObject("settingsList",settingsList);
		modelAndView.addObject("listt", listt);
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("rola", role);
		modelAndView.addObject("login", login2);
		modelAndView.addObject("rolelistt", rolelistt);
		modelAndView.addObject("teamlistt", teamlistt);
		modelAndView.addObject("deptlistt", deptlistt);
		modelAndView.addObject("userBasiclistt", userBasiclistt);
		modelAndView.addObject("deptLeaders", deptLeaders);
		return modelAndView;
	}
	

	
	@RequestMapping(value = "/SUuserRemoved", method = RequestMethod.GET)
	public ModelAndView SUuserRemoved(
			@ModelAttribute("delete") Integer buttonComId, Model model){
		
		List<User> listt = us.getAllUsers();
		Integer comId;
		List<Comment> commentList;
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		
		String login = us.getUser(userName).getLogin();
		
		String role = us.getUser(userName).getRole().getRole();
		
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
		
		Integer superuserroleID = rs.getRoleId("superuser").get(0).getId();
		Integer superuserID = 0;
		for (User u : listt){
			if (u.getRole().getId().equals(superuserroleID))
				superuserID = u.getId();
		}
		
		for (Department d:deptlistt){
			if (us.getUserId(buttonComId).getId().equals(d.getDeptLeaderId())){
				String name = ds.getDeptName(d.getDeptId());
				ds.editDepartment(d.getDeptId(), name, superuserID);				
			}	
		}
		
		
		List<String> userBasiclistt = new ArrayList<String>();
		Integer idDept=us.getUser(userName).getDept().getDeptId();
		List<Settings> settingsList=sett.getSettings(idDept);
		String _name;
		String _surname;
		String _login;
		for (int i=0; i<listt.size(); i++){
			_name = listt.get(i).getName();
			_surname = listt.get(i).getSurname();
			_login = listt.get(i).getLogin();
			userBasiclistt.add(_name+" "+_surname+" "+_login);
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		if(sett.getSettingsFreeze(idDept).get(0)==1)
			modelAndView.addObject("checked", true);
		else
			modelAndView.addObject("checked", false);
		modelAndView.addObject("listt", listt);
		modelAndView.addObject("settingsList",settingsList);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("rola", role);
		modelAndView.addObject("login", login);
		modelAndView.addObject("rolelistt", rolelistt);
		modelAndView.addObject("teamlistt", teamlistt);
		modelAndView.addObject("deptlistt", deptlistt);
		modelAndView.addObject("userBasiclistt", userBasiclistt);
		
		Integer[] userDelIds = {buttonComId};
		if(userDelIds.length == 0)
		{
			modelAndView.addObject("uRemoved", false);
			return modelAndView;
		}
		else{
		for (int i = 0; i < userDelIds.length; i++) {
			// check if there are any comments for this user
			commentList = cs.getCommentsYouGave(userDelIds[i]);
			if (commentList != null) {
				for (int j = 0; j < commentList.size(); j++) {
					comId = commentList.get(j).getComId();
					cs.removeComment(comId);
				}
			}
			Integer balls_id = us.getUserId(userDelIds[i]).getBall().getBallsId();
			us.removeUser(userDelIds[i]);
			bs.removeBalls(balls_id);
			rus.removeUserRole(userDelIds[i]);
			modelAndView.addObject("uRemoved", true);
		}
		}
		return modelAndView;
	}

	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public ModelAndView sendMail(){
		
		return new ModelAndView("redirect:/success-login");
		/*
    	ApplicationContext context =
                new ClassPathXmlApplicationContext("Spring-Mail.xml");

       	MailMail mm = (MailMail) context.getBean("mailMail");
           mm.sendMail("internshiptestproject@gmail.com",
       		   "plotkara94@gmail.com",
       		   "Testing123",
       		   "Testing only \n\n Hello Spring Email Sender");
		
		
		
		return new ModelAndView("redirect:/success-login");
		*/
	}
	
	@RequestMapping(value="/noTeams", method=RequestMethod.GET)
	public ModelAndView noTeamsPage() {
		ModelAndView lista = new ModelAndView();
		lista.setViewName("noTeams");
		return lista;
	}
}