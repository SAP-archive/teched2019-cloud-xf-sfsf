package com.sap.core.extensions.successfactors.connectivity;

import java.util.Objects;

public class User {
	private String userId;
	private String title;
	private String email;
	private String businessPhone;
	private String defaultFullName;
	private String photo;
	private String location;
	private String country;
	private String department;

	public User() {
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getEmail() {
		return email;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public String getDefaultFullName() {
		return defaultFullName;
	}

	public void setDefaultFullName(String defaultFullName) {
		this.defaultFullName = defaultFullName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessPhone, country, defaultFullName, department, email, location, photo, title, userId);
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
		return Objects.equals(businessPhone, other.businessPhone) && Objects.equals(country, other.country)
				&& Objects.equals(defaultFullName, other.defaultFullName)
				&& Objects.equals(department, other.department) && Objects.equals(email, other.email)
				&& Objects.equals(location, other.location) && Objects.equals(photo, other.photo)
				&& Objects.equals(title, other.title) && Objects.equals(userId, other.userId);
	}

}
