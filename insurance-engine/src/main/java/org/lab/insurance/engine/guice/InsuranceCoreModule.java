package org.lab.insurance.engine.guice;

import java.util.Properties;

import org.lab.insurance.engine.camel.CamelModule;
import org.lab.insurance.model.Constants;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.persist.jpa.JpaPersistModule;

public class InsuranceCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		Properties properties = readApplicationProperties();
		Names.bindProperties(binder(), properties);
		installJpaModule(properties);
		install(new CamelModule());
	}

	private Properties readApplicationProperties() {
		try {
			Properties properties = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			properties.load(classLoader.getResourceAsStream(Constants.CONFIGURATION_BASE_CLASSPATH_FILE));
			return properties;
		} catch (Exception ex) {
			throw new RuntimeException("Error reading application properties", ex);
		}
	}

	private void installJpaModule(Properties properties) {
		JpaPersistModule jpaModule = new JpaPersistModule(Constants.PERSISTENCE_UNIT_NAME);
		jpaModule.properties(properties);
		install(jpaModule);
	}
}
