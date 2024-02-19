package org.formation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "notification")
@Data
public class MailConfigurationProperties {

	private String host;
	private int port;
	private String username, password;

	

}
