package org.lab.insurance.order.gateway.controller;

import java.util.ArrayList;

import org.lab.insurance.domain.core.insurance.MarketOrderType;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDistribution;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.lab.insurance.domain.hateoas.insurance.AssetResource;
import org.lab.insurance.domain.hateoas.insurance.OrderDistributionItem;
import org.lab.insurance.domain.hateoas.insurance.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/search")
public class OrderResourceController {

	@Autowired
	private OrderRepository repository;

	// TODO prueba de concepto artesanal. Cambiar cuando esten implementados los mapeos directos entre entidades
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderResource> searchById(@PathVariable(value = "id") String id) {
		Order entity = repository.findOne(id);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		OrderResource resource = new OrderResource();
		resource.setDistribution(new ArrayList<>());
		resource.add(ControllerLinkBuilder.linkTo(OrderResourceController.class).slash(entity.getId()).withSelfRel());

		// TODO orika mapper
		resource.setType(entity.getType());
		resource.setOrderId(entity.getContract().getId());
		resource.setDates(entity.getDates());
		resource.setNetAmount(entity.getNetAmount());
		resource.setGrossAmount(entity.getGrossAmount());

		for (OrderDistribution i : entity.getBuyDistribution()) {
			resource.getDistribution().add(map(i, MarketOrderType.BUY));
		}
		for (OrderDistribution i : entity.getBuyDistribution()) {
			resource.getDistribution().add(map(i, MarketOrderType.SELL));
		}

		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	private OrderDistributionItem map(OrderDistribution value, MarketOrderType type) {
		AssetResource asset = new AssetResource();
		asset.add(new Link("/api/assets/search/" + value.getAsset().getId()));
		asset.setAssetId(value.getAsset().getId());
		asset.setIsin(value.getAsset().getIsin());
		asset.setName(value.getAsset().getName());
		return new OrderDistributionItem(value.getAmount(), value.getPercent(), asset, type);
	}
}
