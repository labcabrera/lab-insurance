package org.lab.insurance.ms.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
public class AssetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetApplication.class, args);
	}

}
