package com.ericsson.controller;

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
public class DeptController {

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
		sett.addNewSettings(deptID);
		ts.addTeam("no-team", leaderID, deptID);
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
	
		return modelAndView;
	}

	//Delete team
	@RequestMapping(value = "/editdept", params="delete", method = RequestMethod.POST)
	public ModelAndView deptRemoved(
		@RequestParam(value = "delete") Integer deptDelId){
		List<User> listt = us.getAllUsersForSuperUser();

		ModelAndView modelAndView = new ModelAndView();
		String name = ds.getDeptName(deptDelId);
		ds.editDepartment(deptDelId, name, 4);
		for(int i=0;i<listt.size();i++){
			if (listt.get(i).getDept().getDeptId().equals(deptDelId))
				us.removeUser(listt.get(i).getId());
		}

			ds.removeDept(deptDelId);
			sett.deleteSettings(deptDelId);
		modelAndView.setViewName("redirect:/success-login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/editdept", method = RequestMethod.POST)
	public String deptEdited(
			@RequestParam(value = "buttonComId") Integer dept_id, 
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
		Department dept = ds.getDept(dept_id);
		Integer kulki = us.getUser(userName).getBall().getBallsToGive();
		Integer user_id = ds.getDept(dept_id).getDeptLeaderId();

		String leaderName = us.getUserId(user_id).getName();
		String leaderSurname = us.getUserId(user_id).getSurname();
		String leaderLogin = us.getUserId(user_id).getLogin();
		
		
		List<User> listt = us.getAllUsersForSuperUser();
		
		Integer su_id = rs.getRoleId("superuser").get(0).getId();
		for (int i=0; i<listt.size(); i++){
			if (listt.get(i).getRole().getId().equals(su_id)){
				listt.remove(i);
			}
		}
		
		model.addAttribute("dept", dept);
		model.addAttribute("leaderName", leaderName);
		model.addAttribute("leaderSurname", leaderSurname);
		model.addAttribute("leaderLogin", leaderLogin);
		model.addAttribute("dept_id", dept_id);
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		model.addAttribute("listt", listt);
		return "editdept";
	}
	
	@RequestMapping(value = "/deptEdited", params="save", method = RequestMethod.POST)
	public ModelAndView deptEdited(

			@RequestParam(value = "name") String name, 
			@RequestParam(value = "user") String user,
			@RequestParam(value = "dept_id") Integer dept_id,
			Model model

	) {
		String[] words = user.split(" ");
		String leader = words[2];
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String login = us.getUser(userName).getLogin();
		
		Integer leaderID = us.getUser(leader).getId();
		Integer previous_leader_id = ds.getDept(dept_id).getDeptLeaderId();
		ds.editDepartment(dept_id, name, leaderID);
		
		us.editRoleID(previous_leader_id, rs.getRoleId("user").get(0).getId());
		us.editRoleID(leaderID, rs.getRoleId("admin").get(0).getId());
		rus.editRole(previous_leader_id, rs.getRoleId("user").get(0).getId());
		rus.editRole(leaderID, rs.getRoleId("admin").get(0).getId());
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");

		model.addAttribute("login", login);
		return modelAndView;
	}
}
