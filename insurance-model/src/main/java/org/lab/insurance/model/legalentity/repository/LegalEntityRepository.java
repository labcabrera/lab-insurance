package org.lab.insurance.model.legalentity.repository;

import org.bson.types.ObjectId;
import org.lab.insurance.model.legalentity.LegalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LegalEntityRepository extends MongoRepository<LegalEntity, ObjectId> {

}
