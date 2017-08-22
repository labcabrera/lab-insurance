package org.lab.insurance.model.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.model.insurance.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Serializable> {

}
