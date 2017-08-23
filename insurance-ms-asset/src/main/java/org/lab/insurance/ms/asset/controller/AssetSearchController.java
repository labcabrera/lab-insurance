package org.lab.insurance.ms.asset.controller;

import java.util.List;

import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.repository.BaseAssetRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/assets")
@RestController
public class AssetSearchController {

	private BaseAssetRepository repository;

	// TODO search & pagination
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<BaseAsset> search() {
		return repository.findAll();
	}

}
