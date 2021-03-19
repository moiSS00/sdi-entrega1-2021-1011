package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

@Repository
public interface OffersRepository extends CrudRepository<Offer,Long> {
	
	List<Offer> findAllByOwner(User owner); 
	
	List<Offer> findAllByBuyer(User buyer);

}
