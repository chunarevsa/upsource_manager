package com.rtkit.upsource_manager.services;

import com.rtkit.upsource_manager.entities.user.User;
import com.rtkit.upsource_manager.repositories.UserRepository;
import com.rtkit.upsource_manager.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	@Autowired
	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) {
		Optional<User> user = userRepository.findByLogin(login);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + login));
		
	} 

	public UserDetails loadUserById(Long id)  {
		Optional<User> user = userRepository.findById(id);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + id));

	} 
	
}  
