package org.lab.insurance.common.integration;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PayloadMongoAdapter<T> {

	@Autowired
	private MongoTemplate template;

	public T read(Serializable id, Class<T> entityClass) {
		log.info("Reading entity {}: {}", entityClass, id);
		T result = template.findById(id, entityClass);
		Assert.notNull(result, String.format("Missing %s:%s", entityClass.getName(), id));
		return result;
	}

	public T save(T entity) {
		log.info("Saving entity {}", entity);
		template.save(entity);
		return entity;
	}

}
