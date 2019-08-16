package com.sap.core.extensions.successfactors.connectivity;

import java.util.Objects;

public class ToDo {
	private String id;
	private String name;

	public ToDo() {
		this(null, null);
	}

	public ToDo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ToDo [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ToDo))
			return false;
		ToDo other = (ToDo) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

}
