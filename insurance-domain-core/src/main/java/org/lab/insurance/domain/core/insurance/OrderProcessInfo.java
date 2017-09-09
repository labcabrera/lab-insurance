package org.lab.insurance.domain.core.insurance;

import org.lab.insurance.domain.core.portfolio.Portfolio;

import lombok.Data;

@Data
public class OrderProcessInfo {

	private OrderSellStrategy sellStrategy;
	private OrderBuyStrategy buyStrategy;
	private Portfolio portfolioPassive;
	private Portfolio portfolioActive;

}
