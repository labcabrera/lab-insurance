package org.lab.insurance.domain;

public interface Constants {

	public interface ContractStates {
		String INITIAL = "CONTRACT_INITIAL";
		String PAYED = "CONTRACT_PAYED";
		String ACTIVE = "CONTRACT_ACTIVE";
		String CLOSED = "CONTRACT_CLOSED";
	}

	public interface OrderStates {
		String INITIAL = "ORDER_INITIAL";
		String PROCESSED = "ORDER_PROCESSED";
		String VALUED = "ORDER_VALUED";
		String ACCOUNTED = "ORDER_ACCOUNTED";
	}

	public interface MarketOrderStates {
		String INITIAL = "MARKET_ORDER_INITIAL";
		String PROCESSED = "MARKET_ORDER_PROCESSED";
		String VALUED = "MARKET_ORDER_VALUED";
		String ACCOUNTED = "MARKET_ORDER_ACCOUNTED";
	}

}
