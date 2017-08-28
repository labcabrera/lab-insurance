package org.lab.insurance.bdd.contract.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableAutoConfiguration
@EnableIntegration
@ComponentScan({ "org.lab.insurance.bdd.contract" })
@EnableMongoRepositories("org.lab.insurance.domain")
@IntegrationComponentScan("com.lab.insurance.contract.creation.gateway")
public class BddConfig {

}
