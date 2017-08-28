package org.lab.insurance.domain.legalentity.repository;

import java.io.Serializable;

import org.lab.insurance.domain.legalentity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Serializable> {

	Person findByIdCardNumber(String idCardNumber);

}
