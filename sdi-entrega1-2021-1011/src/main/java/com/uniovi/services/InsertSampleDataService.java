package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private OffersService offersService;

	@Autowired
	private RolesService rolesService;

	@PostConstruct
	public void init() {
		
		//Creación de 5 usuarios
		User user1 = new User("correo1@prueba.com", "Nombre1", "Apellido1"); 
		user1.setPassword("1234567");
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("correo2@prueba.com", "Nombre2", "Apellido2");
		user2.setPassword("1234567");
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("correo3@prueba.com", "Nombre3", "Apellido3"); 
		user3.setPassword("1234567");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("correo4@prueba.com", "Nombre4", "Apellido4");
		user4.setPassword("1234567");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("admin@email.com", "AdminName", "AdminLastName");
		user5.setPassword("admin");
		user5.setRole(rolesService.getRoles()[1]);
		
		//Creacion de las ofertas
		Offer offer1_1 = new Offer("Título1.1", "Descripción1.1", 1.1,user1);
		Offer offer1_2 = new Offer("Título1.2", "Descripción1.2", 1.2,user1); 
		Offer offer1_3 = new Offer("Título1.3", "Descripción1.3", 1.3,user1);
		
		Offer offer2_1 = new Offer("Título2.1", "Descripción2.1", 2.1,user2); 
		Offer offer2_2 = new Offer("Título2.2", "Descripción2.2", 2.2,user2); 
		Offer offer2_3 = new Offer("Título2.3", "Descripción2.3", 2.3,user2);
		
		Offer offer3_1 = new Offer("Título3.1", "Descripción3.1", 3.1,user3); 
		Offer offer3_2 = new Offer("Título3.2", "Descripción3.2", 3.2,user3); 
		Offer offer3_3 = new Offer("Título3.3", "Descripción3.3", 3.3,user3);
		
		Offer offer4_1 = new Offer("Título4.1", "Descripción4.1", 4.1,user4); 
		Offer offer4_2 = new Offer("Título4.2", "Descripción4.2", 4.2,user4); 
		Offer offer4_3 = new Offer("Título4.3", "Descripción4.3", 4.3,user4);
		
		Offer offer5_1 = new Offer("Título5.1", "Descripción5.1", 5.1,user5); 
		Offer offer5_2 = new Offer("Título5.2", "Descripción5.2", 5.2,user5); 
		Offer offer5_3 = new Offer("Título5.3", "Descripción5.3", 200.0,user5);
		
		
		//Inicializo colecciones necesarias
		Set<Offer> user1OwnedOffers = new HashSet<Offer>();
		user1OwnedOffers.add(offer1_1); 
		user1OwnedOffers.add(offer1_2); 
		user1OwnedOffers.add(offer1_3); 
		
		Set<Offer> user2OwnedOffers = new HashSet<Offer>();
		user2OwnedOffers.add(offer2_1); 
		user2OwnedOffers.add(offer2_2); 
		user2OwnedOffers.add(offer2_3); 
		
		Set<Offer> user3OwnedOffers = new HashSet<Offer>();
		user3OwnedOffers.add(offer3_1); 
		user3OwnedOffers.add(offer3_2); 
		user3OwnedOffers.add(offer3_3); 
		
		Set<Offer> user4OwnedOffers = new HashSet<Offer>();
		user4OwnedOffers.add(offer4_1); 
		user4OwnedOffers.add(offer4_2); 
		user4OwnedOffers.add(offer4_3); 
		
		Set<Offer> user5OwnedOffers = new HashSet<Offer>();
		user5OwnedOffers.add(offer5_1); 
		user5OwnedOffers.add(offer5_2); 
		user5OwnedOffers.add(offer5_3); 
		
		//Asigno colecciones 
		user1.setOwnOffers(user1OwnedOffers);
		user2.setOwnOffers(user2OwnedOffers);
		user3.setOwnOffers(user3OwnedOffers);
		user4.setOwnOffers(user4OwnedOffers);
		user5.setOwnOffers(user5OwnedOffers);
		
		//Añadimos los usuarios junto con sus ofertas a la base de datos	
		usersService.addUser(user1, true);
		usersService.addUser(user2, true);
		usersService.addUser(user3, true);
		usersService.addUser(user4, true);
		usersService.addUser(user5, true);	

		//Se compran algunas ofertas 
		buy(user1, offer2_1);
		buy(user1, offer3_1);
		
		buy(user2, offer4_1);
		buy(user2, offer5_1);
		
		buy(user3, offer1_1);
		buy(user3, offer1_2);
		
		buy(user4, offer1_3);
		buy(user4, offer5_2);
		
		buy(user5, offer4_3);
		buy(user5, offer3_2);
	}
	
	private void buy(User user, Offer offer) {
		offer.setBuyer(user);
		Set<Offer> purchasedOffers = user.getPurchasedOffers(); 
		purchasedOffers.add(offer); 
		user.setPurchasedOffers(purchasedOffers);
		offersService.addOffer(offer);
		usersService.addUser(user, false);
	}


}
