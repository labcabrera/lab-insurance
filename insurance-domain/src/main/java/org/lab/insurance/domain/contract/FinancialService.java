package org.lab.insurance.domain.contract;

import java.util.List;

import org.lab.insurance.domain.insurance.OrderDistribution;

import lombok.Data;

@Data
public abstract class FinancialService {

	private List<OrderDistribution> sourceDistribution;
	private List<OrderDistribution> targetDistribution;

}
