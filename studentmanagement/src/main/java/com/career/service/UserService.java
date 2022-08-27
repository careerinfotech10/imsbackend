package com.career.service;

import java.util.Map;

import com.career.bindings.LoginForm;
import com.career.bindings.UnlockAccountForm;
import com.career.bindings.UserRegForm;
import com.career.exception.UserAppException;

public interface UserService {

	public String loginCheck(LoginForm loginForm) throws UserAppException;

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public String emailCheck(String emailId);

	public boolean saveUser(UserRegForm userForm) throws UserAppException;

	public boolean unlockAccount(UnlockAccountForm unloackAccForm) throws UserAppException;

	public boolean forgotPwd(String emailId) throws UserAppException;

}
