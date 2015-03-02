package org.lab.insurance.engine;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.NotImplementedException;
import org.lab.insurance.core.serialization.Serializer;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.engine.ActionDefinition;
import org.lab.insurance.model.engine.ActionEntity;
import org.lab.insurance.model.jpa.engine.ActionExecution;
import org.lab.insurance.services.common.TimestampProvider;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ActionExecutionService {

	@Inject
	private CamelContext camelContext;
	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private Serializer serializer;
	@Inject
	private TimestampProvider timestampProvider;

	@Transactional
	@SuppressWarnings("unchecked")
	public <T> Message<T> execute(ActionEntity<T> actionEntity) {
		ActionDefinition definition = actionEntity.getClass().getAnnotation(ActionDefinition.class);
		String endpoint = definition.endpoint();
		ProducerTemplate producer = camelContext.createProducerTemplate();
		ActionExecution actionExecution = buildActionExecutionEntity(actionEntity);
		EntityManager entityManager = entityManagerProvider.get();
		try {
			Message<T> result = producer.requestBody(endpoint, actionEntity, Message.class);
			producer.stop();
			actionExecution.setResultJson(serializer.toJson(result));
			actionExecution.setExecuted(timestampProvider.getCurrentDateTime());
			entityManager.persist(actionExecution);
			return result;
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			entityManager.getTransaction().begin();
			actionExecution.setFailure(timestampProvider.getCurrentDateTime());
			// TODO add exception info
			actionExecution.setResultJson(serializer.toJson(ex.getMessage()));
			entityManager.persist(actionExecution);
			entityManager.getTransaction().commit();
			entityManager.getTransaction().begin();
			throw new RuntimeException(ex);
		}
	}

	public void programExecution(ActionEntity<?> actionEntity, Date when) {
		throw new NotImplementedException();
	}

	private <T> ActionExecution buildActionExecutionEntity(ActionEntity<T> actionEntity) {
		ActionExecution actionExecution = new ActionExecution();
		actionExecution.setActionEntityClass(actionEntity.getClass().getName());
		actionExecution.setActionEntityJson(serializer.toJson(actionEntity));
		return actionExecution;
	}
}
