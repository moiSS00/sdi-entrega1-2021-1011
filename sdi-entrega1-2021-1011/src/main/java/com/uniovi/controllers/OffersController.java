package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.SessionSevice;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddOfferFormValidator;

@Controller
public class OffersController {

	@Autowired
	private OffersService offersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private SessionSevice sessionService;

	@Autowired
	private AddOfferFormValidator addOfferFormValidator;

	
	@RequestMapping(value = "/offer/searchList", method = RequestMethod.GET)
	public String getOwnedList(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText,
			@RequestParam(required = false) Long idToPay) {
		
		//Pagamos la oferta y indicamos en el modelo si hubo error al pagarla
		if (idToPay != null) {
			Offer offerToPay = offersService.getOfferById(idToPay); 
			model.addAttribute("error", !offersService.buyOffer(offerToPay)); 
		}
		
		//Recargamos la informacion del usuario 
		sessionService.loadRegisteredUser();
		
		
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		model.addAttribute("searchText", "");
		if (searchText != null && !searchText.isEmpty()) {
			model.addAttribute("searchText", searchText);
			offers = offersService.searchOffersByTitle(pageable, searchText);
		}
		else {
			offers = offersService.getAvailableOffers(pageable);
		}
		model.addAttribute("offersList", offers.getContent());
		model.addAttribute("page", offers);
		return "offer/searchList";
	}
	

	@RequestMapping(value = "/offer/ownedList", method = RequestMethod.GET)
	public String getOwnedList(Model model, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		model.addAttribute("offersList", offersService.getOffersOfUser(user));
		return "offer/ownedList";
	}

	@RequestMapping(value = "/offer/purchasedList", method = RequestMethod.GET)
	public String getPurchasedList(Model model, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		model.addAttribute("offersList", offersService.getPurchasedOffers(user));
		return "offer/purchasedList";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.GET)
	public String setOffer(Model model) {
		model.addAttribute("offer", new Offer());
		return "offer/add";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setOffer(@ModelAttribute @Validated Offer offer, Principal principal, BindingResult result) {
		addOfferFormValidator.validate(offer, result);
		System.out.println(offer.getDescription());
		if (result.hasErrors()) {
			return "offer/add";
		}
		String email = principal.getName();
		User owner = usersService.getUserByEmail(email);
		offer.setOwner(owner);
		offersService.addOffer(offer);
		return "redirect:/offer/ownedList";
	}

	@RequestMapping(value = "/offer/delete/{id}", method = RequestMethod.GET)
	public String deleteOffer(@PathVariable Long id) {
		offersService.deleteOffer(id);
		return "redirect:/offer/ownedList";
	}


}