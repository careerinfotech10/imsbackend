package com.career.mailer.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.career.exception.UserAppException;
import com.career.students.service.MailerServer;

@RestController
@RequestMapping("/mailer")
public class MailerResource {

	@Autowired
	private MailerServer mailerService;

	@GetMapping("/subject/{subject}")
	public Map<String, List<String>> sentEmail(@PathVariable("subject") String subject) throws UserAppException {
		return mailerService.triggerEmail(subject);
	}

}
