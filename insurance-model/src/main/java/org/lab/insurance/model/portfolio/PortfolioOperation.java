package org.lab.insurance.model.portfolio;

import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.MarketOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class PortfolioOperation {

	@Id
	private ObjectId id;
	private Investment debe;
	private Investment haber;
	private BaseAsset asset;
	private MarketOrder marketOrder;
	private Date valueDate;
	private BigDecimal units;
	private BigDecimal amount;
	private String description;

}
