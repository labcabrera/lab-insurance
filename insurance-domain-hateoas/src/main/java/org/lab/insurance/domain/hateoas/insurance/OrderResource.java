package org.lab.insurance.domain.hateoas.insurance;

import java.math.BigDecimal;
import java.util.List;

import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.core.insurance.OrderDates;
import org.lab.insurance.domain.core.insurance.OrderDistribution;
import org.lab.insurance.domain.core.insurance.OrderType;
import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderResource extends ResourceSupport {

	String orderId;
	String contractId;
	OrderType type;
	State state;
	List<OrderDistribution> sellDistribution;
	List<OrderDistribution> buyDistribution;
	OrderDates dates;
	BigDecimal grossAmount;
	BigDecimal netAmount;

}