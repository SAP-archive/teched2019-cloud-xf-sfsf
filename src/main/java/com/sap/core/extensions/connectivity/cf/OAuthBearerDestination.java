package com.sap.core.extensions.connectivity.cf;

public class OAuthBearerDestination {
	private final String url;
	private final String bearerToken;

	public OAuthBearerDestination(String url, String bearerToken) {
		this.url = url;
		this.bearerToken = bearerToken;
	}

	public String getUrl() {
		return url;
	}

	public String getBearerToken() {
		return bearerToken;
	}

	@Override
	public String toString() {
		return "OAuthBearerDestination [url=" + url + ", bearerToken=" + bearerToken + "]";
	}

}
