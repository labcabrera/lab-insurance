package org.lab.insurance.engine;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.NotImplementedException;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.engine.ActionEntity;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ActionExecutionService {

	private final CamelContext camelContext;

	@Inject
	public ActionExecutionService(CamelContext camelContext) {
		this.camelContext = camelContext;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public <T> Message<T> execute(ActionEntity<T> actionEntity) {
		ActionDefinition definition = actionEntity.getClass().getAnnotation(ActionDefinition.class);
		String endpoint = definition.getEndpoint();
		ProducerTemplate producer = camelContext.createProducerTemplate();
		Message<T> result = producer.requestBody(endpoint, actionEntity, Message.class);
		try {
			producer.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public void programExecution(ActionEntity<?> actionEntity, Date when) {
		throw new NotImplementedException();
	}
}
