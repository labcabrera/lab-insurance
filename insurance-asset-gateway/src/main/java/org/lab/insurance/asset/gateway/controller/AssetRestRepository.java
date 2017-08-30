package org.lab.insurance.asset.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.insurance.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "asset", path = "asset")
public interface AssetRestRepository extends MongoRepository<Asset, String> {

	List<Asset> findByName(@Param("name") String name);

}
