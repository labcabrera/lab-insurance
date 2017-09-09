package org.lab.insurance.domain.core.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Serializable> {

}
