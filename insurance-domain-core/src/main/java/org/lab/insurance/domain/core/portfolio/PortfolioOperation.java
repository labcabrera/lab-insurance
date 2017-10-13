package org.lab.insurance.domain.core.portfolio;

import java.math.BigDecimal;
import java.util.Date;

import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.MarketOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class PortfolioOperation {

	@Id
	private String id;

	@DBRef
	private Investment source;

	@DBRef
	private Investment target;

	@DBRef
	private Asset asset;

	@DBRef
	private MarketOrder marketOrder;

	private Date valueDate;
	private BigDecimal units;
	private BigDecimal amount;
	private String description;

}
