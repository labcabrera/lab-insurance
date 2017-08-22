package org.lab.insurance.model.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.model.insurance.AssetGuaranteePercent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetGuaranteePercentRepository extends MongoRepository<AssetGuaranteePercent, Serializable> {

}
