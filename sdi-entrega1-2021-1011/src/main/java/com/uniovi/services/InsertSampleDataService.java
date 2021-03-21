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
		User user1 = new User("correo1@email.com", "Nombre1", "Apellido1"); 
		user1.setPassword("1234567");
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("correo2@email.com", "Nombre2", "Apellido2");
		user2.setPassword("1234567");
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("correo3@email.com", "Nombre3", "Apellido3"); 
		user3.setPassword("1234567");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("correo4@email.com", "Nombre4", "Apellido4");
		user4.setPassword("1234567");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("correo5@email.com", "Nombre5", "Apellido5");
		user5.setPassword("admin");
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("admin@email.com", "AdminName", "AdminLastName");
		user6.setPassword("admin");
		user6.setRole(rolesService.getRoles()[1]);
		
		//Creacion de las ofertas
		Offer offer1_1 = new Offer("Coche SEAT", "Coche SEAT con 500 Km.", 1500.0,user1);
		Offer offer1_2 = new Offer("Libro informática", "Libro 'Internet es maravilloso' de la editorial SA.", 10.50,user1); 
		Offer offer1_3 = new Offer("Portatil", "Procesador I7 y 8Gb de RAM.", 320.99,user1);
		
		Offer offer2_1 = new Offer("Ordenador fijo HP", "Con procesador AMD.", 400.21,user2); 
		Offer offer2_2 = new Offer("Pack material escolar", "Pack 5 rotuladores BIC.", 2.2,user2); 
		Offer offer2_3 = new Offer("Película", "Jurassic Park.", 2.3,user2);
		
		Offer offer3_1 = new Offer("Televisión 4K", "Para una buena tarde de Netflix.", 80.99,user3); 
		Offer offer3_2 = new Offer("Película molona", "Matrix.", 3.2,user3); 
		Offer offer3_3 = new Offer("Academina matemáticas", "Clases universidad y bachillerato.", 30.0,user3);
		
		Offer offer4_1 = new Offer("Coche León", "Tiene un ligero abollón en la puerta delantera izquierda.", 90.0,user4); 
		Offer offer4_2 = new Offer("Ordenador fijo personalizado", "Con tarjeta gráfica NVIDEA RTX 2600.", 320.0,user4); 
		Offer offer4_3 = new Offer("Coche BMW", "Sin usar. Esta nuevo.", 6000.0,user4);
		
		Offer offer5_1 = new Offer("Ratón oficina", "Ratón de uso diario inalámbrico.", 9.80,user5); 
		Offer offer5_2 = new Offer("Microfono", "Para hacer ASMRs.", 120.90,user5); 
		Offer offer5_3 = new Offer("Disco duro", "Disco duro de 500 Gb SSD.", 100.0,user5);
		
		
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
		usersService.addUser(user6, true);

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
