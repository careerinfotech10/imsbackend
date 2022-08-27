package com.career.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.career.bindings.UserRegForm;
import com.career.constants.AppConstants;
import com.career.exception.UserAppException;
import com.career.props.AppProperties;
import com.career.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserRegRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppProperties appProps;

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {
		return userService.getCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		return userService.getCities(stateId);
	}

	@GetMapping("/emailcheck/{email}")
	public String uniqueEmailCheck(@PathVariable String email) {
		return userService.emailCheck(email);
	}

	@PostMapping("/saveUser")
	public String saveUser(@RequestBody UserRegForm userRegForm) throws UserAppException {
		boolean saveUser = userService.saveUser(userRegForm);
		if (saveUser) {
			return appProps.getMessages().get(AppConstants.USER_REG_SUCCESS);
		} else {
			return appProps.getMessages().get(AppConstants.USER_REG_FAIL);
		}
	}
}
