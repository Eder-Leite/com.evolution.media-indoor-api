package com.evolution.email;

import java.io.IOException;

import javax.mail.MessagingException;

public interface EmailService {

	public void sendSimpleMessage(String to, String subject, String text);
	
	public void sendEmail(Mail mail) throws MessagingException, IOException;
	
	public void sendSimpleMessage(Mail mail, String template) throws MessagingException, IOException;

}
