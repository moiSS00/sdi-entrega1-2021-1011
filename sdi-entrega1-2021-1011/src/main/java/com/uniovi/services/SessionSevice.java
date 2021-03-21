package com.uniovi.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.uniovi.entities.User;

@Service
@SessionScope
public class SessionSevice {
	
	@Autowired
	private HttpSession httpSession; 
	
	@Autowired
	private UsersService usersService; 
	
	public void loadRegisteredUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User registeredUser = usersService.getUserByEmail(email); 
		httpSession.setAttribute("registeredUser", registeredUser);
		addBuyErrorToSession(false);
	}
	
	public void addBuyErrorToSession(boolean flag) {
		httpSession.setAttribute("buyError", flag);
	}
	
	public void addToSession(String clave, Object value) {
		httpSession.setAttribute(clave, value);
	}
	
	public Object get(String clave) {
		return httpSession.getAttribute(clave);
	}
}