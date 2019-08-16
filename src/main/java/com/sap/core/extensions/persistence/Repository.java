package com.sap.core.extensions.persistence;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sap.core.extensions.successfactors.connectivity.ToDo;
import com.sap.core.extensions.successfactors.connectivity.User;

@Component
@Scope("singleton")
public class Repository {
	private final Map<String, OnboardRequestEntity> USERS_ENTITY = new ConcurrentHashMap<>();

	OnboardRequestEntity saveNewOnboardingRequest(ToDo todo, User userDTO) {
		OnboardRequestEntity requestEntity = new OnboardRequestEntity(todo, userDTO);

		USERS_ENTITY.put(requestEntity.getId(), requestEntity);

		return requestEntity;
	}

	OnboardRequestEntity removeOnboardingRequest(String id) {
		return USERS_ENTITY.remove(id);
	}

	Collection<OnboardRequestEntity> listRequests() {
		return USERS_ENTITY.values();
	}
}
