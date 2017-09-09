package org.lab.insurance.engine.core.config;

import org.lab.insurance.common.services.TimestampProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineTestConfig {

	@Bean
	TimestampProvider timeStampProvider() {
		return new TimestampProvider();
	}
}
