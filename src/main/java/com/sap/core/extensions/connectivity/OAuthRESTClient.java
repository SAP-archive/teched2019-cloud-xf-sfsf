package com.sap.core.extensions.connectivity;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sap.core.extensions.successfactors.connectivity.ResponseGeneralErrorHandler;

@Component
public class OAuthRESTClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRESTClient.class);

	public <T> T get(String url, Class<T> responseEntity, String token) {
		LOGGER.info("Will execute request to url [{}]", url);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<?> entity = new HttpEntity<>(headers);

		return createRestTemplate().exchange(url, HttpMethod.GET, entity, responseEntity).getBody();
	}

	public <R, T> T post(String url, R requestEntity, String token, Class<T> responseEntity) {
		LOGGER.info("Will execute request to url [{}]", url);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

		HttpEntity<R> entity = new HttpEntity<>(requestEntity, headers);

		return createRestTemplate().exchange(url, HttpMethod.POST, entity, responseEntity).getBody();
	}

	private RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ResponseGeneralErrorHandler());
		return restTemplate;
	}
}
