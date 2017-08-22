package org.lab.insurance.model.contract.repository;

import org.bson.types.ObjectId;
import org.lab.insurance.model.contract.ContractPersonRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractPersonRelationRepository extends MongoRepository<ContractPersonRelation, ObjectId> {

}
