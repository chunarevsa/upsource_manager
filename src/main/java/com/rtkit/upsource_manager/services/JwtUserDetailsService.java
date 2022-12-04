package com.rtkit.upsource_manager.services;

import com.rtkit.upsource_manager.entities.prticipant.Participant;
import com.rtkit.upsource_manager.repositories.ParticipantRepository;
import com.rtkit.upsource_manager.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	private final ParticipantRepository participantRepository;

	@Autowired
	public JwtUserDetailsService(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) {
		Optional<Participant> user = participantRepository.findByLogin(login);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + login));
		
	} 

	public UserDetails loadUserById(Long id)  {
		Optional<Participant> user = participantRepository.findById(id);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + id));

	} 
	
}  
