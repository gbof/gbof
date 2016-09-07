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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
		List<Settings> settingsList=sett.getSettings();
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
		modelandview.addObject("settingsList",settingsList);
		modelandview.addObject("money", wynik);
		modelandview.addObject("kule", kulki);
		modelandview.addObject("login", login);
		return modelandview;
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public String commentsPageString(
			@RequestParam(value = "userIds", required = false, defaultValue = "") Integer[] userIds, Model model,
			@ModelAttribute("message1List") ArrayList<String> message1List,
			@ModelAttribute("message2List") ArrayList<String> message2List,
			@ModelAttribute("ballsNumberList") ArrayList<Integer> ballsNumberList) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Integer> idList = new ArrayList<Integer>();
		List<User> userList = new ArrayList<User>();
		if (userIds.length == 0) {
			return "redirect:/success-login";
		} else {
			for (int i = 0; i < userIds.length; i++) {
				userList.add(us.getUserId(userIds[i]));
				idList.add(userIds[i]);
				model.addAttribute("userList", userList);
				model.addAttribute("correct", true);
			}
		}
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();

		Integer kulki = us.getUser(userName).getBall().getBallsToGive();

		model.addAttribute("idList", idList);
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		return "comments";
	}

	@RequestMapping(value = "/commentAdded", params = "submit", method = RequestMethod.POST)
	public ModelAndView commentAdded(
			@RequestParam(value = "message1") ArrayList<String> message1List,
			@RequestParam(value = "message2") ArrayList<String> message2List,
			@RequestParam(value = "ballsNumber") ArrayList<Integer> ballsNumberList,
			@ModelAttribute("userList") ArrayList<User> userList) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		
		Integer allBallsGiven = 0;
		for (int i = 0; i < userList.size(); i++)
			allBallsGiven = allBallsGiven+ballsNumberList.get(i);
		if (allBallsGiven > us.getUser(userName).getBall().getBallsToGive()) {
			ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
			modelAndView.addObject("outOfBalls", true);
			return modelAndView;
		} else {
			for (int i = 0; i < userList.size(); i++) {
				if (us.getUser(userName).getBall().getBallsToGive() < ballsNumberList.get(i)) {
				
					ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
					modelAndView.addObject("notEnoughBalls", true);
					return modelAndView;
				} else {
					if (cs.checkNull(message1List.get(i))) {
						ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
						
						modelAndView.addObject("nullComment", true);
						return modelAndView;
					}
					if (cs.checkNull(message2List.get(i))) {
						ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
					
						modelAndView.addObject("nullComment", true);
						return modelAndView;
					}
					Integer commentToUserId = userList.get(i).getId();
					cs.addComment(message1List.get(i), message2List.get(i), ballsNumberList.get(i), user_id, commentToUserId);
					us.setBallsAfterComment(user_id, ballsNumberList.get(i), commentToUserId);
				}
				;
			}
			;
			ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		
			modelAndView.addObject("success-comment", true);
			return modelAndView;

		}
	}

	@RequestMapping(value = "/commentAdded", params = "addMore", method = RequestMethod.POST)
	public String addMoreUsers(@ModelAttribute("userList") ArrayList<User> userList, Model model,

			@RequestParam(value = "message1", defaultValue="") ArrayList<String> message1List,
			@RequestParam(value = "message2", defaultValue="") ArrayList<String> message2List,
			@RequestParam(value = "ballsNumber", defaultValue="0") ArrayList<Integer> ballsNumberList) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();
		Integer id1 = us.getUser(userName).getId();

		Integer kulki = us.getUser(userName).getBall().getBallsToGive();
		
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		
		List<User> listt1 = us.getAllUsers();
		List<Integer> ids = new ArrayList<Integer>();
		int id;
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				ids.add(userList.get(i).getId());
			}
			if (ids != null) {
				for (int i = 0; i < ids.size(); i++) {
					for (int j = 0; j < listt1.size(); j++) {
						id = listt1.get(j).getId();
						if (id == ids.get(i))
							listt1.remove(j);
					}
				}
			}
		}
		model.addAttribute("listt1", listt1);
		
		List<Integer> allBallsGivenTo = new ArrayList<Integer>();
		for(int i=0;i<listt1.size();i++){
			allBallsGivenTo.addAll(cs.getAllBallsGivenTo(id1, listt1.get(i).getId()));
			if(allBallsGivenTo.get(i)==null)allBallsGivenTo.set(i,0);
		}
		model.addAttribute("allBallsGivenTo", allBallsGivenTo);

		String[] allMess1 = new String[message1List.size()];
		for (int i = 0; i < message1List.size(); i++) {
			allMess1[i] = message1List.get(i);
		}

	

		String[] allMess2 = new String[message2List.size()];
		for (int i = 0; i < message2List.size(); i++) {
			allMess2[i] = message2List.get(i);
		}

		if (ballsNumberList.size() == 0) ballsNumberList.add(0);



		String[] allBalls = new String[ballsNumberList.size()];
		for (int i = 0; i < ballsNumberList.size(); i++) {
			if (ballsNumberList.get(i) == null){
				allBalls[i] = "0";
			} else{
				allBalls[i] = ballsNumberList.get(i).toString();
			}
		}
		
		String allMess1s = String.join(";;;;;;", allMess1);

		

		model.addAttribute("allMess1s", allMess1s);
		
		String allMess2s = String.join(";;;;;;", allMess2);


	

		model.addAttribute("allMess2s", allMess2s);
		
		String allBallss = String.join(";;;;;;", allBalls);

		model.addAttribute("allBallss", allBallss);
		
		return "moreComments";
	}

	@RequestMapping(value = "/moreComments", method = RequestMethod.POST)
	public String moreCommentsPage(
			@RequestParam(value = "userAddMoreIds", defaultValue = "") Integer[] userAddMoreIds,
			@ModelAttribute("userList") ArrayList<User> userList, Model model,
			@RequestParam("allMess1s") String allMess1s,
			@RequestParam("allMess2s") String allMess2s,
			@RequestParam("allBallss") String allBallss) {
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
		
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);

		
		String[] message1List = allMess1s.split(";;;;;;");
		String[] message2List = allMess2s.split(";;;;;;");
		String[] ballsNumberLists = allBallss.split(";;;;;;");
		
	

		Integer[] ballsNumberList = new Integer[ballsNumberLists.length];
		for (int i=0; i<ballsNumberLists.length; i++) {
			ballsNumberList[i] = (Integer.parseInt(ballsNumberLists[i]));

			}


		model.addAttribute("message1List", message1List);
		model.addAttribute("message2List", message2List);
		
		if (userAddMoreIds != null) {
			for (int i = 0; i < userAddMoreIds.length; i++) {
				userList.add(us.getUserId(userAddMoreIds[i]));
			}
		}
		model.addAttribute("ballsNumberList", ballsNumberList);
		
		return "redirect:/moreComments2";
	}

	@RequestMapping(value = "/moreComments2", method = RequestMethod.GET)
	public String moreCommentsPageGet2(@ModelAttribute("userList") ArrayList<User> userList,
			@RequestParam(value="message1List", defaultValue="") ArrayList<String> message1List,
			@RequestParam(value="message2List", defaultValue="") ArrayList<String> message2List,
			@RequestParam(value="ballsNumberList", defaultValue="0") ArrayList<Integer> ballsNumberList,
			Model model) {
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
		
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		model.addAttribute("message1List", message1List);
		model.addAttribute("message2List", message2List);
		model.addAttribute("ballsNumberList", ballsNumberList);
		return "/comments";
	}

	@RequestMapping(value = "/editcomment", method = RequestMethod.POST)
	public String editCommentPage(@RequestParam(value = "buttonComId") Integer buttonComId, Model model) {
		
		
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

		Comment commentId = cs.getCommentId(buttonComId);

		Integer commentToUserId = commentId.getUser().getId();
		Integer comId = commentId.getComId();

		
		model.addAttribute("commentId", commentId);
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);

		return "editcomment";
	}

	@RequestMapping(value = "/commentEdited", method = RequestMethod.POST)
	public ModelAndView commentEdited(

			@RequestParam(value = "message1") String message1, @RequestParam(value = "message2") String message2,
			@RequestParam("ballsNumber") Integer ballsNumber, @RequestParam("commentToUserId") Integer toUserId,
			@RequestParam("comId") Integer comId,
			
			Model model

	) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		if(cs.getCommentId(comId).getBallsPerCom()!=ballsNumber){
			if(cs.getCommentId(comId).getCreatorId()==user_id)
			us.setBallsAfterCommentEdit(user_id, cs.getCommentId(comId).getBallsPerCom(), ballsNumber, toUserId);
			else{
				if(cs.getCommentId(comId).getBallsPerCom()>ballsNumber)
				us.setBallsAfterCommentEdit(cs.getCommentId(comId).getCreatorId(), cs.getCommentId(comId).getBallsPerCom(), ballsNumber, toUserId);
				else
					us.setBallsAfterCommentEdit(user_id, cs.getCommentId(comId).getBallsPerCom(), ballsNumber, toUserId);
			}
		}
		cs.editComment(message1, message2, ballsNumber, comId);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");

		return modelAndView;
	}
	@RequestMapping(value = "/commentsEdited", method = RequestMethod.POST)
	public ModelAndView commentsEdited(

			@RequestParam(value = "message1") ArrayList<String> messages1,
			@RequestParam(value = "message2") ArrayList<String> messages2,
			@RequestParam("ballsPerCom") ArrayList<Integer> balls,			
			@RequestParam("comId") ArrayList<Integer> comId,
			
			Model model

	) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		
		
		for(int i=0;i<comId.size();i++)
		{
			cs.editComment(messages1.get(i), messages2.get(i), balls.get(i), comId.get(i));
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");

		return modelAndView;
	}
	@RequestMapping(value = "/confirmedComm", params="confirmButton", method = RequestMethod.POST)
	public ModelAndView confirmComm(@RequestParam("confirmButton") Integer commId) {
		cs.setConfirm(commId);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/confirmedComm", params="buttonComId", method = RequestMethod.POST)
	public ModelAndView adminEditComm(@RequestParam("buttonComId")  Integer buttonComId, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		//Comment commentId = cs.getCommentId(buttonComId);
		List<Comment> commentsList=cs.getCommentsYouGave(buttonComId);
		String login = us.getUser(userName).getLogin();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		ModelAndView modelAndView = new ModelAndView("editcomments");
		User user = us.getUser(userName);
		
				
		modelAndView.addObject("user", user);
		modelAndView.addObject("commentList", commentsList);
		modelAndView.addObject("login", login);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		return modelAndView;
	}
	
	@RequestMapping(value = "/confirmedComm", params="deleteButton", method = RequestMethod.POST)
	public ModelAndView deleteComm(@RequestParam("deleteButton") Integer commId) {
		us.setBallsAfterCommentDelete(cs.getCommentId(commId).getCreatorId(), cs.getCommentId(commId).getBallsPerCom(), cs.getCommentId(commId).getUser().getId());
		cs.removeComment(commId);
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		return modelAndView;
	}
	
	//delete comment
	@RequestMapping(value = "/editcomment", params="deleteButton1", method = RequestMethod.POST)
	public ModelAndView deleteComm1(@RequestParam("deleteButton1") Integer commId) {
		
		us.setBallsAfterCommentDelete(cs.getCommentId(commId).getCreatorId(), cs.getCommentId(commId).getBallsPerCom(), cs.getCommentId(commId).getUser().getId());
		System.out.println("=========== "+cs.getCommentId(commId).getUser().getId());
		cs.removeComment(commId);
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/userAdded", method = RequestMethod.POST)
	public ModelAndView userAdded(
			@RequestParam("name") String name, 
			@RequestParam("surname") String surname,
			@RequestParam("login") String login, 
			@RequestParam("roleName") String roleName, 
			@RequestParam("teamName") String teamName,
			@RequestParam("mail") String mail) {

		// default initial values
		received_balls = 0;
		locked = false;
		cash = 0;

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login2 = us.getUser(userName).getLogin();
		String role = us.getUser(userName).getRole().getRole();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		List<Settings> settingsList = sett.getSettings();
		Integer lastSettingsId = settingsList.get(settingsList.size() - 1).getSettingsID();
		Integer balls_to_give = settingsList.get(lastSettingsId - 1).getBallsPerPerson();
		
		
		
		List<Role> roleList = rs.getRoleId(roleName);
		Integer roleId = roleList.get(0).getId();
		
		System.out.println("roleId==="+roleId);
		
		Team teamList = ts.getTeamID(teamName);
		Integer teamID = teamList.getId();

		Integer deptID = us.getUser(userName).getDept().getDeptId();

		


		List<User> listt = us.getAllUsers();
		
		
		

		List<Role> rolelistt = rs.getAllRoles();
		List<Team> teamlistt = ts.getAllTeams();
		List<Department> deptlistt = ds.getAllDepts();
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
				
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		

		ModelAndView modelAndView = new ModelAndView("settings");
		
		if (us.checkLogin(login)==true)
		{
			
			bs.addBall(received_balls, balls_to_give, locked, cash);
			List<Ball> lastBallId = bs.getBallId();
			Integer ballsID = lastBallId.get(lastBallId.size() - 1).getBallsId();
			us.addUser(name, surname, login, roleId, teamID, ballsID, mail, deptID);
			Integer userId = us.getUser(login).getId();
			rus.add(userId, roleId);
			modelAndView.addObject("Uadded", true);
		}
		else
		{
			modelAndView.addObject("Ubadlogin", true);
		}
		
				
		for (int i=0; i<rolelistt.size(); i++){
			if (rolelistt.get(i).getRole().equals("superuser") || rolelistt.get(i).getRole().equals("admin"))
				rolelistt.remove(i);
		}
		
		if(sett.getSettingsFreeze().get(0)==1)
			modelAndView.addObject("checked", true);
		else
			modelAndView.addObject("checked", false);
		
		modelAndView.addObject("settingsList",settingsList);
		modelAndView.addObject("listt", listt);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		modelAndView.addObject("rola", role);
		modelAndView.addObject("login", login2);
		modelAndView.addObject("rolelistt", rolelistt);
		modelAndView.addObject("teamlistt", teamlistt);
		modelAndView.addObject("deptlistt", deptlistt);
		modelAndView.addObject("userBasiclistt", userBasiclistt);
		return modelAndView;
	}

	
	@RequestMapping(value = "/userAddedforsuperuser", method = RequestMethod.POST)
	public ModelAndView userAddedforsuperuser(
			@RequestParam("name") String name, 
			@RequestParam("surname") String surname,
			@RequestParam("login") String login, 
			@RequestParam("roleName") String roleName, 
			@RequestParam("teamName") String teamName,
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
		List<Settings> settingsList = sett.getSettings();
		Integer lastSettingsId = settingsList.get(settingsList.size() - 1).getSettingsID();
		Integer balls_to_give = settingsList.get(lastSettingsId - 1).getBallsPerPerson();
		
		
		
		List<Role> roleList = rs.getRoleId(roleName);
		Integer roleId = roleList.get(0).getId();
	

		Integer deptID = ds.getDeptID(deptName).getDeptId();
		Integer current_leader_id = ds.getDeptID(deptName).getDeptLeaderId();
		Integer teamID = 0;
		
		List<Team> teamList = ts.getTeamsID(teamName);
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
		
		String _name;
		String _surname;
		String _login;
		boolean isLeader = false;
		for (int i=0; i<listt.size(); i++){
			for (int j=0; j<deptlistt1.size(); j++){
				if (deptlistt1.get(j).getDeptLeaderId().equals(listt.get(i).getId())){
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

		

		ModelAndView modelAndView = new ModelAndView("superUser");
		
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
			ds.editDepartment(deptID, leaderID);
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
	

	@RequestMapping(value = "/edituser", params="buttonComId", method = RequestMethod.POST)
	public String editUserPage(@RequestParam(value = "buttonComId") Integer buttonComId, Model model) {
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
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		User user = us.getUserId(buttonComId);
		
		List<Role> rolelistt = rs.getAllRoles();
		String roleName = user.getRole().getRole();
		List<Role> role = rs.getRoleId(roleName);
		rolelistt.remove(role.get(0).getId()-1);
		
		List<Department> deptlistt = ds.getAllDepts();
		String deptName = user.getDept().getDeptName();
		Department dept = ds.getDeptID(deptName);
		deptlistt.remove(dept.getDeptId()-1);
		
		List<Team> teamlistt = ts.getAllTeams();
		String teamName = user.getTeam().getName();
		System.out.println("teamName=="+teamName);
		Team team = ts.getTeamID(teamName);
		System.out.println("team===="+team.getId());
		
		Integer ballstogive = user.getBall().getBallsToGive();
		for (int i=0; i<rolelistt.size(); i++){
			if (rolelistt.get(i).getRole().equals("superuser") || rolelistt.get(i).getRole().equals("admin"))
				rolelistt.remove(i);
		}
		for (int i=0; i<teamlistt.size(); i++){
			if (teamlistt.get(i).getId() == teamlistt.get(i).getId())
				teamlistt.remove(i);
		}
		model.addAttribute("user", user);
		model.addAttribute("rolelistt", rolelistt);
		model.addAttribute("deptlistt", deptlistt);
		model.addAttribute("teamlistt", teamlistt);
		model.addAttribute("ballstogive", ballstogive);

		return "edituser";
	}
	
	@RequestMapping(value = "/edituser", params="delete", method = RequestMethod.POST)
	public String deleteUserPage(@RequestParam(value = "delete") Integer delete, Model model) {
		model.addAttribute("delete", delete);
		return "redirect:/userRemoved";
	}
	
	@RequestMapping(value = "/userRemoved", method = RequestMethod.GET)
	public ModelAndView userRemoved(
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
		
		List<String> userBasiclistt = new ArrayList<String>();
		List<Settings> settingsList=sett.getSettings();
		String _name;
		String _surname;
		String _login;
		for (int i=0; i<listt.size(); i++){
			_name = listt.get(i).getName();
			_surname = listt.get(i).getSurname();
			_login = listt.get(i).getLogin();
			userBasiclistt.add(_name+" "+_surname+" "+_login);
		}
		ModelAndView modelAndView = new ModelAndView("settings");
		if(sett.getSettingsFreeze().get(0)==1)
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
	
	@RequestMapping(value = "/userEdited", params="save", method = RequestMethod.POST)
	public ModelAndView userEdited(

			@RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname,
			@RequestParam(value = "login") String login, @RequestParam(value = "mail") String mail,
			@RequestParam(value = "role") String role,
			@RequestParam(value = "team") String team, @RequestParam(value = "balls") Integer balls,
			@RequestParam("user_id") Integer user_id,
			Model model

	) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login1 = us.getUser(userName).getLogin();
		Integer roleID = rs.getRoleId(role).get(0).getId();
		Integer deptID = us.getUser(login1).getDept().getDeptId();
		Integer teamID = ts.getTeamID(team).getId();
		Integer balls_id = us.getUserId(user_id).getBall().getBallsId();
		
		
		bs.editBallsToGive(balls_id, balls);
		rus.editRole(user_id, roleID);
		us.editUser(user_id, name, surname, login, mail, roleID, deptID, teamID);
		ModelAndView modelAndView = new ModelAndView("redirect:/users");

		return modelAndView;
	}
	
	@RequestMapping(value = "/userEdited", params="delete", method = RequestMethod.POST)
	public String userRemoved1(
			@RequestParam("user_id") Integer user_id, Model model){
			model.addAttribute("delete", user_id);
			return "redirect:/userRemoved";
	}

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
		List<Settings> settingsList = sett.getSettings();
		Integer lastSettingsId = settingsList.get(settingsList.size() - 1).getSettingsID();
		Integer balls_to_give = settingsList.get(lastSettingsId - 1).getBallsPerPerson();
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
				System.out.println("============= NIE MOGE");
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
		System.out.println("leader:="+leader);
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
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();

		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		

		 Date date = new Date();
		   Date date1 = sett.getSettingsDate().get(0);
		   if(date1.before(date)){
			   sett.setToFrozen();
		   }

		
		ModelAndView lista = new ModelAndView();
		
		if(sett.getSettingsFreeze().get(0)==1)
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
	
	@RequestMapping(value = "/deptAdded", method = RequestMethod.POST)
	public ModelAndView deptAdded(
			@RequestParam("deptName") String deptName,
			@RequestParam("leaderLogin") String leaderLogin) {

		String[] words = leaderLogin.split("\\s+");
		leaderLogin = words[2];

		User userList = us.getUser(leaderLogin);
		Integer leaderID = userList.getId();
		String leaderRole = userList.getRole().getRole();
		
		
		if (!leaderRole.equals("superuser")){
			rus.editRole(leaderID, 1);
			us.editRoleID(leaderID, 1);
		}
		ds.addDept(deptName, leaderID);
		List<Department> lastDeptId = ds.getAllDepts();
		Integer deptID = lastDeptId.get(lastDeptId.size() - 1).getDeptId();
		ts.addTeam("no-team", leaderID, deptID);
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
	
		return modelAndView;
	}

	
/*
	@RequestMapping(value = "/confirmedComm", params="buttonComId", method = RequestMethod.POST)
	public ModelAndView adminEditComm(@RequestParam("buttonComId")  Integer buttonComId, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		Comment commentId = cs.getCommentId(buttonComId);
		String login = us.getUser(userName).getLogin();
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		ModelAndView modelAndView = new ModelAndView("editcomment");
		modelAndView.addObject("commentId", commentId);
		modelAndView.addObject("login", login);
		modelAndView.addObject("money", wynik);
		modelAndView.addObject("kule", kulki);
		return modelAndView;
	}
	
	*/


	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public ModelAndView sendMail(){
		
    	ApplicationContext context =
                new ClassPathXmlApplicationContext("Spring-Mail.xml");

       	MailMail mm = (MailMail) context.getBean("mailMail");
           mm.sendMail("internshiptestproject@gmail.com",
       		   "internshiptestproject@gmail.com",
       		   "Testing123",
       		   "Testing only \n\n Hello Spring Email Sender");
		
		
		
		return new ModelAndView("redirect:/success-login");
	}
	
	@RequestMapping(value="/noTeams", method=RequestMethod.GET)
	public ModelAndView noTeamsPage() {
		ModelAndView lista = new ModelAndView();
		lista.setViewName("noTeams");
		return lista;
	}
}