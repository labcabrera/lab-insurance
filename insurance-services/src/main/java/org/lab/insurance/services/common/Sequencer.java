package org.lab.insurance.services.common;

public class Sequencer {

	public Long nextSequence(String name) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// LOG.debug("Generando secuencia para {}", name);
		// try {
		// lock.lock();
		// Long result = 1L;
		// Sequence sequence = null;
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<Sequence> query = entityManager.createQuery(QUERY_SELECT_SEQUENCE, Sequence.class);
		// List<Sequence> list = query.setParameter("id", name).setMaxResults(1).getResultList();
		// if (list.isEmpty()) {
		// result = 1L;
		// entityManager.persist(new Sequence(name, result));
		// } else {
		// sequence = list.iterator().next();
		// entityManager.lock(sequence, LockModeType.PESSIMISTIC_READ);
		// result = sequence.getValue() + 1;
		// sequence.setValue(result);
		// entityManager.merge(sequence);
		// }
		// entityManager.flush();
		// LOG.info("Generada secuencia {} para {}", result, name);
		// return result;
		// } finally {
		// lock.unlock();
		// }
	}
}
