package org.lab.insurance.asset.gateway.controller;

import org.lab.insurance.asset.gateway.domain.AssetResource;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hm/search")
public class AssetResourceController {

	@Autowired
	private AssetRepository repository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AssetResource> searchById(@PathVariable(value = "id") String id) {
		Asset entity = repository.findOne(id);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		AssetResource resource = new AssetResource();
		resource.add(ControllerLinkBuilder.linkTo(AssetResourceController.class).slash(entity.getId()).withSelfRel());
		resource.setIsin(entity.getIsin());
		resource.setName(entity.getName());
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
}
