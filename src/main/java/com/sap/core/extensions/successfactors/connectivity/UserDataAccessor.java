package com.sap.core.extensions.successfactors.connectivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.Token;

@Component
public class UserDataAccessor {
	private static final String USER_PATH = "/User";
	private static final String PHOTO_QUERY_PATH = "/Photo?$select=photo&$filter=photoType eq '1' and userId eq '";
	private static final String SHORT_DETAILS_QUERY = USER_PATH
			+ "?$select=userId,firstName,lastName,title,email,username,businessPhone&$filter=userId eq '";

	private static final String QUERY_SUFFIX = "'";

	private SuccessFactorsCommunicator communicator;

	@Autowired
	public UserDataAccessor(SuccessFactorsCommunicator communicator) {
		this.communicator = communicator;
	}

	public User fetchUserProfile(String userName, Token userToken) {
		SFUserResponse response = communicator.getWithUserPropagation(buildUserRequestPath(userName),
				SFUserResponse.class, userToken);
		return response.getD().getResults().get(0);
	}

	public String fetchUserPicture(String userName, Token userToken) {
		String resultString = communicator.getWithUserPropagation(buildPhotoRequestPath(userName), String.class,
				userToken);

		JSONObject jsonObject = new JSONObject(resultString);
		JSONArray query = (JSONArray) jsonObject.query("/d/results");

		if (query.length() == 0) {
			return "";
		}

		JSONObject firstResult = (JSONObject) query.get(0);

		return (String) firstResult.query("/photo");

	}

	private String buildPhotoRequestPath(String userName) {
		return PHOTO_QUERY_PATH + userName + QUERY_SUFFIX;
	}

	private String buildUserRequestPath(String username) {
		return SHORT_DETAILS_QUERY + username + QUERY_SUFFIX;
	}
}
