package com.reddit.clone.servicesimpl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.models.NotificationEmail;
import com.reddit.clone.services.MailService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
	@Override
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(notificationEmail.getBody());
		};
		try {
			mailSender.send(mimeMessagePreparator);
			log.info("Activation email sent!!");
		} catch(MailException e) {
			log.error("Exception occurred when sending mail", e);
			throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
		}
	}

}
