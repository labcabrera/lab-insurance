package org.lab.insurance.test.serialization;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.lab.insurance.core.serialization.Serializer;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.model.jpa.Policy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

public class TestPolicySerializer {

	@Test
	public void test() {
		Injector injector = Guice.createInjector(new InsuranceCoreModule());
		injector.getInstance(PersistService.class).start();
		Provider<EntityManager> entityManagerProvider = injector.getProvider(EntityManager.class);
		Serializer serializer = injector.getInstance(Serializer.class);
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<Policy> query = entityManager.createQuery("select e from Policy e", Policy.class);
		query.setMaxResults(10);
		for (Policy i : query.getResultList()) {
			System.out.println(serializer.toJson(i));
		}
	}
}
