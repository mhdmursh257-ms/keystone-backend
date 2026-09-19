package com.key_stone.Security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.key_stone.Entity.UserAuth;
import com.key_stone.Repository.UserAuthRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAuthRepository userAuthRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserAuth user = userAuthRepository.findByUserEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		
		List<GrantedAuthority> authorities = Arrays.asList(
			new SimpleGrantedAuthority("ROLE_" + user.getRole().name())	
		);
		
		return new User(
				user.getUserEmail(),
				user.getPassword(),
				authorities
		);		
	}
}
