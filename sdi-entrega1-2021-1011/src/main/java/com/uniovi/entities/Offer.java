package com.uniovi.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Offer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	private String description;
	private LocalDate creationDate;
	private Double price;
	private Boolean buyed; 
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner; 
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyer; 

	public Offer(String title, String description,Double price, User owner) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.owner = owner; 
		this.creationDate = LocalDate.now();
	}

	public Offer() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Boolean getBuyed() {
		return buyed;
	}

	public void setBuyed(Boolean buyed) {
		this.buyed = buyed;
	}

	
}
