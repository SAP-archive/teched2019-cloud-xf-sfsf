package com.sap.core.extensions.successfactors.connectivity;

import java.util.Objects;

public class UserNavRequestDTO {
	private final Metadata __metadata;

	public UserNavRequestDTO(String userId) {
		__metadata = new Metadata(userId);
	}

	public Metadata get__metadata() {
		return __metadata;
	}

	@Override
	public int hashCode() {
		return Objects.hash(__metadata);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserNavRequestDTO))
			return false;
		UserNavRequestDTO other = (UserNavRequestDTO) obj;
		return Objects.equals(__metadata, other.__metadata);
	}

	@Override
	public String toString() {
		return "UserNavRequestDTO [__metadata=" + __metadata + "]";
	}

	public static class Metadata {
		private final String uri;

		public Metadata(String userId) {
			uri = "User('" + userId + "')";
		}

		public String getUri() {
			return uri;
		}

		@Override
		public int hashCode() {
			return Objects.hash(uri);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Metadata))
				return false;
			Metadata other = (Metadata) obj;
			return Objects.equals(uri, other.uri);
		}

		@Override
		public String toString() {
			return "Metadata [uri=" + uri + "]";
		}

	}
}
