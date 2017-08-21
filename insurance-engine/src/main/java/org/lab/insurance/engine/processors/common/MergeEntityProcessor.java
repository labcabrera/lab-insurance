package org.lab.insurance.engine.processors.common;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergeEntityProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(MergeEntityProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Object entity = exchange.getIn().getBody();
		LOG.debug("Merge entity {}", entity);
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.merge(entity);
		entityManager.flush();
	}
}
