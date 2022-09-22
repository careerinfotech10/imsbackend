package com.career.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.career.bindings.UnlockAccountForm;
import com.career.exception.UserAppException;
import com.career.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UnlockAccountRestController {

	@Autowired
	private UserService service;

	@PostMapping("/unlock")
	public String unlockUserAccount(@RequestBody UnlockAccountForm unlockAccForm) throws UserAppException {
		boolean status = service.unlockAccount(unlockAccForm);
		if (status) {
			return "Account unlocked successfully";
		} else {
			return "Incorrect Temporary Password";
		}
	}

}
