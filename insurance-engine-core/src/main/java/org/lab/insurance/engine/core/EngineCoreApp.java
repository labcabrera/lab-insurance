package org.lab.insurance.engine.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("org.lab.insurance.engine.domain")
public class EngineCoreApp {

	public static void main(String[] args) {
		SpringApplication.run(EngineCoreApp.class, args);
	}

}
