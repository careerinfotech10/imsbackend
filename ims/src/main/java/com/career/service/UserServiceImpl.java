package com.career.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.career.bindings.LoginForm;
import com.career.bindings.UnlockAccountForm;
import com.career.bindings.UserRegForm;
import com.career.constants.AppConstants;
import com.career.entity.CitiesMasterEntity;
import com.career.entity.CountryMasterEntity;
import com.career.entity.StateMasterEntity;
import com.career.entity.UserAccountEntity;
import com.career.exception.UserAppException;
import com.career.props.AppProperties;
import com.career.repository.CitiesMasterRepository;
import com.career.repository.CountryMasterRepository;
import com.career.repository.StatesMasterRepository;
import com.career.repository.UserAccountsRepository;
import com.career.util.EmailUtils;
import com.career.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private CountryMasterRepository countryRepo;

	@Autowired
	private StatesMasterRepository stateRepo;

	@Autowired
	private CitiesMasterRepository cityRepo;

	@Autowired
	private UserAccountsRepository userRepo;

	@Autowired
	private AppProperties appProps;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String loginCheck(LoginForm loginForm) throws UserAppException {
		String msg;
		String encryptedPwd = PwdUtils.encryptMsg(loginForm.getPwd());

		UserAccountEntity user = userRepo.findByEmailAndPazzword(loginForm.getEmail(), encryptedPwd);
		if (user != null) {
			String accStatus = user.getAccStatus();
			if (AppConstants.LOCKED.equals(accStatus)) {
				msg = appProps.getMessages().get(AppConstants.ACC_LOCKED);
			} else {
				msg = AppConstants.SUCCESS;
			}
		} else {
			msg = appProps.getMessages().get(AppConstants.INVALID_CREDENTAILS);
		}
		return msg;
	}

	@Override
	public Map<Integer, String> getCountries() {

		List<CountryMasterEntity> countries = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();

		countries.forEach(country -> countryMap.put(country.getCountryId(), country.getCountryName()));

		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);

		Map<Integer, String> stateMap = new HashMap<>();
		states.forEach(state -> stateMap.put(state.getStateId(), state.getStateName()));

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		List<CitiesMasterEntity> cities = cityRepo.findByStateId(stateId);

		Map<Integer, String> cityMap = new HashMap<>();

		cities.forEach(city -> cityMap.put(city.getCityId(), city.getCityName()));

		return cityMap;
	}

	@Override
	public String emailCheck(String emailId) {

		UserAccountEntity entity = new UserAccountEntity();
		entity.setEmail(emailId.trim());

		Example<UserAccountEntity> example = Example.of(entity);

		Optional<UserAccountEntity> findOne = userRepo.findOne(example);

		if (findOne.isPresent()) {
			return AppConstants.DUPLICATE;
		} else {
			return AppConstants.UNIQUE;
		}
	}

	@Override
	public boolean saveUser(UserRegForm userForm) throws UserAppException {

		UserAccountEntity entity = new UserAccountEntity();

		BeanUtils.copyProperties(userForm, entity);

		entity.setAccStatus(AppConstants.LOCKED);

		String randomPwd = generateRandomPazzwrd(6);
		String encryptedPwd = PwdUtils.encryptMsg(randomPwd);
		entity.setPazzword(encryptedPwd);

		entity = userRepo.save(entity);

		String emailBody = readUnlockAccEmailBody(entity);
		String subject = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_SUB);

		try {
			emailUtils.sendEmail(userForm.getEmail(), subject, emailBody);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION_OCCURED + e.getMessage(), e);
			throw new UserAppException(e.getMessage());
		}

		return entity.getUserId() != null ? true : false;
	}

	@Override
	public boolean unlockAccount(UnlockAccountForm unlockAccForm) throws UserAppException {
		String email = unlockAccForm.getEmail();
		String tempPwd = unlockAccForm.getTempPwd();
		String encryptedPwd = PwdUtils.encryptMsg(tempPwd);

		UserAccountEntity user = userRepo.findByEmailAndPazzword(email, encryptedPwd);

		if (user != null) {
			String newPwd = unlockAccForm.getNewPwd();
			String encryptedNewPwd = null;
			try {
				encryptedNewPwd = PwdUtils.encryptMsg(newPwd);
				user.setPazzword(encryptedNewPwd);
			} catch (Exception e) {
				logger.error(AppConstants.EXCEPTION_OCCURED + e.getMessage(), e);
				throw new UserAppException(e.getMessage());
			}
			user.setAccStatus(AppConstants.UNLOCKED);
			userRepo.save(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean forgotPwd(String emailId) throws UserAppException {

		UserAccountEntity entity = new UserAccountEntity();
		entity.setEmail(emailId);

		Example<UserAccountEntity> example = Example.of(entity);
		Optional<UserAccountEntity> findOne = userRepo.findOne(example);

		if (findOne.isPresent()) {
			UserAccountEntity userEntity = findOne.get();
			String body = readForgotPwdEmailBody(userEntity);
			String subject = appProps.getMessages().get(AppConstants.RECOVER_PZZWD_EMAIL_SUB);
			try {
				emailUtils.sendEmail(userEntity.getEmail(), subject, body);
			} catch (Exception e) {
				logger.error(AppConstants.EXCEPTION_OCCURED + e.getMessage(), e);
				throw new UserAppException(e.getMessage());
			}
			return true;
		} else {
			return false;
		}
	}

	private String readForgotPwdEmailBody(UserAccountEntity entity) throws UserAppException {
		StringBuilder sb = new StringBuilder(AppConstants.EMPTY_STR);
		String mailBody = AppConstants.EMPTY_STR;
		String fileName = appProps.getMessages().get(AppConstants.RECOVER_PZZWD_EMAIL_BODY_FILE);
		try (InputStream in = getClass().getResourceAsStream("/" + fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();

			String pazzword = entity.getPazzword();
			String decryptedPwd = PwdUtils.decryptMsg(pazzword);

			mailBody = sb.toString();
			mailBody = mailBody.replace(AppConstants.FNAME, entity.getFname());
			mailBody = mailBody.replace(AppConstants.LNAME, entity.getLname());
			mailBody = mailBody.replace(AppConstants.PZZWD, decryptedPwd);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION_OCCURED + e.getMessage(), e);
			throw new UserAppException(e.getMessage());
		}
		return mailBody;

	}

	private static String generateRandomPazzwrd(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(AppConstants.CANDIDATE_CHARS.charAt(random.nextInt(AppConstants.CANDIDATE_CHARS.length())));
		}
		return sb.toString();
	}

	private String readUnlockAccEmailBody(UserAccountEntity entity) throws UserAppException {
		StringBuilder sb = new StringBuilder(AppConstants.EMPTY_STR);
		String mailBody = AppConstants.EMPTY_STR;

		String fileName = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_BODY_FILE);

		try (InputStream in = getClass().getResourceAsStream("/" + fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			// Use resource
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();

			String decryptedPwd = PwdUtils.decryptMsg(entity.getPazzword());

			mailBody = sb.toString();
			mailBody = mailBody.replace(AppConstants.FNAME, entity.getFname());
			mailBody = mailBody.replace(AppConstants.LNAME, entity.getLname());
			mailBody = mailBody.replace(AppConstants.TEMP_PZZWD, decryptedPwd);
			mailBody = mailBody.replace(AppConstants.EMAIL, entity.getEmail());
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION_OCCURED + e.getMessage(), e);
			throw new UserAppException(e.getMessage());
		}
		return mailBody;
	}

	private File getFileFromText(String fileName) {

		try (InputStream in = getClass().getResourceAsStream("/" + fileName);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			// Use resource
		} catch (Exception e) {
			// TODO: handle exception
		}

		File file = null;
//
//		try {
//			file = resource.getFile();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		return file;
	}
}
