package org.lab.insurance.domain.insurance;

import org.lab.insurance.domain.portfolio.Portfolio;

import lombok.Data;

@Data
public class OrderProcessInfo {

	private OrderSellStrategy sellStrategy;
	private OrderBuyStrategy buyStrategy;
	private Portfolio portfolioPassive;
	private Portfolio portfolioActive;

}
