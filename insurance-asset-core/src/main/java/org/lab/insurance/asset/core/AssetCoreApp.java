package org.lab.insurance.asset.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("org.lab.insurance.domain.insurance")
public class AssetCoreApp {

	public static void main(String[] args) {
		SpringApplication.run(AssetCoreApp.class, args);
	}

}
