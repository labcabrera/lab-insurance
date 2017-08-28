package org.lab.insurance.domain.trading;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.engine.State;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class TradeOrder implements HasState<String> {

	@Id
	private ObjectId id;
	private Date orderDate;
	private List<TradeOrderDetail> details;
	private State currentState;

}
