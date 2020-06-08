package com.evolution.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.evolution.model.Status;
import com.evolution.model.User;
import com.evolution.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<User> userOptional = null;

		try {
			userOptional = userRepository.findByEmailAndStatus(email, Status.ACTIVE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Incorrect email and or password!"));

		return new SystemUser(user, getRoles(user));
	}

	private Collection<? extends GrantedAuthority> getRoles(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getTypeUser().toString()));
		return authorities;
	}

}
