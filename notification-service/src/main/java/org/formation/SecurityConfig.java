package org.formation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.authorizeHttpRequests(acl -> acl.anyRequest().hasAuthority("SCOPE_service"))
				 .oauth2ResourceServer(r -> r.jwt(Customizer.withDefaults()))
		    .csrf(csrf -> csrf.disable())
		    .build();

	}
	

	
}
