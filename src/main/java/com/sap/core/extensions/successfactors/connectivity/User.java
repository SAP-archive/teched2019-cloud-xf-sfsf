package com.sap.core.extensions.successfactors.connectivity;

import java.util.Objects;

public class User {
	private String userId;
	private String firstName;
	private String lastName;
	private String title;
	private String email;
	private String username;
	private String businessPhone;

	public User() {
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getTitle() {
		return title;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessPhone, email, firstName, lastName, title, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(businessPhone, other.businessPhone) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(title, other.title) && Objects.equals(userId, other.userId)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", title=" + title
				+ ", email=" + email + ", username=" + username + ", businessPhone=" + businessPhone + "]";
	}

}
