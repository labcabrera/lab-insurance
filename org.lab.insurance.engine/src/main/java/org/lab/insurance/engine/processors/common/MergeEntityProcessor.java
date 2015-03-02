package org.lab.insurance.engine.processors.common;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MergeEntityProcessor implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		entityManagerProvider.get().persist(exchange.getIn().getBody());
	}
}
