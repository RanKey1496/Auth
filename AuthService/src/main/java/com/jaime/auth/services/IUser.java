package com.jaime.auth.services;

import java.util.List;

import com.jaime.auth.models.User;

public interface IUser {

	User findByUsername(String username);
	
	List<String> findAllUsernames();
	
}
