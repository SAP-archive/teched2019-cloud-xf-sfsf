package com.sap.core.extensions.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.persistence.OnboardRequestService;
import com.sap.core.extensions.successfactors.connectivity.User;
import com.sap.core.extensions.successfactors.connectivity.UserDataAccessor;

@RestController
public class InitController {
	private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);
	private final UserDataAccessor userAccessor;
	private final OnboardRequestService onboardRequestService;

	@Autowired
	public InitController(UserDataAccessor userAccessor, OnboardRequestService onboardRequestService) {
		this.userAccessor = userAccessor;
		this.onboardRequestService = onboardRequestService;
	}

	@GetMapping(value = "/v1/admin/init")
	public ResponseEntity<?> init(@AuthenticationPrincipal Token token) {
		User userProfile = userAccessor.fetchUserProfile(token.getLogonName(), token);
		LOGGER.error("Recieved user profile {}" + userProfile);

		onboardRequestService.createOnboardingRequest(userProfile, token);

		return ResponseEntity.noContent().build();
	}
}
