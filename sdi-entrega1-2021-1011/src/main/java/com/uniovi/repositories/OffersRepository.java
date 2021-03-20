package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

@Repository
public interface OffersRepository extends CrudRepository<Offer, Long> {

	List<Offer> findAllByOwner(User owner);

	List<Offer> findAllByBuyer(User buyer);

	@Query("SELECT o from Offer o WHERE o.owner.email != ?1 AND LOWER(o.title) like LOWER(?2) ORDER BY o.creationDate DESC")
	Page<Offer> searchByTitle(Pageable pageable, String email, String title);

	@Query("SELECT o from Offer o WHERE o.owner.email != ?1 ORDER BY o.creationDate DESC")
	Page<Offer> findAllAvailableOffers(Pageable pageable, String email);

}
