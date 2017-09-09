package org.lab.insurance.domain.core.legalentity.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.legalentity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Serializable> {

	Person findByIdCardNumber(String idCardNumber);

}
