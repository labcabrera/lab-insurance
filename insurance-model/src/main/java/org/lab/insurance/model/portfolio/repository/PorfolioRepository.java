package org.lab.insurance.model.portfolio.repository;

import java.io.Serializable;

import org.lab.insurance.model.portfolio.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PorfolioRepository extends MongoRepository<Portfolio, Serializable> {

}
