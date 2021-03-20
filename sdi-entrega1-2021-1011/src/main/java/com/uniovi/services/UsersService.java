package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RolesService rolesService; 

	public List<User> getAllUsersxceptAdmin() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAllUsersxceptAdmin().forEach(users::add);
		return users;
	}

	public void addUser(User user, boolean encode) {
		if (encode) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void removeUsers(List<Long> ids) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User registeredUser = getUserByEmail(email); 
		if(registeredUser.getRole().equals(rolesService.getRoles()[1])) {
			for(Long id : ids) {
				usersRepository.deleteById(id);
			}
		}
	}
	
	
	

}
