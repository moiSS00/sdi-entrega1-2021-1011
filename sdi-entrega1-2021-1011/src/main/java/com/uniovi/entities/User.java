package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String email; 
	
	private String name; 
	private String lastName;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Offer> ownOffers; 
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
	private Set<Offer> purchasedOffers; 
	
	public User(String email, String name, String lastName) {
		super(); 
		this.email = email; 
		this.name = name; 
		this.lastName = lastName; 
	}
	
	public User() {
		
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Offer> getOwnOffers() {
		return ownOffers;
	}

	public void setOwnOffers(Set<Offer> ownOffers) {
		this.ownOffers = ownOffers;
	}

	public Set<Offer> getPurchasedOffers() {
		return purchasedOffers;
	}

	public void setPurchasedOffers(Set<Offer> purchasedOffers) {
		this.purchasedOffers = purchasedOffers;
	}

	

}
