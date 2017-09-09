package org.lab.insurance.domain.core.portfolio.repository;

import org.lab.insurance.domain.core.portfolio.ContractPortfolioRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractPortfolioRelationRepository extends MongoRepository<ContractPortfolioRelation, String> {

}
