package com.sap.core.extensions.successfactors.connectivity;

public class ToDo {
	
	private String todoEntryId;
	private String todoEntryName;

	public ToDo() {
		this(null, null);
	}

	public ToDo(String id, String name) {
		this.todoEntryId = id;
		this.todoEntryName = name;
	}

	public String getTodoEntryId() {
		return todoEntryId;
	}

	public void setTodoEntryId(String todoEntryId) {
		this.todoEntryId = todoEntryId;
	}

	public String getTodoEntryName() {
		return todoEntryName;
	}

	public void setTodoEntryName(String todoEntryName) {
		this.todoEntryName = todoEntryName;
	}
}
