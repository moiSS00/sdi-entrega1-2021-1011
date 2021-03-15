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
	
	@PostConstruct
	public void init() {
		
		//Creación de 5 usuarios y se añaden a la base de datos
		User user1 = new User("correo1@prueba.com", "Nombre1", "Apellido1"); 
		user1.setPassword("123456");
		User user2 = new User("correo2@prueba.com", "Nombre2", "Apellido2");
		user2.setPassword("123456");
		User user3 = new User("correo3@prueba.com", "Nombre3", "Apellido3"); 
		user3.setPassword("123456");
		User user4 = new User("correo4@prueba.com", "Nombre4", "Apellido4");
		user4.setPassword("123456");
		User user5 = new User("correo5@prueba.com", "Nombre5", "Apellido5");
		user5.setPassword("123456");
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		
		//Creación de 3 ofertas para cada usuario y se añaden a la base de datos
		Offer offer1_1 = new Offer("Título1.1", "Descripción1.1", 1.1,user1); 
		Offer offer1_2 = new Offer("Título1.2", "Descripción1.2", 1.2,user1); 
		Offer offer1_3 = new Offer("Título1.3", "Descripción1.3", 1.3,user1);
		offersService.addOffer(offer1_1);
		offersService.addOffer(offer1_2);
		offersService.addOffer(offer1_3);
		
		Offer offer2_1 = new Offer("Título2.1", "Descripción2.1", 2.1,user2); 
		Offer offer2_2 = new Offer("Título2.2", "Descripción2.2", 2.2,user2); 
		Offer offer2_3 = new Offer("Título2.3", "Descripción2.3", 2.3,user2);
		offersService.addOffer(offer2_1);
		offersService.addOffer(offer2_2);
		offersService.addOffer(offer2_3);
		
		Offer offer3_1 = new Offer("Título3.1", "Descripción3.1", 3.1,user3); 
		Offer offer3_2 = new Offer("Título3.2", "Descripción3.2", 3.2,user3); 
		Offer offer3_3 = new Offer("Título3.3", "Descripción3.3", 3.3,user3);
		offersService.addOffer(offer3_1);
		offersService.addOffer(offer3_2);
		offersService.addOffer(offer3_3);
		
		Offer offer4_1 = new Offer("Título4.1", "Descripción4.1", 4.1,user4); 
		Offer offer4_2 = new Offer("Título4.2", "Descripción4.2", 4.2,user4); 
		Offer offer4_3 = new Offer("Título4.3", "Descripción4.3", 4.3,user4);
		offersService.addOffer(offer4_1);
		offersService.addOffer(offer4_2);
		offersService.addOffer(offer4_3);
		
		Offer offer5_1 = new Offer("Título5.1", "Descripción5.1", 5.1,user5); 
		Offer offer5_2 = new Offer("Título5.2", "Descripción5.2", 5.2,user5); 
		Offer offer5_3 = new Offer("Título5.3", "Descripción5.3", 5.3,user5);
		offersService.addOffer(offer5_1);
		offersService.addOffer(offer5_2);
		offersService.addOffer(offer5_3);
		
		Set<Offer> user1Offers = new HashSet<Offer>(); 
		user1Offers.add(offer1_1);
		user1Offers.add(offer1_2);
		user1Offers.add(offer1_3); 
					
		Set<Offer> user2Offers = new HashSet<Offer>(); 
		user2Offers.add(offer2_1);
		user2Offers.add(offer2_2);
		user2Offers.add(offer2_3); 
		
		Set<Offer> user3Offers = new HashSet<Offer>();
		user3Offers.add(offer3_1);
		user3Offers.add(offer3_2);
		user3Offers.add(offer3_3); 
				
		Set<Offer> user4Offers = new HashSet<Offer>();
		user4Offers.add(offer4_1);
		user4Offers.add(offer4_2);
		user4Offers.add(offer4_3);
				
		Set<Offer> user5Offers = new HashSet<Offer>();
		user5Offers.add(offer5_1);
		user5Offers.add(offer5_2);
		user5Offers.add(offer5_3); 
		
		user1.setOwnOffers(user1Offers);
		user2.setOwnOffers(user2Offers);
		user3.setOwnOffers(user3Offers);
		user4.setOwnOffers(user4Offers);
		user5.setOwnOffers(user5Offers);
		
		//Cada usuario compra 2 ofertas
		Set<Offer> user1PurchasedOffers = new HashSet<Offer>();
		offer2_1.setBuyer(user1);
		offer3_1.setBuyer(user1);
		user1PurchasedOffers.add(offer2_1);
		user1PurchasedOffers.add(offer3_1); 
		
		Set<Offer> user2PurchasedOffers = new HashSet<Offer>();
		offer4_1.setBuyer(user2);
		offer5_1.setBuyer(user2);
		user2PurchasedOffers.add(offer4_1);
		user2PurchasedOffers.add(offer5_1); 
		
		Set<Offer> user3PurchasedOffers = new HashSet<Offer>();
		offer1_1.setBuyer(user3);
		offer1_2.setBuyer(user3);
		user3PurchasedOffers.add(offer1_1);
		user3PurchasedOffers.add(offer1_2); 
		
		Set<Offer> user4PurchasedOffers = new HashSet<Offer>();
		offer1_3.setBuyer(user4);
		offer5_2.setBuyer(user4);
		user4PurchasedOffers.add(offer1_3);
		user4PurchasedOffers.add(offer5_2); 
		
		Set<Offer> user5PurchasedOffers = new HashSet<Offer>();
		offer4_3.setBuyer(user5);
		offer3_2.setBuyer(user5);
		user5PurchasedOffers.add(offer4_3);
		user5PurchasedOffers.add(offer3_2);
		
		user1.setPurchasedOffers(user1PurchasedOffers); 
		user2.setPurchasedOffers(user2PurchasedOffers);
		user3.setPurchasedOffers(user3PurchasedOffers);
		user4.setPurchasedOffers(user4PurchasedOffers);
		user5.setPurchasedOffers(user5PurchasedOffers);
		
	}
}
