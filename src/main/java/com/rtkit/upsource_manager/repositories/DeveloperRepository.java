package com.rtkit.upsource_manager.repositories;

import com.rtkit.upsource_manager.entities.developer.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

//	Boolean existsByEmail(String email);
//
//	Optional<User> findByEmail(String email);
//
    Optional<Developer> findByLogin (String login);
//
//	List<User> findByActive (Boolean acttive);
//
    Boolean existsByLogin(String login);
} 
