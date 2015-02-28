package org.lab.insurance.bootstrap.feeders;

import javax.persistence.EntityManager;

import net.sf.flatpack.DataSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEntityFeeder<T> extends AbstractFeeder {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityFeeder.class);

	protected abstract String getResourceName();

	protected abstract T buildEntity(DataSet dataSet);

	@Override
	public void run() {
		String resourceName = getResourceName();
		try {
			DataSet dataSet = buildParser(resourceName);
			EntityManager entityManager = entityManagerProvider.get();
			Long t0 = System.currentTimeMillis();
			Long count = 0L;
			while (dataSet.next()) {
				T entity = buildEntity(dataSet);
				entityManager.persist(entity);
				count++;
			}
			entityManager.flush();
			LOG.info("Loaded {} entities in {} ms from file {}", count, (System.currentTimeMillis() - t0), resourceName);
		} catch (Exception ex) {
			throw new RuntimeException(String.format("Error loading %s", resourceName), ex);
		}
	}
}
