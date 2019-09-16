package com.sap.core.extensions.successfactors.connectivity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.core.extensions.successfactors.connectivity.util.ODataResponseUtils;

@Component
public class UserDataAccessor {
	private static final TypeReference<List<User>> USER_LIST_TYPE = new TypeReference<List<User>>() {
	};
	private static final String USER_PATH = "/User";
	private static final String PHOTO_QUERY_PATH = "/Photo?$select=photo&$filter=photoType eq '1' and userId eq '";
	private static final String SHORT_DETAILS_QUERY = USER_PATH
			+ "?$select=userId,country,department,location,title,email,defaultFullName,businessPhone&$filter=userId eq '";

	private static final String QUERY_SUFFIX = "'";

	private final SuccessFactorsCommunicator communicator;
	private final ODataResponseUtils responseUtils;

	@Autowired
	public UserDataAccessor(SuccessFactorsCommunicator communicator, ODataResponseUtils responseUtils) {
		this.communicator = communicator;
		this.responseUtils = responseUtils;
	}

	public User fetchUserProfile(String userName) {
		return fetchUserProfile(userName, null);
	}

	public User fetchUserProfile(String userName, Token userToken) {
		String response;
		if (null == userToken) {
			response = communicator.getWithTechnicalUser(buildUserRequestPath(userName), String.class);
		} else {
			response = communicator.getWithUserPropagation(buildUserRequestPath(userName), String.class, userToken);
		}
		List<User> users = responseUtils.readODataResponse(response, USER_LIST_TYPE);

		User user = users.get(0);
		String photo = fetchUserPicture(userName, userToken);
		user.setPhoto(photo);

		return user;
	}
	
	public User fetchSimpleProfileWithPicture(String userName, Token userToken) {
		String firstName = userToken.getGivenName();
		String lastName = userToken.getFamilyName();
		String photo = fetchUserPicture(userName, userToken);
		
		User user = new User();
		user.setDefaultFullName(firstName + " " + lastName);
		user.setPhoto(photo);
		
		return user;
	}

	private String fetchUserPicture(String userName, Token userToken) {
		String resultString;
		if (null == userToken) {
			resultString = communicator.getWithTechnicalUser(buildPhotoRequestPath(userName), String.class);
		} else {
			resultString = communicator.getWithUserPropagation(buildPhotoRequestPath(userName), String.class,
					userToken);
		}

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
