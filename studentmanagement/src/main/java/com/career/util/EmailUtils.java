package com.career.util;

import java.io.File;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.joran.conditional.IfAction;

@Component
public class EmailUtils {
	private static final String FROM_EMAIL = "ssturerao@gmail.com";
	private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String to, String subject, String body) {
		boolean isSent = false;
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(body, true);
			mailSender.send(mimeMessageHelper.getMimeMessage());
			isSent = true;
		} catch (Exception e) {
			logger.error("Exception Occured" + e.getMessage(), e);
		}
		return isSent;
	}

	public boolean sendMailWithAttachment(String to, String subject, String body, String fileUrl, String fileName) {
		boolean isemailSent = false;
		FileSystemResource file = 
				new FileSystemResource(new File(fileUrl));
	
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				// Message.RecipientType.TO, new InternetAddress(to)
				helper.addTo(new InternetAddress(to));
				helper.setFrom(new InternetAddress(FROM_EMAIL));
				helper.setSubject(subject);
				helper.setText(body, true);
				helper.addAttachment(fileName, file);

			}
		};

		try {
			mailSender.send(preparator);
			isemailSent = true;
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
		return isemailSent;
	}

}
