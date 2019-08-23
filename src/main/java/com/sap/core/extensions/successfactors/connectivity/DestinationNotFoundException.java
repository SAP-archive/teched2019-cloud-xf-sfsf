package com.sap.core.extensions.successfactors.connectivity;

public class DestinationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public DestinationNotFoundException(String message) {
        super(message);
    }
}
