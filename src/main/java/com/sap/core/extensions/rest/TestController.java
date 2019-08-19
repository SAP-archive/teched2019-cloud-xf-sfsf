package com.sap.core.extensions.rest;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.persistence.OnboardRequestEntity;
import com.sap.core.extensions.persistence.OnboardRequestService;
import com.sap.core.extensions.successfactors.connectivity.ToDo;
import com.sap.core.extensions.successfactors.connectivity.ToDoAccessor;
import com.sap.core.extensions.successfactors.connectivity.User;
import com.sap.core.extensions.successfactors.connectivity.UserDataAccessor;

@RestController
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	private final OnboardRequestService onboardRequestService;
	private final UserDataAccessor userAccessor;
	private final ToDoAccessor todoAccessor;

	@Autowired
	public TestController(OnboardRequestService onboardRequestService, UserDataAccessor userAccessor,
			ToDoAccessor todoAccessor) {
		this.onboardRequestService = onboardRequestService;
		this.userAccessor = userAccessor;
		this.todoAccessor = todoAccessor;

	}

	@GetMapping(value = "/v1/requests")
	public ResponseEntity<Collection<OnboardRequestEntity>> listOnboardingRequests(
			@AuthenticationPrincipal Token userToken) {
		
		if (onboardRequestService.listOnboardingRequests().isEmpty()) {
			LOGGER.error("Requests are empty. Loading predefined onboarding requests");
			loadPreset(userToken);
		}

		Collection<OnboardRequestEntity> requests = onboardRequestService.listOnboardingRequests();

		return ResponseEntity.ok(requests);
	}

	private void loadPreset(Token userToken) {
		List<ToDo> listUserTodos = todoAccessor.listUserTodos(userToken.getLogonName());

		for (ToDo todo : listUserTodos) {
			String todoName = todo.getTodoEntryName();
			String relocatedUserId = todoName.substring(todoName.indexOf('(') + 1, todoName.indexOf(')'));

			onboardRequestService.saveOnboardingRequest(todo, relocatedUserId);
		}
	}

	public static void main(String[] args) {
		String todoName = "Onboard Aanya Singh(sfadmin)";
		System.out.println(todoName.substring(todoName.indexOf('(') + 1, todoName.indexOf(')')));
	}	
	
	@GetMapping(value = "/v1/currentUser")
	public ResponseEntity<User> getUserPhoto(@AuthenticationPrincipal Token userToken) {
		User currentUser = userAccessor.fetchUserProfile(userToken.getLogonName(), userToken);

		return ResponseEntity.ok(currentUser);
	}

	@DeleteMapping(value = "/v1/requests/{requestId}")
	public ResponseEntity<?> deleteRequest(@PathVariable(name = "requestId") String requestId,
			@AuthenticationPrincipal Token userToken) {
		onboardRequestService.completeOnboardingRequest(requestId, userToken);

		return ResponseEntity.noContent().build();
	}
}