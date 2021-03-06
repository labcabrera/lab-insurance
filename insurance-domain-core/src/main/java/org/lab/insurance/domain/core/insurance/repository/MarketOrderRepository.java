package org.lab.insurance.domain.core.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.insurance.MarketOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketOrderRepository extends MongoRepository<MarketOrder, Serializable> {

}
