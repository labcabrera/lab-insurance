package org.lab.insurance.asset.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.repository.BaseAssetRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/assets")
@RestController
public class AssetSearchController {

	private BaseAssetRepository repository;

	// TODO search & pagination
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Asset> search() {
		return repository.findAll();
	}

}
