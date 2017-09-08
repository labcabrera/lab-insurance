package org.lab.insurance.domain.insurance;

import java.math.BigDecimal;
import java.util.List;

import org.lab.insurance.domain.HasCode;
import org.lab.insurance.domain.HasContract;
import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.engine.State;
import org.lab.insurance.domain.validation.ValidOrder;
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
public class Order implements HasContract, HasState {

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

	public enum States implements HasCode {
		INITIAL, PROCESSED, VALUED, ACCOUNTED;

		@Override
		public String getCode() {
			return name();
		}

	}

}
