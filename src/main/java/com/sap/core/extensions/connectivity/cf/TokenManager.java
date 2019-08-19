package com.sap.core.extensions.connectivity.cf;

import java.net.URI;
import java.net.URISyntaxException;

import org.cloudfoundry.identity.client.UaaContext;
import org.cloudfoundry.identity.client.UaaContextFactory;
import org.cloudfoundry.identity.client.token.GrantType;
import org.cloudfoundry.identity.client.token.TokenRequest;
import org.cloudfoundry.identity.uaa.oauth.token.CompositeAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.xs2.security.container.XSTokenRequestImpl;
import com.sap.xsa.security.container.XSTokenRequest;

import io.pivotal.cfenv.core.CfCredentials;

@Component
public class TokenManager {

	private CredentialsProvider credentialsProvider;

	@Autowired
	TokenManager(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;

	}

	public String getTokenForDestinationServce() {
		CfCredentials xsuaCredentials = credentialsProvider.getXSUAАCredentials();
		String xsuaaURI = xsuaCredentials.getUri();

		DestinationServiceCredentials destinationCredentials = credentialsProvider.getDestinationCredentials();
		String destinationServiceClientId = destinationCredentials.getClientId();
		String destinationServiceClientSecret = destinationCredentials.getClientSecret();

		URI xsuaaUrl;
		try {
			xsuaaUrl = new URI(xsuaaURI);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}

		// Make request to UAA to retrieve JWT
		UaaContextFactory factory = UaaContextFactory.factory(xsuaaUrl);
		TokenRequest tokenRequest = factory.tokenRequest();
		tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
		tokenRequest.setClientId(destinationServiceClientId);
		tokenRequest.setClientSecret(destinationServiceClientSecret);

		UaaContext xsUaaContext = factory.authenticate(tokenRequest);
		CompositeAccessToken token = xsUaaContext.getToken();
		return token.getValue();
	}

	public String exchangeTokenForDestinationService(Token token) {
		CfCredentials xsuaCredentials = credentialsProvider.getXSUAАCredentials();
		String xsuaaURI = xsuaCredentials.getUri();

		DestinationServiceCredentials destinationCredentials = credentialsProvider.getDestinationCredentials();
		String destinationServiceClientId = destinationCredentials.getClientId();
		String destinationServiceClientSecret = destinationCredentials.getClientSecret();

		try {
			XSTokenRequest tokenRequest = createTokenRequest(xsuaaURI, destinationServiceClientId,
					destinationServiceClientSecret);

			return token.requestToken(tokenRequest);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid URI found for XSUAA instance [" + xsuaaURI + "]");
		}
	}

	private XSTokenRequest createTokenRequest(String xsuaaUri, String destinationServiceClientId,
			String destinationServiceClientSecret) throws URISyntaxException {
		XSTokenRequest tokenRequest = new XSTokenRequestImpl(xsuaaUri);

		tokenRequest.setType(XSTokenRequest.TYPE_USER_TOKEN);
		tokenRequest.setClientId(destinationServiceClientId);
		tokenRequest.setClientSecret(destinationServiceClientSecret);

		return tokenRequest;
	}
}
