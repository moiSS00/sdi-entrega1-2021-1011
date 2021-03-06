package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;

	private String name;
	private String lastName;
	private Double amount = 100.0;  
	private String password;
	private String role;

	@Transient
	private String passwordConfirm;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Offer> ownOffers = new HashSet<Offer>();

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
	private Set<Offer> purchasedOffers = new HashSet<Offer>();

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		amount = Math.round(amount*100.0)/100.0;
		this.amount = amount;
	}
	
}
