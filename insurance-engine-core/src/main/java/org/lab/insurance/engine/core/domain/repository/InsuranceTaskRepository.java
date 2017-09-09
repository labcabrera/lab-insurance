package org.lab.insurance.engine.core.domain.repository;

import java.io.Serializable;

import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InsuranceTaskRepository extends MongoRepository<InsuranceTask, Serializable> {

}
