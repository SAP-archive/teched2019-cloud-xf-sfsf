package com.sap.core.extensions.connectivity.cf;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.connectivity.OAuthRESTClient;

@Component
public class OAuthDestinationProvider {

	private static final String GET_DESTINATION_API_PATH = "/destination-configuration/v1/destinations/";
	private static final String PATH_TO_TOKEN = "/authTokens/0/value";
	private static final String PATH_TO_URL = "/destinationConfiguration/URL";
	private final TokenManager tokenExchanger;
	private final String destinationServiceAPIPath;
	private final OAuthRESTClient client;

	@Autowired
	public OAuthDestinationProvider(TokenManager tokenExchanger, CredentialsProvider credentialsProvider,
			OAuthRESTClient client) {
		this.tokenExchanger = tokenExchanger;
		this.client = client;
		this.destinationServiceAPIPath = credentialsProvider.getDestinationCredentials().getServiceURI()
				+ GET_DESTINATION_API_PATH;
	}

	public OAuthBearerDestination fetchBearerDestination(String destinationName, Token userToken) {
		JSONObject destination = fetchDestination(destinationName, userToken);

		String url = (String) destination.query(PATH_TO_URL);
		String token = (String) destination.query(PATH_TO_TOKEN);

		return new OAuthBearerDestination(url, token);
	}

	public OAuthBearerDestination fetchBearerDestination(String destinationName) {
		String tokenForDestinationService = tokenExchanger.getTokenForDestinationServce();

		String destinationServiceResponse = client.get(destinationServiceAPIPath + destinationName, String.class,
				tokenForDestinationService);
		JSONObject destination = new JSONObject(destinationServiceResponse);

		String url = (String) destination.query(PATH_TO_URL);
		String token = (String) destination.query(PATH_TO_TOKEN);

		return new OAuthBearerDestination(url, token);
	}

	private JSONObject fetchDestination(String destinationName, Token userToken) {
		String tokenForDestinationService = tokenExchanger.exchangeTokenForDestinationService(userToken);

		String destinationServiceResponse = client.get(destinationServiceAPIPath + destinationName, String.class,
				tokenForDestinationService);

		return new JSONObject(destinationServiceResponse);
	}

}
