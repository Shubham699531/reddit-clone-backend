package com.reddit.clone.services;

import com.reddit.clone.models.NotificationEmail;

public interface MailService {
	
	void sendMail(NotificationEmail notificationEmail);

}
