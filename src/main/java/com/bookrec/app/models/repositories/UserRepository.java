package com.bookrec.app.models.repositories;

import org.springframework.data.repository.CrudRepository;

import com.bookrec.app.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
