package com.sap.core.extensions.successfactors.connectivity;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.successfactors.connectivity.util.ODataResponseUtils;

@Component
public class ToDoAccessor {
	private static final TypeReference<List<ToDo>> TODO_LIST_TYPE = new TypeReference<List<ToDo>>() {
	};
	private static final String API_PATH = "/TodoEntryV2";
	private static final String UPSERT_API_PATH = "/upsert";
	private static final String COMPLETE_TODO_REQUEST_PREFIX = "{\"__metadata\":{\"uri\":\"TodoEntryV2(";
	private static final String COMPLETE_TODO_REQUEST_SUFFIX = "M)\"}, \"status\":\"3\"}";
	private static final String USER_TODOS_QUERY = "?$expand=userNav&$select=todoEntryId,todoEntryName,status,userNav/userId&$filter=categoryId eq '41' and userNav/userId eq '%s' and status eq 2";

	private final SuccessFactorsCommunicator communicator;
	private final ODataResponseUtils responseUtils;

	@Autowired
	public ToDoAccessor(SuccessFactorsCommunicator communicator, ODataResponseUtils responseUtils) {
		this.communicator = communicator;
		this.responseUtils = responseUtils;
	}

	public ToDo createToDo(String userId, String relocatedUserName, String relocatedUserId) {
		ToDoRequestDTO todo = new ToDoRequestDTO(userId, relocatedUserName, relocatedUserId);
		LoggerFactory.getLogger(ToDoAccessor.class).error("TODO DTO {}", new JSONObject(todo).toString());
		JSONObject response = communicator.postWithTechnicalUser(API_PATH, todo);

		String name = (String) response.query("/d/todoEntryName");
		String id = (String) response.query("/d/todoEntryId");

		return new ToDo(id, name);
	}

	public void completeTodo(ToDo todo, Token userToken) {
		String completeToDoPayload = COMPLETE_TODO_REQUEST_PREFIX + todo.getTodoEntryId()
				+ COMPLETE_TODO_REQUEST_SUFFIX;

		communicator.postWithUserPropagation(UPSERT_API_PATH, completeToDoPayload, userToken);
	}

	public List<ToDo> listUserTodos(Token userToken) {
		String responseString = communicator.getWithUserPropagation(
				API_PATH + String.format(USER_TODOS_QUERY, userToken.getLogonName()), String.class, userToken);

		return responseUtils.readODataResponse(responseString, TODO_LIST_TYPE);
	}
}
