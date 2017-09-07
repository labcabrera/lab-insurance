package org.lab.insurance.domain.portfolio.repository;

import org.lab.insurance.domain.portfolio.ContractPortfolioRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractPortfolioRelationRepository extends MongoRepository<ContractPortfolioRelation, String> {

}
