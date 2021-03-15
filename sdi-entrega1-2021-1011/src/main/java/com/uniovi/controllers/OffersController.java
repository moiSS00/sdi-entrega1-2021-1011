package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;

@Controller
public class OffersController {
	
	@Autowired
	private OffersService offersService; 
	
	@Autowired
	private UsersService usersService; 
	

	@RequestMapping(value = "/offer/privateList", method = RequestMethod.GET)
	public String getList(Model model, Principal principal) {
		String email = principal.getName();
		System.out.println("Email: " + email); 
		User user = usersService.getUserByEmail(email); 
		model.addAttribute(offersService.getOffersOfUser(user)); 
		return "offer/privateList"; 
	}
	
	@RequestMapping(value = "/offer/add", method = RequestMethod.GET)
	public String setOffer() {
		return "offer/add"; 
	}
	
	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setOffer(@ModelAttribute Offer offer) {
		offersService.addOffer(offer);
		return "redirect:/offer/privateList";
	}
}
