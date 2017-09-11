package org.lab.insurance.domain.hateoas.mapper;

import java.util.ArrayList;

import org.lab.insurance.domain.core.insurance.MarketOrderType;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDistribution;
import org.lab.insurance.domain.hateoas.insurance.AssetResource;
import org.lab.insurance.domain.hateoas.insurance.OrderDistributionItem;
import org.lab.insurance.domain.hateoas.insurance.OrderResource;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class OrderResourceMapper extends CustomMapper<Order, OrderResource> {

	@Override
	public void mapAtoB(Order a, OrderResource b, MappingContext context) {
		b.setOrderId(a.getId());
		b.setContractId(a != null && a.getContract() != null ? a.getContract().getId() : null);
		b.setType(a.getType());
		b.setDates(a.getDates());
		b.setNetAmount(a.getNetAmount());
		b.setGrossAmount(a.getGrossAmount());
		b.setDistribution(new ArrayList<>());
		if (a.getBuyDistribution() != null) {
			a.getBuyDistribution().forEach(x -> b.getDistribution().add(map(x, MarketOrderType.BUY)));
		}
		if (a.getSellDistribution() != null) {
			a.getSellDistribution().forEach(x -> b.getDistribution().add(map(x, MarketOrderType.SELL)));
		}
	}

	@Override
	public void mapBtoA(OrderResource b, Order a, MappingContext context) {
		throw new RuntimeException("Not implemented");
	}

	private OrderDistributionItem map(OrderDistribution value, MarketOrderType type) {
		AssetResource asset = new AssetResource();
		asset.setAssetId(value.getAsset().getId());
		asset.setIsin(value.getAsset().getIsin());
		asset.setName(value.getAsset().getName());
		// TODO
		return new OrderDistributionItem(null, value.getAmount(), value.getPercent(), asset, type);
	}
}