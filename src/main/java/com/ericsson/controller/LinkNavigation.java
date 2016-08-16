package com.ericsson.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String commentsPageString(
			@RequestParam(value = "userIds", required = false, defaultValue = "") Integer[] userIds, Model model) {
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
	
	@RequestMapping(value = "/commentAdded", params="submit", method = RequestMethod.POST)
	public ModelAndView commentAdded(
			@RequestParam(value = "message1") String[] message1List,
			@RequestParam(value = "message2") String[] message2List,

			@RequestParam("ballsNumber") Integer[] ballsNumberList,
			@ModelAttribute("userList") ArrayList<User> userList) {
		System.out.println("******************3*******"+userList);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		System.out.println("+++++++++++ " + message1List.length);
		System.out.println("+++++++++++ " + message2List.length);
		if (us.getUser(userName).getBall().getBallsToGive() == 0) {
			ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
			System.out.println("================== NIE MASZ JUZ KULEK");
			modelAndView.addObject("outOfBalls", true);
			return modelAndView;
		} else {
			for (int i = 0; i < userList.size(); i++) {
				if (us.getUser(userName).getBall().getBallsToGive() < ballsNumberList[i]) {
					System.out.println("================== NIE MASZ TYLE KULEK");
					ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
					modelAndView.addObject("notEnoughBalls", true);
					return modelAndView;
				} else {
					if (cs.checkNull(message1List[i])) {
						ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
						System.out.println("================== Nie moze byc puste");
						modelAndView.addObject("nullComment", true);
						return modelAndView;
					}
					if (cs.checkNull(message2List[i])) {
						ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
						System.out.println("================== Nie moze byc puste");
						modelAndView.addObject("nullComment", true);
						return modelAndView;
					}
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
	
	@RequestMapping(value = "/commentAdded", params="addMore", method = RequestMethod.POST)
	public String addMoreUsers(
			@ModelAttribute("userList") ArrayList<User> userList,
			Model model,
			@RequestParam(value = "message1") String[] message1List,
			@RequestParam(value = "message2") String[] message2List){
		
		
		List<User> listt1 = us.getAllUsers();
		List<Integer> ids = new ArrayList<Integer>();
		int id;
		if (userList != null){
			for (int i=0;i<userList.size(); i++){
				ids.add(userList.get(i).getId());
			}
			if (ids != null){
				for (int i=0; i<ids.size(); i++){
					for (int j=0; j<listt1.size(); j++){
						id = listt1.get(j).getId();
						if (id == ids.get(i))
							listt1.remove(j);
					}
				}
			}
		}
		model.addAttribute("listt1", listt1);	
		model.addAttribute("userList", userList);
		
		
		return "moreComments";
	}
	
	
	@RequestMapping(value="/moreComments", method=RequestMethod.POST)
	public String moreCommentsPage(
			@RequestParam(value = "userAddMoreIds", required = false) Integer[] userAddMoreIds,
			@ModelAttribute("userList") ArrayList<User> userList,
			Model model) {
		
		
		System.out.println("IDS************"+userAddMoreIds);
		if (userAddMoreIds != null){
			for (int i=0; i<userAddMoreIds.length; i++){
				userList.add(us.getUserId(userAddMoreIds[i]));
			}
		}
		model.addAttribute("userList", userList);
		return "comments";
	}
	
	
	@RequestMapping(value = "/editcomment", method = RequestMethod.POST)
	public String editCommentPage(@RequestParam(value = "buttonComId") Integer buttonComId,
			Model model) {
		
		System.out.println(buttonComId);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue/ballValue2);
		String login = us.getUser(userName).getLogin();
		
		Integer kulki=us.getUser(userName).getBall().getBallsToGive();
		
		Comment commentId=cs.getCommentId(buttonComId);
		
		Integer commentToUserId= commentId.getUser().getId();
		Integer comId= commentId.getComId();
		 
		System.out.println("To user:"+commentToUserId);
		System.out.println("Nr kom:"+comId);
		model.addAttribute("commentId", commentId);
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);

		return "editcomment";
	}
	
	@RequestMapping(value = "/commentEdited", method = RequestMethod.POST)
	public ModelAndView  commentEdited(
						
			@RequestParam(value = "message1") String message1,
			@RequestParam(value = "message2") String message2,
			@RequestParam("ballsNumber") Integer ballsNumber,
			@RequestParam("commentToUserId") Integer toUserId,
			@RequestParam("comId") Integer comId,
			
			Model model
			
			) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		
		cs.editComment(message1,message2,ballsNumber,comId);
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		
		return modelAndView;
	}


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

		List<Settings> settingsList = sett.getSettings();
		Integer lastSettingsId = settingsList.get(settingsList.size() - 1).getSettingsID();
		Integer balls_to_give = settingsList.get(lastSettingsId - 1).getBallsPerPerson();

		List<Role> roleList = rs.getRoleId(roleName);
		Integer roleId = roleList.get(0).getId();

		Team teamList = ts.getTeamID(teamName);
		Integer teamID = teamList.getId();

		Department deptList = ds.getDeptID(deptName);
		Integer deptID = deptList.getDeptId();

		bs.addBall(received_balls, balls_to_give, locked, cash);

		List<Ball> lastBallId = bs.getBallId();
		Integer ballsID = lastBallId.get(lastBallId.size() - 1).getBallsId();

		us.addUser(name, surname, login, password, roleId, teamID, ballsID, mail, deptID);
		ModelAndView modelAndView = new ModelAndView("redirect:/settings");
		return modelAndView;
	}
	
	@RequestMapping(value="/teamAdded", method=RequestMethod.POST)
	public ModelAndView teamAdded(
			@RequestParam("teamName") String teamName,
			@RequestParam("leaderLogin") String leaderLogin,
			@RequestParam("deptName") String deptName) {
		
		String[] words = leaderLogin.split("\\s+");
		leaderLogin = words[2];
		
		User userList = us.getUser(leaderLogin);
		Integer leaderID = userList.getId();
		
		Department deptList = ds.getDeptID(deptName);
		Integer deptID = deptList.getDeptId();
		
		ts.addTeam(teamName, leaderID, deptID);
		ModelAndView modelAndView = new ModelAndView("redirect:/settings");
		return modelAndView;
	}
	
	@RequestMapping(value="/deptAdded", method=RequestMethod.POST)
	public ModelAndView deptAdded(
			@RequestParam("deptName") String deptName,
			@RequestParam("leaderLogin") String leaderLogin) {
		
		String[] words = leaderLogin.split("\\s+");
		leaderLogin = words[2];
		
		User userList = us.getUser(leaderLogin);
		Integer leaderID = userList.getId();

		ds.addDept(deptName, leaderID);
		ModelAndView modelAndView = new ModelAndView("redirect:/settings");
		return modelAndView;
	}
	
	@RequestMapping(value="/userRemoved", method=RequestMethod.POST)
	public ModelAndView userRemoved(
			@RequestParam(value = "userDelIds", required = false) Integer[] userDelIds) {
		
		Integer comId;
		List<Comment> commentList;
		for (int i=0; i<userDelIds.length; i++){
			// check if there are any comments for this user
			commentList = cs.getCommentsYouGave(userDelIds[i]);
			if (commentList != null){
				for (int j=0; j<commentList.size(); j++){
					comId = commentList.get(j).getComId();
					cs.removeComment(comId);
				}
			}
			us.removeUser(userDelIds[i]);
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/settings");
		return modelAndView;
	}


	@RequestMapping(value="/confirmedComm", method=RequestMethod.POST)
	public ModelAndView settingsAdd(@RequestParam("confirmButton") Integer commId){
		cs.setConfirm(commId);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		return modelAndView;
	}
	
	@RequestMapping(value="/teamRemoved", method=RequestMethod.POST)
	public ModelAndView teamRemoved(
			@RequestParam(value = "teamDelIds", required = false) Integer[] teamDelIds) {
		
		/*Integer comId;
		List<Comment> commentList;
		for (int i=0; i<teamDelIds.length; i++){
			// check if there are any comments for this user
			commentList = cs.getCommentsYouGave(teamDelIds[i]);
			if (commentList != null){
				for (int j=0; j<commentList.size(); j++){
					comId = commentList.get(j).getComId();
					cs.removeComment(comId);
				}
			}
			us.removeUser(teamDelIds[i]);
		}*/

		for (int i=0; i<teamDelIds.length; i++){
			ts.removeTeam(teamDelIds[i]);
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/settings");
		return modelAndView;
	}
}