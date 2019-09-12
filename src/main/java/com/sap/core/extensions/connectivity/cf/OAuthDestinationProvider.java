package com.sap.core.extensions.connectivity.cf;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.connectivity.OAuthRESTClient;
import com.sap.core.extensions.successfactors.connectivity.DestinationNotFoundException;

@Component
public class OAuthDestinationProvider {

	private static final String GET_DESTINATION_API_PATH = "/destination-configuration/v1/destinations/";
	private static final String PATH_TO_TOKEN = "/authTokens/0/value";
	private static final String PATH_TO_URL = "/destinationConfiguration/URL";
	private final TokenManager tokenManager;
	private final String destinationServiceAPIPath;
	private final OAuthRESTClient client;

	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthDestinationProvider.class);

	@Autowired
	public OAuthDestinationProvider(TokenManager tokenExchanger, CredentialsProvider credentialsProvider,
			OAuthRESTClient client) {
		this.tokenManager = tokenExchanger;
		this.client = client;
		this.destinationServiceAPIPath = credentialsProvider.getDestinationCredentials().getServiceURI()
				+ GET_DESTINATION_API_PATH;
	}

	public OAuthBearerDestination fetchBearerDestination(String destinationName, Token userToken) {
		String tokenForDestinationService = tokenManager.exchangeTokenForDestinationService(userToken);

		return fetchFromDestinationService(destinationName, tokenForDestinationService);
	}

	public OAuthBearerDestination fetchBearerDestination(String destinationName) {
		String tokenForDestinationService = tokenManager.getTokenForDestinationServce();

		return fetchFromDestinationService(destinationName, tokenForDestinationService);
	}

	private OAuthBearerDestination fetchFromDestinationService(String destinationName, String destinationServiceToken) {
		String destinationServiceResponse = client.get(destinationServiceAPIPath + destinationName, String.class,
				destinationServiceToken);

		LOGGER.info("Recieved response for destination [{}]: [{}]", destinationName, destinationServiceResponse);

		if (destinationServiceResponse == null) {
			throw new DestinationNotFoundException(
					String.format("Destination with name [%s] could not be found", destinationName));
		}

		JSONObject destinationJSON = new JSONObject(destinationServiceResponse);

		return buildDestinationFromJson(destinationJSON);
	}

	private OAuthBearerDestination buildDestinationFromJson(JSONObject destination) {
		String url = (String) destination.query(PATH_TO_URL);
		String token = (String) destination.query(PATH_TO_TOKEN);

		return new OAuthBearerDestination(url, token);
	}

}