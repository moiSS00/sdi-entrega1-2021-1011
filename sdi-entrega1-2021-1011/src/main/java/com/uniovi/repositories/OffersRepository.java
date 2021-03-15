package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Offer;

@Repository
public interface OffersRepository extends CrudRepository<Offer,Long> {

}
