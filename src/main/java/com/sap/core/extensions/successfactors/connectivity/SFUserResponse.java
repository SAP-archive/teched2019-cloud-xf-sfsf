package com.sap.core.extensions.successfactors.connectivity;

import java.util.List;

public class SFUserResponse {
	private Data d;

	public SFUserResponse() {
	}

	public Data getD() {
		return d;
	}

	public void setD(Data d) {
		this.d = d;
	}

	public static class Data {
		private List<User> results;

		public Data() {
		}

		public List<User> getResults() {
			return results;
		}

		public void setResults(List<User> results) {
			this.results = results;
		}
	}
}
