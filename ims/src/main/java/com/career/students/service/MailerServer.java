package com.career.students.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.career.constants.AppConstants;
import com.career.exception.UserAppException;
import com.career.props.AppProperties;
import com.career.students.entity.Student;
import com.career.students.repo.StudentRepo;
import com.career.util.EmailUtils;
import com.itextpdf.text.log.SysoCounter;

@Service
public class MailerServer {

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private AppProperties appProps;

	@Autowired
	private StudentRepo studentRepo;

	Map<String, List<String>> success = new HashMap<String, List<String>>();

	List<String> failedRecipent = new ArrayList<String>();
	List<String> successRecipent = new ArrayList<String>();

	public List<Student> getToEmails() {
		List<Student> findAllEmailByIsContractSentFalse = studentRepo.findAllEmailByIsContractSentFalse();
//		List<String> emails = new ArrayList<String>();
//		if (!findAllEmailByIsContractSentFalse.isEmpty()) {
//			emails = findAllEmailByIsContractSentFalse.stream().map(Student::getEmail).collect(Collectors.toList());
//		}
		return findAllEmailByIsContractSentFalse;

	}

	@Autowired
	ResourceLoader resourceLoader;

	public Map<String, List<String>> triggerEmail(String subject) throws UserAppException {

		List<Student> toEmails = getToEmails();

		for (Student student : toEmails) {
			String fileExtension = ".pdf";
			String fileUrl = "contracts\\" + student.getFirstName() + student.getLastName() + fileExtension;
			String fileName = student.getFirstName() + student.getLastName() + fileExtension;
			boolean sendEmail = emailUtils.sendMailWithAttachment(student.getEmail(), subject,
					readContractEmailBody(student), fileUrl, fileName);

			// .sendEmailWithAttachment(student.getEmail(), subject,
			// readContractEmailBody(student), fileName,
			// fileToAttach);
			// (email.getEmail(), subject, readUnlockAccEmailBody(email));

			if (sendEmail) {
				successRecipent.add(student.getEmail());
				student.setContractSent(true);
				studentRepo.save(student);
				System.out.println("email success : "+student.getEmail());
			} else {
				failedRecipent.add(student.getEmail());
				System.out.println("email failed : "+student.getEmail());
			}
		}
		success.put("success", successRecipent);
		success.put("failed", failedRecipent);

		return success;

	}

	private String readContractEmailBody(Student entity) throws UserAppException {
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
//			String decryptedPwd = PwdUtils.decryptMsg(entity.getPazzword());

			mailBody = sb.toString();
			mailBody = mailBody.replace(AppConstants.FNAME, entity.getFirstName());
			mailBody = mailBody.replace(AppConstants.LNAME, entity.getLastName());
//			mailBody = mailBody.replace(AppConstants.TEMP_PZZWD, decryptedPwd);
			mailBody = mailBody.replace(AppConstants.EMAIL, entity.getEmail());
		} catch (Exception e) {
//			logger.error(AppConstants.EXCEPTION_OCCURED + e.getMessage(), e);
			throw new UserAppException(e.getMessage());
		}
		return mailBody;
	}

}
