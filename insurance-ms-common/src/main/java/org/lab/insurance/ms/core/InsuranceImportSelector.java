package org.lab.insurance.ms.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsuranceImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(InsuranceConfiguration.class.getName());
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
		List<String> configurations = new ArrayList<>();

		log.debug("Attributes {}", attributes);

		// if (attributes.getBoolean("enableCommonModel")) {
		// configurations.add(InsuranceModelConfig.class.getName());
		// }

		// TODO
		// boolean enableSwagger = attributes.getBoolean("enableSwagger");
		// boolean enableEurekaClient = attributes.getBoolean("enableEurekaClient");

		return configurations.toArray(new String[0]);
	}

}
