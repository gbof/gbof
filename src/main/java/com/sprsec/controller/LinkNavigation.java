package com.sprsec.controller;

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

import com.sprsec.dao.CommentDAO;
import com.sprsec.model.Comment;
import com.sprsec.model.User;
import com.sprsec.service.CommentService;
import com.sprsec.service.UserService;

@Controller
@SessionAttributes("userList")
public class LinkNavigation {

	@Autowired
	private UserService us;

	@Autowired
	private CommentService cs;

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

		List<Integer> idList = new ArrayList<Integer>();
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < userIds.length; i++) {
			userList.add(us.getUserId(userIds[i]));
			idList.add(userIds[i]);
		}
		model.addAttribute("idList", idList);
		model.addAttribute("userList", userList);
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
			ModelAndView modelAndView = new ModelAndView("redirect:/inside");
			System.out.println("================== NIE MASZ JUZ KULEK");
			modelAndView.addObject("success-comment", false);
			return modelAndView;
		} else {
			for (int i = 0; i < message1List.length; i++) {
				if (us.getUser(userName).getBall().getBallsToGive() < ballsNumberList[i]) {
					System.out.println("================== NIE MASZ TYLE KULEK");
					ModelAndView modelAndView = new ModelAndView("redirect:/inside");
					modelAndView.addObject("success-comment", false);
					return modelAndView;
				} else {
					Integer commentToUserId = userList.get(i).getId();
					cs.addComment(message1List[i], message2List[i], ballsNumberList[i], user_id, commentToUserId);
					us.setBallsAfterComment(user_id, ballsNumberList[i]);
				};
				};
				ModelAndView modelAndView = new ModelAndView("redirect:/inside");
				System.out.println("================== DODANO KOMENTARZ");
				modelAndView.addObject("success-comment", true);
				return modelAndView;
		
		}
	}

	/*
	 * @RequestMapping(value="/inside", method=RequestMethod.GET) public
	 * ModelAndView insidePage() { return new ModelAndView("inside2"); }
	 */

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView testPage() {

		ModelAndView lista = new ModelAndView();
		List<User> list = us.getAllUsers();
		lista.addObject("list", list);
		lista.setViewName("test");
		return lista;
	}

}