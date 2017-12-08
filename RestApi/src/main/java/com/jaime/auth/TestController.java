package com.jaime.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class TestController {

	@RequestMapping("/time")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public String currentTime() {
		return "Pasó";
	}
	
}
