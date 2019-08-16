package com.sap.core.extensions.persistence;

import java.util.Objects;

import com.sap.core.extensions.successfactors.connectivity.ToDo;
import com.sap.core.extensions.successfactors.connectivity.User;

public class OnboardRequestEntity {
	private final ToDo todo;
	private final User user;

	OnboardRequestEntity(ToDo todo, User user) {
		this.todo = todo;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public ToDo getTodo() {
		return todo;
	}

	@Override
	public String toString() {
		return "OnboardRequestEntity [todo=" + todo + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(todo, user);
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
		return Objects.equals(todo, other.todo) && Objects.equals(user, other.user);
	}

}
