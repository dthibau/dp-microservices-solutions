package org.formation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(	ServerHttpSecurity http) {
		return http.authorizeExchange(acl -> acl.anyExchange().hasAnyRole("MANAGER","CLIENT"))
				.oauth2ResourceServer((v -> v.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))))
				.csrf(csrf -> csrf.disable())
				.build();
	}
	
	@Bean
	public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	    grantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
	    grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

	    ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
	    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
	    return jwtAuthenticationConverter;
	}
}
