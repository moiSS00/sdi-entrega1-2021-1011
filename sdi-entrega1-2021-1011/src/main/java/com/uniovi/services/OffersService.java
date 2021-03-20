package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;

@Service
public class OffersService {
	
	@Autowired
	private OffersRepository offersRepository;
	
	
	public List<Offer> getOffers() {
		List<Offer> offers = new ArrayList<Offer>(); 
		offersRepository.findAll().forEach(offers::add);
		return offers; 
	}
	
	public List<Offer> getOffersOfUser(User user) {
		return offersRepository.findAllByOwner(user);
	}
	
	public List<Offer> getPurchasedOffers(User user) {
		return offersRepository.findAllByBuyer(user); 
	}
		
	public void addOffer(Offer offer) {
		offersRepository.save(offer); 
	}
	
	public void deleteOffer(Long id) {
		offersRepository.deleteById(id);
	}

}
