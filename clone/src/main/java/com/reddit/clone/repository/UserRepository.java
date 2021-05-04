package com.reddit.clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.clone.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUserName(String userName);

	User findByEmail(String email);
	
}
