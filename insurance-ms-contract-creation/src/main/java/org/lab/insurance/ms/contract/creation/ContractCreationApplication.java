package org.lab.insurance.ms.contract.creation;

import org.lab.insurance.ms.contract.creation.config.ContractCreationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ContractCreationConfig.class)
public class ContractCreationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractCreationApplication.class, args);
	}

}
