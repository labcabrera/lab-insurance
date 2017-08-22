package org.lab.insurance.services.insurance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.insurance.AssetPrice;
import org.lab.insurance.model.insurance.MarketOrder;
import org.lab.insurance.model.insurance.MarketOrderSource;
import org.lab.insurance.model.insurance.MarketOrderType;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.repository.MarketOrderRepository;
import org.lab.insurance.services.common.TimestampProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * Servicio que se encarga de valorizar un movimiento calculando el importe o las unidades a partir de las cotizaciones.
 */
public class ValorizationService {

	@Autowired
	private CotizationsService cotizationsService;
	@Autowired
	private TimestampProvider timestampProvider;
	@Autowired
	private MarketOrderRepository marketOrderRepository;

	public void valorizate(Order order) throws NoCotizationException {
		List<MarketOrder> sell = order.getMarketOrders().stream().filter(x -> MarketOrderType.SELL.equals(x.getType()))
				.collect(Collectors.toList());
		List<MarketOrder> buy = order.getMarketOrders().stream().filter(x -> MarketOrderType.BUY.equals(x.getType()))
				.collect(Collectors.toList());
		for (MarketOrder i : sell) {
			valorizate(i);
		}
		// TODO calcular el importe total de las ventas y actualizar el order
		for (MarketOrder i : buy) {
			valorizate(i);
		}
		order.getDates().setValued(timestampProvider.getCurrentDateTime());
	}

	private void valorizate(MarketOrder marketOrder) throws NoCotizationException {
		AssetPrice price = cotizationsService.findPriceAtDate(marketOrder.getAsset(),
				marketOrder.getDates().getValueDate());
		marketOrder.setNav(price.getPrice());
		if (marketOrder.getSource() == MarketOrderSource.UNITS) {
			Assert.notNull(marketOrder.getUnits(), "Missing units");
			BigDecimal units = marketOrder.getUnits();
			BigDecimal amount = price.getPrice().multiply(units);
			marketOrder.setNetAmount(amount);
		}
		else {
			Assert.notNull(marketOrder.getUnits(), "Missing net amount");
			BigDecimal amount = marketOrder.getNetAmount();
			Integer decimals = marketOrder.getAsset().getDecimals() != null ? marketOrder.getAsset().getDecimals() : 5;
			BigDecimal units = amount.divide(price.getPrice(), decimals, RoundingMode.HALF_EVEN);
			marketOrder.setUnits(units);
		}
		marketOrderRepository.save(marketOrder);
	}
}
