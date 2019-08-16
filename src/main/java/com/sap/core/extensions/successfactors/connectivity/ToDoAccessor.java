package com.sap.core.extensions.successfactors.connectivity;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;

@Component
public class ToDoAccessor {
	private static final String API_PATH = "/TodoEntryV2";
	private static final String UPSERT_API_PATH = "/upsert";
	private static final String COMPLETE_TODO_REQUEST_PREFIX = "{\"__metadata\":{\"uri\":\"TodoEntryV2('";
	private static final String COMPLETE_TODO_REQUEST_SUFFIX = "')\"}, \"status\":\"3\"}";
	private final SuccessFactorsCommunicator communicator;

	@Autowired
	public ToDoAccessor(SuccessFactorsCommunicator communicator) {
		this.communicator = communicator;
	}

	public ToDo createToDo(String userId, String userName, Token token) {
		ToDoRequestDTO todo = new ToDoRequestDTO(userId, userName);
		LoggerFactory.getLogger(ToDoAccessor.class).error("TODO DTO {}", new JSONObject(todo).toString());
		JSONObject response = communicator.postWithTechnicalUser(API_PATH, todo, token);

		String name = (String) response.query("/d/todoEntryName");
		String id = (String) response.query("/d/todoEntryId");

		return new ToDo(id, name);
	}

	public void completeTodo(ToDo todo, Token token) {
		String completeToDoPayload = COMPLETE_TODO_REQUEST_PREFIX + todo.getId() + COMPLETE_TODO_REQUEST_SUFFIX;

		communicator.postWithTechnicalUser(UPSERT_API_PATH, completeToDoPayload, token);
	}
}
