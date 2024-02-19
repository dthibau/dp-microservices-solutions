package org.formation.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceManager;

	OAuth2AuthorizedClient oAuth2AuthorizedClient;

	public OAuth2AccessToken getToken() {
		if (oAuth2AuthorizedClient == null) {
			oAuth2AuthorizedClient = authorizedClientServiceManager.authorize(initialRequest());
		} else if (oAuth2AuthorizedClient.getAccessToken().getExpiresAt().minusSeconds(60).isBefore(Instant.now())) {
			oAuth2AuthorizedClient = authorizedClientServiceManager.authorize(initialRequest());
		}
		return oAuth2AuthorizedClient.getAccessToken();
	}

	OAuth2AuthorizeRequest initialRequest() {
		return OAuth2AuthorizeRequest.withClientRegistrationId("store").principal("Order Service").build();
	}

	OAuth2AuthorizeRequest refreshRequest(OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		return OAuth2AuthorizeRequest.withAuthorizedClient(oAuth2AuthorizedClient).principal("Order Service").build();
	}

}
