package com.evolution.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Override
	public void sendSimpleMessage(Mail mail, String template) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				"UTF-8");

		Context context = new Context();
		context.setVariables(mail.getModel());
		String html = templateEngine.process(template, context);

		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());

		emailSender.send(message);
	}

	@Override
	public void sendEmail(Mail mail) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
		Context context = new Context();
		context.setVariables(mail.getProps());

		String html = templateEngine.process("newsletter-template", context);
		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());
		emailSender.send(message);
	}

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			emailSender.send(message);
		} catch (MailException e) {
			System.out.println(e.getMessage());
		}
	}
}
