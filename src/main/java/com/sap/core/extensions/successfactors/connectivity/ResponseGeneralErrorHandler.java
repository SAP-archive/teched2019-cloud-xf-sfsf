package com.sap.core.extensions.successfactors.connectivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ResponseGeneralErrorHandler implements ResponseErrorHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseGeneralErrorHandler.class);

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {

		int rawStatusCode = response.getRawStatusCode();
		return rawStatusCode >= 300 || rawStatusCode < 200;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		LOGGER.error("REQUEST FAILED! Status: {}, Body: {}", response.getStatusText(),
				IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
	}

}
