package com.sap.core.extensions.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.successfactors.connectivity.ToDo;
import com.sap.core.extensions.successfactors.connectivity.ToDoAccessor;
import com.sap.core.extensions.successfactors.connectivity.User;

@Component
public class OnboardRequestService {

	private final ToDoAccessor todoAccessor;
	private final Repository requestRepository;

	@Autowired
	public OnboardRequestService(ToDoAccessor todoAccessor, Repository requestRepository) {
		this.todoAccessor = todoAccessor;
		this.requestRepository = requestRepository;
	}

	public void createOnboardingRequest(User user, Token userToken) {

		String fullName = user.getFirstName() + " " + user.getLastName();

		ToDo todo = todoAccessor.createToDo(user.getUserId(), fullName, userToken);

		requestRepository.saveNewOnboardingRequest(todo, user);
	}

	public void completeOnboardingRequest(String requestId, Token userToken) {

		OnboardRequestEntity request = requestRepository.removeOnboardingRequest(requestId);

		todoAccessor.completeTodo(request.getTodo(), userToken);
	}

	public Collection<OnboardRequestEntity> listOnboardingRequests() {
		return requestRepository.listRequests();
	}
}
