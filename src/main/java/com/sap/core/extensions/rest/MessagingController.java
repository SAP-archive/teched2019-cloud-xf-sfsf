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
import com.sap.core.extensions.successfactors.connectivity.User;
import com.sap.core.extensions.successfactors.connectivity.UserDataAccessor;

@RestController
public class MessagingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessagingController.class);
	
	private final OnboardRequestService onboardRequestService;
	private UserDataAccessor userAccessor;

	@Autowired
	public MessagingController(OnboardRequestService onboardRequestService, UserDataAccessor userAccessor) {
		this.onboardRequestService = onboardRequestService;
		this.userAccessor = userAccessor;

	}
	
	@PostMapping(value = "/v1/webhook")
	public void onMessage(HttpServletRequest request, @RequestBody String body) {
		//{"firstName":"Aanya","lastName":"Singh","jobTitle":"Administrative Support","userId":"sfadmin"}
		JSONObject bodyJson = new JSONObject(body);
		String userId = (String) bodyJson.query("/userId");

		User user = new User();
		user.setUserId(userId);
		
		onboardRequestService.createOnboardingRequest(user, null);
		
		LOGGER.error(body);
		
	}
}