package org.lab.insurance.model.contract.repository;

import org.bson.types.ObjectId;
import org.lab.insurance.model.contract.PolicyEntityRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolicyEntityRelationRepository extends MongoRepository<PolicyEntityRelation, ObjectId> {

}
