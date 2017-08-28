package org.lab.insurance.asset.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.repository.AssetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/assets")
@RestController
public class AssetSearchController {

	private AssetRepository repository;

	// TODO search & pagination
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Asset> search() {
		return repository.findAll();
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
