package com.rtkit.upsource_manager.repositories;

import com.rtkit.upsource_manager.entities.developer.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByLogin (String login);

    Boolean existsByLogin(String login);
}
