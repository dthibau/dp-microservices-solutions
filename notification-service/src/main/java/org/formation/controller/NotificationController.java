package org.formation.controller;

import org.formation.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {


	@Autowired
	EmailService emailService;
	
	@Value("${server.port}")
	private int port;
	
	@PostMapping("/sendSimple")
	public String sendSimpleMessage(@RequestBody Email email) {
	
		emailService.sendSimpleMessage(email.getTo(), email.getSubject(), email.getText());
		
		return "OK from " + port;
		
	}
}
