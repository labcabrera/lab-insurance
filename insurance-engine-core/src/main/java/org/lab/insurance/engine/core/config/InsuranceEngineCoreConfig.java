package org.lab.insurance.engine.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan("org.lab.insurance.engine.core")
@EnableMongoRepositories("org.lab.insurance.engine.core.domain")
public class InsuranceEngineCoreConfig {

}
