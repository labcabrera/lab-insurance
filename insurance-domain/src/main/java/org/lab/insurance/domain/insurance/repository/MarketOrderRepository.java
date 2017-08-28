package org.lab.insurance.domain.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.insurance.MarketOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketOrderRepository extends MongoRepository<MarketOrder, Serializable> {

}
