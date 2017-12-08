package com.jaime.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaime.auth.models.User;
import com.jaime.auth.repositories.UserRepository;

@Service
public class UserService implements IUser {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<String> findAllUsernames() {
		return userRepository.findAllUsernames();
	}
	
}
