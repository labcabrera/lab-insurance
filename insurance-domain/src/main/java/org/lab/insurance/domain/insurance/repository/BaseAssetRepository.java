package org.lab.insurance.domain.insurance.repository;

import org.lab.insurance.domain.insurance.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseAssetRepository extends MongoRepository<Asset, String> {

	public Asset findByIsin(String isin);
}
