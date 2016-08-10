package com.sprsec.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
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


import com.sprsec.model.Ball;
import com.sprsec.model.Department;
import com.sprsec.model.Role;
import com.sprsec.model.Settings;
import com.sprsec.model.Team;
import com.sprsec.model.User;
import com.sprsec.service.BallService;
import com.sprsec.service.CommentService;
import com.sprsec.service.DepartmentService;
import com.sprsec.service.RoleService;
import com.sprsec.service.SettingService;
import com.sprsec.service.TeamService;
import com.sprsec.service.UserService;

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
	private SettingService ss;
	
	@Autowired
	private RoleService rs;
	
	@Autowired
	private TeamService ts;
	
	@Autowired
	private DepartmentService ds;
	
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

	@RequestMapping(value = "/sec/moderation", method = RequestMethod.GET)
	public ModelAndView moderatorPage() {
		return new ModelAndView("moderation");
	}

	@RequestMapping(value = "/admin/first", method = RequestMethod.GET)
	public ModelAndView firstAdminPage() {
		return new ModelAndView("admin-first");
	}

	@RequestMapping(value = "/admin/second", method = RequestMethod.GET)
	public ModelAndView secondAdminPage() {
		return new ModelAndView("admin-second");
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public String commentsPageString(@RequestParam(value = "userIds", required = false) Integer[] userIds,
			Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Integer> idList = new ArrayList<Integer>();
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < userIds.length; i++) {
			userList.add(us.getUserId(userIds[i]));
			idList.add(userIds[i]);
		}
		String login = us.getUser(userName).getLogin();
		
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		model.addAttribute("idList", idList);
		model.addAttribute("userList", userList);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		return "comments";
	}

	@RequestMapping(value = "/commentAdded", method = RequestMethod.POST)
	public ModelAndView commentAdded(@RequestParam(value = "message1") String[] message1List,
			@RequestParam(value = "message2") String[] message2List,
			@RequestParam("ballsNumber") Integer[] ballsNumberList,
			@ModelAttribute("userList") ArrayList<User> userList) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		if (us.getUser(userName).getBall().getBallsToGive() == 0) {
			ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
			System.out.println("================== NIE MASZ JUZ KULEK");
			modelAndView.addObject("success-comment", false);
			return modelAndView;
		} else {
			for (int i = 0; i < message1List.length; i++) {
				if (us.getUser(userName).getBall().getBallsToGive() < ballsNumberList[i]) {
					System.out.println("================== NIE MASZ TYLE KULEK");
					ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
					modelAndView.addObject("success-comment", false);
					return modelAndView;
				} else {
					Integer commentToUserId = userList.get(i).getId();
					cs.addComment(message1List[i], message2List[i], ballsNumberList[i], user_id, commentToUserId);
					us.setBallsAfterComment(user_id, ballsNumberList[i], commentToUserId);
				};
				};
				ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
				System.out.println("================== DODANO KOMENTARZ");
				modelAndView.addObject("success-comment", true);
				return modelAndView;
		
		}
	}

	/*
	 * @RequestMapping(value="/inside", method=RequestMethod.GET) public
	 * ModelAndView insidePage() { return new ModelAndView("inside2"); }
	 */

	
	

	@RequestMapping(value="/userAdded", method=RequestMethod.POST)
	public ModelAndView userAdded(
			@RequestParam("name") String name,
			@RequestParam("surname") String surname,
			@RequestParam("login") String login,
			@RequestParam("password") String password,
			@RequestParam("roleName") String roleName,
			@RequestParam("teamName") String teamName,
			@RequestParam("mail") String mail,
			@RequestParam("deptName") String deptName) {

		// default initial values
		received_balls = 0;
		locked = false;		
		cash = 0;
		
		
		List<Settings> settingsList = ss.getAllSettings();
		Integer lastSettingsId = settingsList.get(settingsList.size()-1).getSettingsID();
		Integer balls_to_give = settingsList.get(lastSettingsId-1).getBallsPerPerson();
		
		List <Role> roleList = rs.getRoleId(roleName);
		Integer roleId = roleList.get(0).getId();
		
		Team teamList = ts.getTeamID(teamName);
		Integer teamID = teamList.getId();
		
		Department deptList = ds.getDeptID(deptName);
		Integer deptID = deptList.getDeptId();

		bs.addBall(received_balls, balls_to_give, locked, cash);
		
		List<Ball> lastBallId = bs.getBallId();
		Integer ballsID = lastBallId.get(lastBallId.size()-1).getBallsId();
		
		us.addUser(name, surname, login, password, roleId, teamID, ballsID, mail, deptID);
		ModelAndView modelAndView = new ModelAndView("redirect:/settings");
		return modelAndView;
	}

}