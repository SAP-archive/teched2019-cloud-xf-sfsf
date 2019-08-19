package com.sap.core.extensions.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.successfactors.connectivity.ToDo;
import com.sap.core.extensions.successfactors.connectivity.ToDoAccessor;
import com.sap.core.extensions.successfactors.connectivity.User;
import com.sap.core.extensions.successfactors.connectivity.UserDataAccessor;

@Component
public class OnboardRequestService {

	private final ToDoAccessor todoAccessor;
	private final Repository requestRepository;
	private final UserDataAccessor userAccessor;

	@Autowired
	public OnboardRequestService(ToDoAccessor todoAccessor, Repository requestRepository,
			UserDataAccessor userAccessor) {
		this.todoAccessor = todoAccessor;
		this.requestRepository = requestRepository;
		this.userAccessor = userAccessor;
	}

	public void createOnboardingRequest(String onboarAdministratorUserId, String relocatedUserId) {
		User relocatedUser = userAccessor.fetchUserProfile(relocatedUserId);

		ToDo todo = todoAccessor.createToDo(onboarAdministratorUserId, relocatedUser.getDefaultFullName(),
				relocatedUserId);

		requestRepository.saveNewOnboardingRequest(todo, relocatedUser);
	}

	public void completeOnboardingRequest(String requestId, Token userToken) {

		OnboardRequestEntity request = requestRepository.removeOnboardingRequest(requestId);

		todoAccessor.completeTodo(request.getTodo());
	}

	public Collection<OnboardRequestEntity> listOnboardingRequests() {
		return requestRepository.listRequests();
	}
}
