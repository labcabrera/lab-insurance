package org.lab.insurance.domain.hateoas.contract;

import java.util.List;

import org.lab.insurance.domain.core.contract.ContractDates;
import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.hateoas.insurance.OrderResource;
import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContractResource extends ResourceSupport {

	String contractId;
	ContractDates dates;
	State currentState;

	List<OrderResource> orders;

}
