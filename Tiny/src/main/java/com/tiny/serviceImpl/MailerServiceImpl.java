package com.tiny.serviceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tiny.service.MailerService;

@Service
public class MailerServiceImpl implements MailerService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String fromAddress;
	@Value("${app.mail_to_Address}")
	private String toMailsAddress;

	public void sendCSVLogs() throws MessagingException {
		try {

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject("Daily Report");
			helper.setText("");
			helper.setTo(toMailsAddress);
			helper.setFrom(fromAddress);
			//FileSystemResource file = new FileSystemResource(TinyUtils.logCsvPath);
			//helper.addAttachment(file.getFilename(), file);

			javaMailSender.send(message);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public void sendCSVLogs(String locPath) throws Exception {
		// TODO Auto-generated method stub
		
	}
}