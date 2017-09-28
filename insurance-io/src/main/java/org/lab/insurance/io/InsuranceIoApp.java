package org.lab.insurance.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("org.lab.insurance.domain")
public class InsuranceIoApp {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceIoApp.class, args);
	}

}