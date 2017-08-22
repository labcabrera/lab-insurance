package org.lab.insurance.model.portfolio.repository;

import java.io.Serializable;

import org.lab.insurance.model.portfolio.PortfolioOperation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioOperationRepository extends MongoRepository<PortfolioOperation, Serializable> {

}
