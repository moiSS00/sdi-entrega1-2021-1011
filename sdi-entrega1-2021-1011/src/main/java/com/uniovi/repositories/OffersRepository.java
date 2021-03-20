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

	@Query("SELECT o from Offer o WHERE o.title like ?1 ORDER BY o.creationDate DESC")
	Page<Offer> searchByTitle(Pageable pageable, String title);
	
	Page<Offer> findAll(Pageable pageable); 

}
