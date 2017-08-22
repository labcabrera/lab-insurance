package org.lab.insurance.model.legalentity.repository;

import org.bson.types.ObjectId;
import org.lab.insurance.model.legalentity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, ObjectId> {

	Person findByIdCardNumber(String idCardNumber);

}
