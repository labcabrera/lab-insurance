package org.lab.insurance.model.contract;

import java.util.List;

import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.system.TriggerDefinition;

import lombok.Data;

@Data
public abstract class FinancialService {

	private TriggerDefinition trigger;
	private List<OrderDistribution> sourceDistribution;
	private List<OrderDistribution> targetDistribution;

}
