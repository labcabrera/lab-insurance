package org.lab.insurance.domain.portfolio.repository;

import java.io.Serializable;

import org.lab.insurance.domain.portfolio.PortfolioOperation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioOperationRepository extends MongoRepository<PortfolioOperation, Serializable> {

}
