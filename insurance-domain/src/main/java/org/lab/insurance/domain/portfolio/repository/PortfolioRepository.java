package org.lab.insurance.domain.portfolio.repository;

import java.io.Serializable;

import org.lab.insurance.domain.portfolio.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, Serializable> {

}
