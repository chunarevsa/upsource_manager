package com.rtkit.upsource_manager.repositories;


import com.rtkit.upsource_manager.entities.prticipant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole (String role); 
} 
