package com.sap.core.extensions.successfactors.connectivity.util;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ODataResponseUtils {
	private static final String ODATA_RESULTS_PATH = "/d/results";

	public <T> List<T> readODataResponse(String oDataResponse, TypeReference<List<T>> type) {

		JSONObject jsonResponse = new JSONObject(oDataResponse);
		JSONArray results = (JSONArray) jsonResponse.query(ODATA_RESULTS_PATH);

		try {
			return createObjectMapper().readValue(results.toString(), type);
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read Java Objects from json " + oDataResponse, e);
		}
	}

	private ObjectMapper createObjectMapper() {
		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
