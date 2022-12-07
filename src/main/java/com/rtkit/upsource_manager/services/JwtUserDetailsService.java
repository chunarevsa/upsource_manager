package com.rtkit.upsource_manager.services;

import com.rtkit.upsource_manager.entities.developer.Developer;
import com.rtkit.upsource_manager.repositories.DeveloperRepository;
import com.rtkit.upsource_manager.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	private final DeveloperRepository developerRepository;

	@Autowired
	public JwtUserDetailsService(DeveloperRepository developerRepository) {
		this.developerRepository = developerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) {
		Optional<Developer> user = developerRepository.findByLogin(login);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + login));
		
	} 

	public UserDetails loadUserById(Long id)  {
		Optional<Developer> user = developerRepository.findById(id);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + id));

	} 
	
}  
