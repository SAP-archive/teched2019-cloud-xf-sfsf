package com.sap.core.extensions.successfactors.connectivity;

import java.util.Objects;

public class ToDoRequestDTO {

	private static final int MINUTE = 1000 * 60;
	private static final int HOUR = MINUTE * 60;
	private static final int DAY = HOUR * 24;
	private static final int STATUS_CURRENT = 2;
	private static final String INTELLIGENT_SERVICE_CATEGORY = "41";
	private final String todoEntryName;
	private final int status;
	private final String subjectId;
	private final String categoryId;
	private final String dueDate;
	private final String linkUrl;
	private final UserNavRequestDTO userNav;

	public ToDoRequestDTO(String subjectId, String userName) {
		this.todoEntryName = "Onboard " + userName;
		this.status = STATUS_CURRENT;
		this.subjectId = subjectId;
		this.categoryId = INTELLIGENT_SERVICE_CATEGORY;
		this.dueDate = getNextWeekDate();
		this.linkUrl = "https://google.com";
		this.userNav = new UserNavRequestDTO(subjectId);
	}

	private static String getNextWeekDate() {
		long currentTime = System.currentTimeMillis();
		long currentTimeRoundedDown = currentTime / 100000 * 100000;
		long nextWeek = currentTimeRoundedDown + (7 * DAY);
		return "/Date(" + nextWeek + ")/";
	}

	public static void main(String[] args) {
		System.out.println(getNextWeekDate());
	}

	public String getTodoEntryName() {
		return todoEntryName;
	}

	public int getStatus() {
		return status;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public UserNavRequestDTO getUserNav() {
		return userNav;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, dueDate, linkUrl, status, subjectId, todoEntryName, userNav);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ToDoRequestDTO))
			return false;
		ToDoRequestDTO other = (ToDoRequestDTO) obj;
		return Objects.equals(categoryId, other.categoryId) && Objects.equals(dueDate, other.dueDate)
				&& Objects.equals(linkUrl, other.linkUrl) && Objects.equals(status, other.status)
				&& Objects.equals(subjectId, other.subjectId) && Objects.equals(todoEntryName, other.todoEntryName)
				&& Objects.equals(userNav, other.userNav);
	}

	@Override
	public String toString() {
		return "ToDoRequestDTO [todoEntryName=" + todoEntryName + ", status=" + status + ", subjectId=" + subjectId
				+ ", categoryId=" + categoryId + ", dueDate=" + dueDate + ", linkUrl=" + linkUrl + ", userNav="
				+ userNav + "]";
	}

}
