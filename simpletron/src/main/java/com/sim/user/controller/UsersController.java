package com.sim.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sim.user.User;
import com.sim.user.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUp() {
		return "users/signUp";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(User user) throws Exception {
		userService.insert(user);
		return "users/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "users/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String id, String passwd, HttpSession session) throws Exception {
		User loginUser = userService.login(id, passwd);
		if (loginUser != null) {
			session.setAttribute("check", loginUser);
			return "redirect:/";
		} else {
			return "users/login";
		}
	}

	@RequestMapping(value = "/editAccount", method = RequestMethod.GET)
	public String editAccount() {
		return "users/editAccount";
	}

	@RequestMapping(value = "/editAccount", method = RequestMethod.POST)
	public String editAccount(User user, HttpSession session) throws Exception {
		User loginUser = (User) session.getAttribute("check");
		String id = loginUser.getId(); // 세션에 저장된 사용자 정보로부터 이메일을 알아낸다.

		if (user.getNick() == null) {
			user.setNick(loginUser.getNick());
		}

		user.setId(id);
		int check = userService.modify(user);
		if (check == 1) {
			session.setAttribute("check", user);
		}

		return "users/changePasswd";

	}

	@RequestMapping(value = "/changePasswd", method = RequestMethod.GET)
	public String changePasswd() {
		return "users/changePasswd";
	}

	@RequestMapping(value = "/changePasswd", method = RequestMethod.POST)
	public String changePasswd(String passwd, HttpSession session) throws Exception {
		String id = ((User) session.getAttribute("check")).getId();

		User user = new User();
		user.setId(id);
		user.setPasswd(passwd);

		userService.changePasswd(user);

		return "users/confirm";
	}

	@RequestMapping(value = "/bye", method = RequestMethod.GET)
	public String bye(HttpSession session) {
		User user = (User) session.getAttribute("check");
		userService.byeMember(user);
		session.invalidate();

		return "users/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("check");

		return "users/login";

	}
}