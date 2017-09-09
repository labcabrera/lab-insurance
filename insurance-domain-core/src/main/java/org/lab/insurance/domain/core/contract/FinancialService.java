package org.lab.insurance.domain.core.contract;

import java.util.List;

import org.lab.insurance.domain.core.insurance.OrderDistribution;

import lombok.Data;

@Data
public abstract class FinancialService {

	private List<OrderDistribution> sourceDistribution;
	private List<OrderDistribution> targetDistribution;

}
