package com.uniovi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.SessionSevice;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private SessionSevice sessionService; 
	
	@Autowired
	private SignUpFormValidator signUpFormValidator; 

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute @Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user, true);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		sessionService.loadRegisteredUser();
		return "home";
	}
	
	
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String getUsersList(Model model) {
		model.addAttribute("usersList", usersService.getAllUsersxceptAdmin()); 
		return "user/list";
	}
		
	@RequestMapping(value = "/user/list/update/{ids}", method = RequestMethod.GET)
	public String updateUsersList(Model model, @PathVariable List<Long> ids) {
		usersService.removeUsers(ids);
		model.addAttribute("usersList", usersService.getAllUsersxceptAdmin()); 
		return "user/list :: tableUsers";
	}
	

}
