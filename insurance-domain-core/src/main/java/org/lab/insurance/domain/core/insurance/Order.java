package org.lab.insurance.domain.core.insurance;

import java.math.BigDecimal;
import java.util.List;

import org.lab.insurance.domain.core.HasContract;
import org.lab.insurance.domain.core.HasIdentifier;
import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.core.validation.ValidOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa un movimiento u operacion de entrada/salida de fondos en un contrato.
 */
@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidOrder
@ToString(of = { "id", "type" })
public class Order implements HasContract, HasState, HasIdentifier<String> {

	@Id
	String id;

	OrderType type;

	@DBRef
	Contract contract;

	State currentState;
	OrderDates dates;
	OrderProcessInfo processInfo;

	List<OrderDistribution> sellDistribution;
	List<OrderDistribution> buyDistribution;
	List<MarketOrder> marketOrders;

	BigDecimal grossAmount;
	BigDecimal netAmount;

	public enum States {
		INITIAL, TO_PROCESS, PROCESSING, PROCESSED, VALUING, VALUED, ACCOUNTED;
	}

}
