package com.ericsson.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.ericsson.service.UserRolesService;
import com.ericsson.service.UserService;

@Controller
@SessionAttributes("userList")
public class UserEditController {

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
	@RequestMapping(value = "/userAdded", method = RequestMethod.POST)
	public ModelAndView userAdded(
			@RequestParam("name") String name, 
			@RequestParam("surname") String surname,
			@RequestParam("login") String login, 
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
		Integer idDept=us.getUser(userName).getDept().getDeptId();

		List<Settings> settingsList = sett.getSettings(idDept);
		Integer balls_to_give = settingsList.get(0).getBallsPerPerson();
	
		

		
		
		//Integer lastSettingsId = settingsList.get(settingsList.size() - 1).getSettingsID();
		
		
		
		
		List<Role> roleList = rs.getRoleId("user");
		Integer roleId = roleList.get(0).getId();
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
		
		if(sett.getSettingsFreeze(deptID).get(0)==1)
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
	

	@RequestMapping(value = "/edituser", params="buttonComId", method = RequestMethod.POST)
	public String editUserPage(@RequestParam(value = "buttonComId") Integer buttonComId, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer id = us.getUser(userName).getId();
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
		
				
		List<Team> teamlistt = ts.getAllTeams();
		
				
		Integer ballstogive = user.getBall().getBallsToGive();
		for (int i=0; i<rolelistt.size(); i++){
			if (rolelistt.get(i).getRole().equals("superuser") || rolelistt.get(i).getRole().equals("admin"))
				rolelistt.remove(i);
		}
		for (int i=0; i<teamlistt.size(); i++){
			if (teamlistt.get(i).getId() == teamlistt.get(i).getId())
				teamlistt.remove(i);
		}
		if(user.getBall().getLocked()==1)
			model.addAttribute("checked", true);
		else
			model.addAttribute("checked", false);
		
		model.addAttribute("user", user);
		model.addAttribute("rolelistt", rolelistt);
		model.addAttribute("id", id);
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
		
		for (Department d:deptlistt){
			if (us.getUserId(buttonComId).getId().equals(d.getDeptLeaderId())){
				String name = ds.getDeptName(d.getDeptId());
				ds.editDepartment(d.getDeptId(), name, us.getUserWithRole(rs.getRoleId("superuser").get(0)).get(0).getId());				
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
		ModelAndView modelAndView = new ModelAndView("settings");
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
	@RequestMapping(value = "/userEdited", params="delete", method = RequestMethod.POST)
	public String userRemoved1(
			@RequestParam("user_id") Integer user_id, Model model){
			model.addAttribute("delete", user_id);
			return "redirect:/userRemoved";
	}
	@RequestMapping(value = "/userEdited", params="save", method = RequestMethod.POST)
	public ModelAndView userEdited(

			@RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname,
			@RequestParam(value = "login") String login, @RequestParam(value = "mail") String mail,
			@RequestParam(value = "role") String role,
			@RequestParam(value = "team") String team, @RequestParam(value = "balls") Integer balls,
			@RequestParam(value = "extraMoney") Double money, 
			@RequestParam(value = "userIds", required = false, defaultValue = "0") Integer[] isLocked,
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
		
		Integer locked;
		Integer creator_id;
		Integer ballsForComment;
		if(isLocked[0].equals(1)){
			locked = 1;
			List<Comment> commentsList = cs.getCommentsYouGave(user_id);
			if(commentsList!=null)
			for(int i=0;i<commentsList.size();i++){
				creator_id = commentsList.get(i).getCreatorId();
				ballsForComment = commentsList.get(i).getBallsPerCom();
				us.setBallsAfterCommentDelete(creator_id, ballsForComment, user_id);
				cs.editCommentBalls(commentsList.get(i).getComId());
			}
		}
		else
		{
			locked = 0;
		}

		
		
		bs.editLocked(balls_id, locked);
		bs.editCach(balls_id, money);
		bs.editBallsToGive(balls_id, balls);
		rus.editRole(user_id, roleID);
		us.editUser(user_id, name, surname, login, mail, roleID, deptID, teamID);
		ModelAndView modelAndView = new ModelAndView("redirect:/users");

		return modelAndView;
	}
}
