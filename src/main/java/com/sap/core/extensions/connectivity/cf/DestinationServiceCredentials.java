package com.sap.core.extensions.connectivity.cf;

import io.pivotal.cfenv.core.CfCredentials;

public class DestinationServiceCredentials {

	private static final String CLIENT_SECRET_KEY = "clientsecret";
	private static final String CLIENT_ID_KEY = "clientid";
	private final CfCredentials cfCredentials;

	public DestinationServiceCredentials(CfCredentials cfCredentials) {
		this.cfCredentials = cfCredentials;
	}

	public String getClientId() {
		return cfCredentials.getString(CLIENT_ID_KEY);
	}

	public String getClientSecret() {
		return cfCredentials.getString(CLIENT_SECRET_KEY);
	}

	public String getServiceURI() {
		return cfCredentials.getUri();
	}
}
