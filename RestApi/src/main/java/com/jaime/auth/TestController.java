package com.jaime.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jaime.auth.model.User;
import com.jaime.auth.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/service")
public class TestController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/time", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public String currentTime() {
		return "Pasó";
	}
	
	@RequestMapping(value = "/hola", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String holaAuthenticated() {
		return "Pasan todos";
	}
	
	@ApiOperation(value = "Obtener un usuario por su Id", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Succesfully retrieved user")
	})
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User get(@PathVariable("id") int id) {
		User user = userRepository.findOne(id);
		return user;
	}
	
	@RequestMapping(value = "/user/{username}/{password}", method = RequestMethod.POST)
	public String create(@PathVariable("username") String username, @PathVariable("password") String password) {
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		userRepository.saveAndFlush(newUser);
		return "Created";
	}
	
	@RequestMapping(value = "/user/{id}/{password}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, @PathVariable("password") String password) {
		User user = userRepository.findOne(id);
		user.setPassword(password);
		userRepository.saveAndFlush(user);
		return "Updated";
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id) {
		userRepository.delete(id);
		return "Deleted";
	}
	
}
