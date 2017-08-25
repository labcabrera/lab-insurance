package org.lab.insurance.model.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.model.insurance.MarketOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketOrderRepository extends MongoRepository<MarketOrder, Serializable> {

}
