package org.lab.insurance.domain.portfolio.repository;

import java.io.Serializable;

import org.lab.insurance.domain.portfolio.ContractPortfolioRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractPortfolioRelationRepository extends MongoRepository<ContractPortfolioRelation, String> {

}
