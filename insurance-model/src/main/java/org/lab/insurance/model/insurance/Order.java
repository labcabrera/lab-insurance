package org.lab.insurance.model.insurance;

import java.math.BigDecimal;
import java.util.List;

import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.validation.ValidOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un movimiento u operacion de entrada/salida de fondos en un contrato.
 */
@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidOrder
public class Order implements HasContract, HasState<String> {

	@Id
	private String id;

	private OrderType type;

	@DBRef
	private Contract contract;

	private State currentState;

	private OrderProcessInfo processInfo;

	private List<OrderDistribution> sellDistribution;

	private List<OrderDistribution> buyDistribution;

	private List<MarketOrder> marketOrders;

	private OrderDates dates;

	private BigDecimal grossAmount;
	private BigDecimal netAmount;

}
