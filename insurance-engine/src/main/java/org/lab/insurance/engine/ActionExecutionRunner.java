package org.lab.insurance.engine;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.lab.insurance.core.serialization.Serializer;
import org.lab.insurance.engine.model.ActionEntity;
import org.lab.insurance.model.jpa.engine.ActionExecution;
import org.lab.insurance.services.common.TimestampProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.persist.Transactional;

public class ActionExecutionRunner {

	private static final Logger LOG = LoggerFactory.getLogger(ActionExecutionRunner.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private ActionExecutionService actionExecutionService;
	@Inject
	private Serializer serializer;
	@Inject
	private TimestampProvider timeStampProvider;

	public void run(Date from, Date to) {
		EntityManager entityManager = entityManagerProvider.get();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActionExecution> criteriaQuery = builder.createQuery(ActionExecution.class);
		Root<ActionExecution> root = criteriaQuery.from(ActionExecution.class);
		Predicate predicate = builder.isNull(root.<Date> get("cancelled"));
		predicate = builder.and(predicate, builder.isNull(root.<Date> get("executed")));
		predicate = builder.and(predicate, builder.isNull(root.<Date> get("failure")));
		predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.<Date> get("scheduled"), from));
		predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.<Date> get("scheduled"), to));
		criteriaQuery.where(predicate);
		criteriaQuery.orderBy(builder.asc(root.<Date> get("scheduled")), builder.asc(root.<Integer> get("priority")));
		TypedQuery<ActionExecution> query = entityManager.createQuery(criteriaQuery);
		query.setMaxResults(1);
		query.setParameter("from", from);
		query.setParameter("to", to);
		while (true) {
			List<ActionExecution> list = query.getResultList();
			if (list.isEmpty()) {
				break;
			} else {
				ActionExecution actionExecution = list.iterator().next();
				executeActionExecution(actionExecution, entityManager);
				entityManager.detach(actionExecution);
				entityManager.clear();
			}
		}
	}

	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void executeActionExecution(ActionExecution actionExecution, EntityManager entityManager) {
		LOG.debug("Executing scheduled task {}", actionExecution.getActionEntityClass().getSimpleName());
		try {
			Class<?> actionEntityClass = actionExecution.getActionEntityClass();
			ActionEntity actionEntity = (ActionEntity) serializer.fromJson(actionExecution.getActionEntityJson(), actionEntityClass);
			actionExecutionService.execute(actionEntity);
			actionExecution.setExecuted(timeStampProvider.getCurrentDateTime());
			entityManager.merge(actionExecution);
		} catch (Exception ex) {
			LOG.error("Execution error", ex);
			entityManager.getTransaction().rollback();
			entityManager.getTransaction().begin();
			actionExecution.setFailure(timeStampProvider.getCurrentDateTime());
			entityManager.merge(actionExecution);
			entityManager.getTransaction().commit();
			entityManager.getTransaction().begin();
		} finally {
			entityManager.flush();
		}
	}
}
