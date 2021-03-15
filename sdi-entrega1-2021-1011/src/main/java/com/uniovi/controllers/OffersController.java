package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Offer;
import com.uniovi.services.OffersService;

@Controller
public class OffersController {
	
	@Autowired
	private OffersService offersService; 

	@RequestMapping(value = "/offer/list", method = RequestMethod.GET)
	public String getList(Model model) {
		model.addAttribute(offersService.getOffers()); 
		return "offer/list"; 
	}
	
	@RequestMapping(value = "/offer/add", method = RequestMethod.GET)
	public String setOffer() {
		return "offer/add"; 
	}
	
	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setOffer(@ModelAttribute Offer offer) {
		offersService.addOffer(offer);
		return "redirect:/offer/list";
	}
}