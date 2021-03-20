package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

	@Query("SELECT u from User u WHERE u.role not like 'ROLE_ADMIN' ORDER BY u.id ASC")
	List<User> findAllUsersxceptAdmin();

	User findByEmail(String email);

}
