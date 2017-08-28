package org.lab.insurance.domain.legalentity.repository;

import org.bson.types.ObjectId;
import org.lab.insurance.domain.legalentity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, ObjectId> {

	Person findByIdCardNumber(String idCardNumber);

}
