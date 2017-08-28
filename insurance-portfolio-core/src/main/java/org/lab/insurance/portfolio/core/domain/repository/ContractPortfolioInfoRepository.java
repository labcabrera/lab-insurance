package org.lab.insurance.portfolio.core.domain.repository;

import java.io.Serializable;

import org.lab.insurance.portfolio.core.domain.ContractPortfolioRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractPortfolioInfoRepository extends MongoRepository<ContractPortfolioRelation, Serializable> {

}
