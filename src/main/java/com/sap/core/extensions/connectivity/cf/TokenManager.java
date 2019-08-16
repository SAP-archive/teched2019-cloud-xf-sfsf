package com.sap.core.extensions.connectivity.cf;

import java.net.URISyntaxException;

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

	public String exchangeTokenForDestinationService(Token token, boolean propagateUser) {
		CfCredentials xsuaCredentials = credentialsProvider.getXSUAАCredentials();
		String xsuaaURI = xsuaCredentials.getUri();

		DestinationServiceCredentials destinationCredentials = credentialsProvider.getDestinationCredentials();
		String destinationServiceClientId = destinationCredentials.getClientId();
		String destinationServiceClientSecret = destinationCredentials.getClientSecret();

		try {
			XSTokenRequest tokenRequest = createTokenRequest(xsuaaURI, destinationServiceClientId,
					destinationServiceClientSecret, propagateUser);

			return token.requestToken(tokenRequest);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid URI found for XSUAA instance [" + xsuaaURI + "]");
		}
	}

	private XSTokenRequest createTokenRequest(String xsuaaUri, String destinationServiceClientId,
			String destinationServiceClientSecret, boolean propagateUser) throws URISyntaxException {
		XSTokenRequest tokenRequest = new XSTokenRequestImpl(xsuaaUri);

		int tokenType = propagateUser ? XSTokenRequest.TYPE_USER_TOKEN : XSTokenRequest.TYPE_CLIENT_CREDENTIALS_TOKEN;
		tokenRequest.setType(tokenType);
		tokenRequest.setClientId(destinationServiceClientId);
		tokenRequest.setClientSecret(destinationServiceClientSecret);

		return tokenRequest;
	}
}
