package com.sap.core.extensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Context initialized");
	}
}