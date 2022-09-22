package com.career.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.career.exception.UserAppException;
import com.career.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class ForgotPwdRestController {

	@Autowired
	private UserService service;

	@GetMapping("/forgotPwd/{emailId}")
	public String forgotPwd(@PathVariable String emailId) throws UserAppException {

		boolean status = service.forgotPwd(emailId);

		if (status) {
			return "We have sent password to your email";
		} else {
			return "Please enter valid email id";
		}

	}

}
