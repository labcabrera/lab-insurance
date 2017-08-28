package org.lab.insurance.asset.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/assets")
@RestController
public class AssetSearchController {

	@Autowired
	private AssetRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Asset> searchAlt(@RequestParam(value = "p", defaultValue = "0") Integer page,
			@RequestParam(value = "s", defaultValue = "10") Integer size) {
		final Pageable pageableRequest = new PageRequest(page, size);
		Query query = new Query();
		query.with(pageableRequest);
		query.with(new Sort(Sort.Direction.ASC, "name"));
		List<Asset> result = mongoTemplate.find(query, Asset.class);
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Asset> searchById(@PathVariable(value = "id") String id) {
		Asset entity = repository.findOne(id);
		HttpStatus status = entity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Asset>(entity, status);
	}

	@RequestMapping(value = "/isin/{isin}", method = RequestMethod.GET)
	public ResponseEntity<Asset> searchByIsin(@PathVariable(value = "isin") String isin) {
		Asset entity = repository.findByIsin(isin);
		HttpStatus status = entity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Asset>(entity, status);
	}

}
