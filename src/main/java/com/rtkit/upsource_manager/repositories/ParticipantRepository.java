package com.rtkit.upsource_manager.repositories;


import com.rtkit.upsource_manager.entities.prticipant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

//	Boolean existsByEmail(String email);
//
//	Optional<User> findByEmail(String email);
//
    Optional<Participant> findByLogin (String login);
//
//	List<User> findByActive (Boolean acttive);
//
    Boolean existsByLogin(String login);
} 
