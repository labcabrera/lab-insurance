package org.lab.insurance.model.insurance.repository;

import org.lab.insurance.model.insurance.BaseAsset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseAssetRepository extends MongoRepository<BaseAsset, String> {

	public BaseAsset findByIsin(String isin);
}
