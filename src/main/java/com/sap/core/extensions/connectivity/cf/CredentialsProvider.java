package com.sap.core.extensions.connectivity.cf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfEnv;

@Component
public class CredentialsProvider {

	private static final String DESTINATION_CREDENTIALS_LABEL = "destination";
	private static final String XSUAA_CREDENTIALS_LABEL = "xsuaa";
	private CfEnv cfEnv;

	@Autowired
	public CredentialsProvider(CfEnv cfEnv) {
		this.cfEnv = cfEnv;
	}

	public CfCredentials getXSUAАCredentials() {
		return cfEnv.findCredentialsByLabel(XSUAA_CREDENTIALS_LABEL);
	}

	public DestinationServiceCredentials getDestinationCredentials() {
		CfCredentials destinationServiceCredentials = cfEnv.findCredentialsByLabel(DESTINATION_CREDENTIALS_LABEL);

		return new DestinationServiceCredentials(destinationServiceCredentials);
	}
}
