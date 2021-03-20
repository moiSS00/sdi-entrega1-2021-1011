package com.uniovi.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;

@Service
public class OffersService {

	@Autowired
	private OffersRepository offersRepository;

	@Autowired
	private UsersService usersService;

	public Page<Offer> getAvailableOffers(Pageable pageable) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Page<Offer> offers = offersRepository.findAllAvailableOffers(pageable, email);
		return offers;
	}

	public Page<Offer> searchOffersByTitle(Pageable pageable, String searchText) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		searchText = "%" + searchText + "%";
		offers = offersRepository.searchByTitle(pageable, email, searchText);
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

	public Offer getOfferById(Long id) {
		return offersRepository.findById(id).get();
	}

	public void deleteOffer(Long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (getOfferById(id).getOwner().getEmail().equals(email)) {
			offersRepository.deleteById(id);
		}
	}

	public boolean buyOffer(Offer offer) {

		// Obtenemos el usuario que esta autenticado en este momento
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User registeredUser = usersService.getUserByEmail(email);

		// Si tiene dinero suficiente
		if (registeredUser.getAmount() >= offer.getPrice()) {

			// Restamos la cantidad pertinente
			registeredUser.setAmount(registeredUser.getAmount() - offer.getPrice());

			// Asignamos al usuario autenticado como comprador de la oferta
			offer.setBuyer(registeredUser);
			Set<Offer> purchasedOffers = registeredUser.getPurchasedOffers();
			purchasedOffers.add(offer);
			registeredUser.setPurchasedOffers(purchasedOffers);

			// Guardamos los cambios realizados en la base de datos
			addOffer(offer);
			usersService.addUser(registeredUser, false);

			// Indicamos que se compro con exito
			return true;

		}

		// Indicamos que no se pudo comprar
		return false;
	}

}
