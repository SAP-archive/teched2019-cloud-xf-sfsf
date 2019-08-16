package com.sap.core.extensions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pivotal.cfenv.core.CfEnv;

@Configuration
public class EnvironmentConfiguration {
	@Bean
	public CfEnv createCFEnv() {
		return new CfEnv();
	}
}
