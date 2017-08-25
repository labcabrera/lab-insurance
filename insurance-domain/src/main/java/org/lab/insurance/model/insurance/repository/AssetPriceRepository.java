package org.lab.insurance.model.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.model.insurance.AssetPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetPriceRepository extends MongoRepository<AssetPrice, Serializable> {

}
