package com.sap.core.extensions.persistence;

import java.util.Objects;
import java.util.UUID;

import com.sap.core.extensions.successfactors.connectivity.ToDo;
import com.sap.core.extensions.successfactors.connectivity.User;

public class OnboardRequestEntity {
	private final String id;
	private final ToDo todo;
	private final User user;

	OnboardRequestEntity(ToDo todo, User user) {
		this.id = UUID.randomUUID().toString();
		this.todo = todo;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public ToDo getTodo() {
		return todo;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "OnboardRequestEntity [todo=" + todo + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, todo, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OnboardRequestEntity))
			return false;
		OnboardRequestEntity other = (OnboardRequestEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(todo, other.todo) && Objects.equals(user, other.user);
	}

}
