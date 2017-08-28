package org.lab.insurance.domain.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.insurance.AssetGuaranteePercent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetGuaranteePercentRepository extends MongoRepository<AssetGuaranteePercent, Serializable> {

}
