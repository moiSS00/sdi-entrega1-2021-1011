package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Offer;

@Component
public class AddOfferFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		return Offer.class.equals(aClass); 
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Offer offer = (Offer) target; 
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Error.empty");

		
		if (offer.getTitle().length() < 5 || offer.getTitle().length() > 20) {
			errors.rejectValue("title", "Error.title.length");
		}
		
		if (offer.getDescription().length() < 5 || offer.getDescription().length() > 50) {
			errors.rejectValue("description", "Error.description.length");
		} 
		
		
		if(offer.getPrice() != null) {
			if (offer.getPrice() < 0) {
				errors.rejectValue("price", "Error.price.positive");
			}
		}

				
	}

}
