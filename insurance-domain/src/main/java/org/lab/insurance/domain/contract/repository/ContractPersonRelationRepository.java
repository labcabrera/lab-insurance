package org.lab.insurance.domain.contract.repository;

import java.io.Serializable;

import org.lab.insurance.domain.contract.ContractPersonRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractPersonRelationRepository extends MongoRepository<ContractPersonRelation, Serializable> {

}
