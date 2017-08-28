package org.lab.insurance.domain.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.insurance.AssetPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetPriceRepository extends MongoRepository<AssetPrice, Serializable> {

}
