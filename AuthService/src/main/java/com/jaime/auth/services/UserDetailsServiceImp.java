package com.jaime.auth.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jaime.auth.models.User;

@Service("userDetailsServiceImp")
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	private IUser userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("The username % doesn't exists", username));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.
				User(user.getUsername(), user.getPassword(), authorities);
		
		return userDetails;
	}
	
	

}
