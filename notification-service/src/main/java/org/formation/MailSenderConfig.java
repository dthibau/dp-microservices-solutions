package org.formation;

import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class MailSenderConfig {

	
	@Autowired
	MailConfigurationProperties mailConfigurationProperties;
	
	protected Logger logger = Logger.getLogger(MailSenderConfig.class.getName());

	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

	    
	    mailSender.setHost(mailConfigurationProperties.getHost());
	    mailSender.setPort(mailConfigurationProperties.getPort());
	     
	    mailSender.setUsername(mailConfigurationProperties.getUsername());
	    mailSender.setPassword(mailConfigurationProperties.getPassword());
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "false");
	     
	    return mailSender;
	}
}
