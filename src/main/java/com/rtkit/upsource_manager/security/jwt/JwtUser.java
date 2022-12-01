package com.rtkit.upsource_manager.security.jwt;

import com.rtkit.upsource_manager.entities.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;


public class JwtUser extends User implements UserDetails {

	public JwtUser (final User user) {
		super(user.getLogin(), user.getPassword(), user.isActive());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole().name()))
					.collect(Collectors.toList());
	} 

	@Override
	public String getUsername() {
		return super.getLogin();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return super.isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.isActive();
	}

	@Override
	 public boolean equals(Object obj) {
		  if (obj == this)
				return true;
		  if (!(obj instanceof JwtUser)) {
				return false;
		  }
		  JwtUser jwtUser = (JwtUser) obj;
		  return Objects.equals(getId(), jwtUser.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
} 
