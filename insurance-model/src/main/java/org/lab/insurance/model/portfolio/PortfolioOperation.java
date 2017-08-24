package org.lab.insurance.model.portfolio;

import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.MarketOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class PortfolioOperation {

	@Id
	private ObjectId id;

	@DBRef
	private Investment debe;

	@DBRef
	private Investment haber;

	@DBRef
	private BaseAsset asset;

	@Transient
	private MarketOrder marketOrder;

	private Date valueDate;
	private BigDecimal units;
	private BigDecimal amount;
	private String description;

}
