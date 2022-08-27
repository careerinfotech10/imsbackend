package com.career.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.career.bindings.LoginForm;
import com.career.exception.UserAppException;
import com.career.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class LoginRestController {

	@Autowired
	private UserService service;

	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) throws UserAppException{
		return service.loginCheck(loginForm);
	}
}
