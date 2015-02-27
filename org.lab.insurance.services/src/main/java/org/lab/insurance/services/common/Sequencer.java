package org.lab.insurance.services.common;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import org.lab.insurance.model.jpa.common.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class Sequencer {

	private static final Logger LOG = LoggerFactory.getLogger(Sequencer.class);
	private static final String QUERY_SELECT_SEQUENCE = "select s from Sequence s where s.id = :id order by s.value desc";

	private final Provider<EntityManager> entityManagerProvider;
	private final ReentrantLock lock;

	@Inject
	public Sequencer(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
		this.lock = new ReentrantLock();
	}

	public Long nextSequence(String name) {
		LOG.debug("Generando secuencia para {}", name);
		try {
			lock.lock();
			Long result = 1L;
			Sequence sequence = null;
			EntityManager entityManager = entityManagerProvider.get();
			TypedQuery<Sequence> query = entityManager.createQuery(QUERY_SELECT_SEQUENCE, Sequence.class);
			List<Sequence> list = query.setParameter("id", name).setMaxResults(1).getResultList();
			if (list.isEmpty()) {
				result = 1L;
				entityManager.persist(new Sequence(name, result));
			} else {
				sequence = list.iterator().next();
				entityManager.lock(sequence, LockModeType.PESSIMISTIC_READ);
				result = sequence.getValue() + 1;
				sequence.setValue(result);
				entityManager.merge(sequence);
			}
			entityManager.flush();
			LOG.info("Generada secuencia {} para {}", result, name);
			return result;
		} finally {
			lock.unlock();
		}
	}
}
