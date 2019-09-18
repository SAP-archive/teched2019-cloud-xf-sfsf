package com.sap.core.extensions.successfactors.connectivity.util;

public class TimeUtil {
	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {

		}
	}
}
