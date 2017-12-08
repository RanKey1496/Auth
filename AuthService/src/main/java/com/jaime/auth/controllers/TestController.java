package com.jaime.auth.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaime.auth.services.IUser;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	IUser userService;
	
	@RequestMapping("/random")
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public String getRandom() {
		return UUID.randomUUID().toString();
	}
	
	@RequestMapping(value="/users")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public List<String> getUsers() {
		return userService.findAllUsernames();
	}
	
}
