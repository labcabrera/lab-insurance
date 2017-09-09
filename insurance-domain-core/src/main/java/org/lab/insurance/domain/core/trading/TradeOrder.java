package org.lab.insurance.domain.core.trading;

import java.util.Date;
import java.util.List;

import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.engine.State;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class TradeOrder implements HasState {

	@Id
	private String id;
	private Date orderDate;
	private List<TradeOrderDetail> details;
	private State currentState;

}
