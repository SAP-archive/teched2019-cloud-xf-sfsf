package com.sap.core.extensions.successfactors.connectivity;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.connectivity.OAuthRESTClient;
import com.sap.core.extensions.connectivity.cf.OAuthBearerDestination;
import com.sap.core.extensions.connectivity.cf.OAuthDestinationProvider;

@Component
public class SuccessFactorsCommunicator {

	private static final String USER_PROGAGATION_DESTINATION_NAME = "SFOAuth";
	private static final String TECHNICAL_USER_DESTINATION_NAME = "SFOAuthTechUser";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SuccessFactorsCommunicator.class);

	private final OAuthRESTClient client;
	private final OAuthDestinationProvider destinationProvider;

	@Autowired
	public SuccessFactorsCommunicator(OAuthRESTClient client, OAuthDestinationProvider destinationProvider) {
		this.client = client;
		this.destinationProvider = destinationProvider;
	}

	public <T> T getWithUserPropagation(String relativePath, Class<T> responseEntity, Token userToken) {
		OAuthBearerDestination destination = destinationProvider
				.fetchBearerDestination(USER_PROGAGATION_DESTINATION_NAME, userToken);

		return get(relativePath, responseEntity, destination);
	}

	public <T> JSONObject postWithTechnicalUser(String relativePath, T requestEntity) {
		OAuthBearerDestination destination = destinationProvider
				.fetchBearerDestination(TECHNICAL_USER_DESTINATION_NAME);
		String url = destination.getUrl();
		String sfToken = destination.getBearerToken();

		String responseString = client.post(url + relativePath, requestEntity, sfToken, String.class);

		return new JSONObject(responseString);
	}

	public <T> T getWithTechnicalUser(String relativePath, Class<T> responseEntity) {
		OAuthBearerDestination destination = destinationProvider
				.fetchBearerDestination(TECHNICAL_USER_DESTINATION_NAME);

		return get(relativePath, responseEntity, destination);
	}

	private <T> T get(String relativePath, Class<T> responseEntity, OAuthBearerDestination destination) {
		String url = destination.getUrl();
		String token = destination.getBearerToken();

		LOGGER.error("Bearer token for SFSF: " + token);
		
		return client.get(url + relativePath, responseEntity, token);
	}
}
