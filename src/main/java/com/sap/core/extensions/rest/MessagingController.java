package com.sap.core.extensions.rest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.core.extensions.persistence.OnboardRequestService;

@RestController
public class MessagingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessagingController.class);

	private final OnboardRequestService onboardRequestService;

	@Autowired
	public MessagingController(OnboardRequestService onboardRequestService) {
		this.onboardRequestService = onboardRequestService;

	}

	@PostMapping(value = "/v1/webhook")
	public void onMessage(HttpServletRequest request, @RequestBody String body) {
		JSONObject bodyJson = new JSONObject(body);
		String todoUserId = (String) bodyJson.query("/createdBy");
		String userId = (String) bodyJson.query("/userId");
		String fullName = (String) bodyJson.query("/firstName") + " " + (String) bodyJson.query("/lastName");

		LOGGER.info(
				"Recieved event for relocation of user {} [{}] assigned to onboard administrator {}. Request body [{}]",
				fullName, userId, todoUserId, bodyJson);

		onboardRequestService.createOnboardingRequest(todoUserId, userId);
	}
}
