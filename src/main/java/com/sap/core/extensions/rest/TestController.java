package com.sap.core.extensions.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.persistence.OnboardRequestEntity;
import com.sap.core.extensions.persistence.OnboardRequestService;
import com.sap.core.extensions.successfactors.connectivity.User;
import com.sap.core.extensions.successfactors.connectivity.UserDataAccessor;

@RestController
public class TestController {

	private final OnboardRequestService onboardRequestService;
	private UserDataAccessor userAccessor;

	@Autowired
	public TestController(OnboardRequestService onboardRequestService, UserDataAccessor userAccessor) {
		this.onboardRequestService = onboardRequestService;
		this.userAccessor = userAccessor;

	}

	@GetMapping(value = "/v1/requests")
	public ResponseEntity<Collection<OnboardRequestEntity>> listOnboardingRequests() {
		Collection<OnboardRequestEntity> requests = onboardRequestService.listOnboardingRequests();

		return ResponseEntity.ok(requests);
	}

	@GetMapping(value = "/v1/currentUser")
	public ResponseEntity<User> getUserPhoto(@AuthenticationPrincipal Token userToken) {
		User currentUser = userAccessor.fetchUserProfile(userToken.getLogonName(), userToken);

		return ResponseEntity.ok(currentUser);
	}

	@GetMapping(value = "/v1/requests/{requestId}")
	public ResponseEntity<?> deleteRequest(@PathVariable(name = "requestId") String requestId,
			@AuthenticationPrincipal Token userToken) {
		onboardRequestService.completeOnboardingRequest(requestId, userToken);

		return ResponseEntity.noContent().build();
	}
}