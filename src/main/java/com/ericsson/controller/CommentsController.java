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

import com.ericsson.model.Comment;
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
public class CommentsController {


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
		Integer ifNull=0;
		Integer roleId=us.getUser(userName).getRole().getId();
		if (userIds.length == 0) {
			ifNull=1;
			model.addAttribute("ifNull",ifNull);
			if(roleId==3)
			{
				return "redirect:/inside";
			}
			if(roleId==1)
			{
				return "redirect:/adminview";
			}
			
			
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

	@RequestMapping(value = "/commentAdded", method = RequestMethod.POST)
	public ModelAndView commentAdded(
			@RequestParam(value = "message1") ArrayList<String> message1List1,
			@RequestParam(value = "message2") ArrayList<String> message2List2,
			@RequestParam(value = "ballsNumber") ArrayList<Integer> ballsNumberList,
			@ModelAttribute("userList") ArrayList<User> userList) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Integer user_id = us.getUser(userName).getId();
		ArrayList<String> message1List = new ArrayList<String>();
		ArrayList<String> message2List = new ArrayList<String>();
		for(int k = 0;k<message1List1.size();k++){
			message1List.add(message1List1.get(k).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
		for(int l =0;l<message2List2.size();l++){
			message2List.add(message2List2.get(l).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
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
			ModelAndView modelAndView = new ModelAndView();
		
			modelAndView.addObject("success", true);
			modelAndView.setViewName("redirect:/success-login");
			return modelAndView;

		}
	}

	@RequestMapping(value = "/commentAdded", params = "addMore", method = RequestMethod.POST)
	public String addMoreUsers(@ModelAttribute("userList") ArrayList<User> userList, Model model,

			@RequestParam(value = "message1", defaultValue="") ArrayList<String> message1List1,
			@RequestParam(value = "message2", defaultValue="") ArrayList<String> message2List2,
			@RequestParam(value = "ballsNumber", defaultValue="0") ArrayList<Integer> ballsNumberList) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<String> message1List = new ArrayList<String>();
		ArrayList<String> message2List = new ArrayList<String>();
		for(int k = 0;k<message1List1.size();k++){
			message1List.add(message1List1.get(k).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
		for(int l =0;l<message2List2.size();l++){
			message2List.add(message2List2.get(l).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();
		Integer id1 = us.getUser(userName).getId();

		List<Team> teamlistt = ts.getAllTeams();

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
		model.addAttribute("teamlistt", teamlistt);
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
			allMess1s = allMess1s.trim().replaceAll(" +", " ").replace("\n", "").replace("\r", "");
			allMess2s = allMess2s.trim().replaceAll(" +", " ").replace("\n", "").replace("\r", "");
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();

		List<Team> teamlistt = ts.getAllTeams();


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
		model.addAttribute("teamlistt", teamlistt);
		return "redirect:/moreComments2";
	}

	@RequestMapping(value = "/moreComments2", method = RequestMethod.GET)
	public String moreCommentsPageGet2(@ModelAttribute("userList") ArrayList<User> userList,
			@RequestParam(value="message1List", defaultValue="") ArrayList<String> message1List1,
			@RequestParam(value="message2List", defaultValue="") ArrayList<String> message2List2,
			@RequestParam(value="ballsNumberList", defaultValue="0") ArrayList<Integer> ballsNumberList,
			Model model) {
		ArrayList<String> message1List = new ArrayList<String>();
		ArrayList<String> message2List = new ArrayList<String>();
		for(int k = 0;k<message1List1.size();k++){
			message1List.add(message1List1.get(k).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
		for(int l =0;l<message2List2.size();l++){
			message2List.add(message2List2.get(l).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Double> money = sett.getMoney(1);
		Double moneyValue = money.get(0);
		List<Long> ballValue2List = cs.getBallValue2();
		int ballValue2 = ((Long) ballValue2List.get(0)).intValue();
		Double wynik = (double) (moneyValue / ballValue2);
		wynik = sett.round(wynik, 2);
		String login = us.getUser(userName).getLogin();

		List<Team> teamlistt = ts.getAllTeams();

		Integer kulki = us.getUser(userName).getBall().getBallsToGive();
		
		model.addAttribute("money", wynik);
		model.addAttribute("kule", kulki);
		model.addAttribute("login", login);
		model.addAttribute("message1List", message1List);
		model.addAttribute("message2List", message2List);
		model.addAttribute("ballsNumberList", ballsNumberList);
		model.addAttribute("teamlistt", teamlistt);
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
		message1 = message1.trim().replaceAll(" +", " ").replace("\n", "").replace("\r", "");
		message2 = message2.trim().replaceAll(" +", " ").replace("\n", "").replace("\r", "");
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
		modelAndView.addObject("edited", true);
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
		ArrayList<String> message1List = new ArrayList<String>();
		ArrayList<String> message2List = new ArrayList<String>();
		for(int k = 0;k<messages1.size();k++){
			message1List.add(messages1.get(k).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
		for(int l =0;l<messages2.size();l++){
			message2List.add(messages2.get(l).trim().replaceAll(" +", " ").replace("\n", "").replace("\r", ""));
		}
				
		for(int i=0;i<comId.size();i++)
		{
			cs.editComment(message1List.get(i), message2List.get(i), balls.get(i), comId.get(i));
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		modelAndView.addObject("edited",true);
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
		cs.removeComment(commId);
		ModelAndView modelAndView = new ModelAndView("redirect:/success-login");
		modelAndView.addObject("removed", true);
		return modelAndView;
	}
}
