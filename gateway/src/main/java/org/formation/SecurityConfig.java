package org.formation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(	ServerHttpSecurity http) {
		return http.authorizeExchange(acl -> acl.anyExchange().authenticated())
				.oauth2ResourceServer((v -> v.jwt(Customizer.withDefaults())))
				.csrf(csrf -> csrf.disable())
				.build();
	}
}
