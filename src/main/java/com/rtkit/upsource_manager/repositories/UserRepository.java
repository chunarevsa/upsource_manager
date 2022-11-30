package com.rtkit.upsource_manager.repositories;


import com.rtkit.upsource_manager.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//	Boolean existsByEmail(String email);
//
//	Optional<User> findByEmail(String email);
//
//	Optional<User> findByUsername (String username);
//
//	List<User> findByActive (Boolean acttive);
//
//	Boolean existsByUsername(String username);
} 
