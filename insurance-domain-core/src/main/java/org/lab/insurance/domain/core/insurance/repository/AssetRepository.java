package org.lab.insurance.domain.core.insurance.repository;

import org.lab.insurance.domain.core.insurance.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetRepository extends MongoRepository<Asset, String> {

	public Asset findByIsin(String isin);
}
