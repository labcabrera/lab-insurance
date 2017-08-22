package org.lab.insurance.model.insurance;

import org.lab.insurance.model.portfolio.Portfolio;

import lombok.Data;

@Data
public class OrderProcessInfo {

	private OrderSellStrategy sellStrategy;
	private OrderBuyStrategy buyStrategy;
	private Portfolio portfolioPassive;
	private Portfolio portfolioActive;

}
