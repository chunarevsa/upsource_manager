package com.rtkit.upsource_manager.services;

import com.rtkit.upsource_manager.entities.user.Role;
import com.rtkit.upsource_manager.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	/**
	 * Получение списка всех ролей
	 * @return
	 */
	public Collection<Role> findAll() {
		return roleRepository.findAll();
	}
}
